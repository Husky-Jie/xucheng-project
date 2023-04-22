package com.husky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.husky.base.exception.XueChengException;
import com.husky.base.model.PageParams;
import com.husky.base.model.PageResult;
import com.husky.mapper.CourseBaseMapper;
import com.husky.model.dto.AddCourseDto;
import com.husky.model.dto.CourseBaseInfoDto;
import com.husky.model.dto.QueryCourseParamsDto;
import com.husky.model.dto.UpdateCourseDto;
import com.husky.model.entity.CourseBase;
import com.husky.model.entity.CourseCategory;
import com.husky.model.entity.CourseMarket;
import com.husky.service.CourseBaseService;
import com.husky.service.CourseCategoryService;
import com.husky.service.CourseMarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/7
 * Time: 16:45
 * Description: 描述该类的功能
 */
@Slf4j
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    @Autowired
    private CourseMarketService courseMarketService;
    @Autowired
    private CourseCategoryService courseCategoryService;

    /**
     * 课程分页查询
     * @param pageParams    分页参数
     * @param courseParams  查询条件
     * @return
     */
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParams) {
        // 分页查询
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 拼接查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(courseParams.getCourseName()), CourseBase::getName, courseParams.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(courseParams.getAuditStatus()), CourseBase::getAuditStatus, courseParams.getAuditStatus());
        queryWrapper.eq(StringUtils.isNotEmpty(courseParams.getPublishStatus()), CourseBase::getStatus, courseParams.getPublishStatus());
        queryWrapper.orderByDesc(CourseBase::getCreateDate);
        // 获取查询数据
        Page<CourseBase> basePage = this.page(page, queryWrapper);
        // 获取课表
        List<CourseBase> records = basePage.getRecords();
        // 总条数
        long total = basePage.getTotal();
        return new PageResult<>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Transactional
    @Override
    public CourseBaseInfoDto addCourseBase(Long companyId, AddCourseDto dto) {
        /**
         * 新增课程基本信息
         */
        // 新增courseBase对象
        CourseBase courseBase = new CourseBase();
        // 将AddCourseDto中的courseBase信息拷贝进课程信息对象中
        BeanUtils.copyProperties(dto, courseBase);
        // 设置审核状态
        courseBase.setAuditStatus("202002");
        // 设置发布状态
        courseBase.setStatus("203001");
        // 机构id
        courseBase.setCompanyId(companyId);
        // 添加时间
        courseBase.setCreateDate(LocalDateTime.now());
        // 存入course_base表中
        boolean save = this.save(courseBase);
        // 判断新增课程是否新增成功
        if (!save) {
            throw new XueChengException("新增课程基本信息失败");
        }

        // 课程营销新增
        courseMarketService.addCourseMarket(dto, courseBase.getId());

        // 查询课程基本信息及营销信息并返回
        return getCourseBaseInfo(courseBase.getId());
    }

    @Override
    public CourseBaseInfoDto queryCourseBase(Long id) {
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(id);
        return courseBaseInfo;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto putCourseBase(UpdateCourseDto updateCourseDto, Long companyId) {

        // 根据课程id查询courserBase
        CourseBase base = this.getById(updateCourseDto.getId());
        if (base == null) {
            throw new XueChengException("课程不存在");
        }

        // 修改课程信息表
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(updateCourseDto, courseBase);
        courseBase.setChangeDate(LocalDateTime.now());

        // 机构id
        Long baseCompanyId = base.getCompanyId();
        // 本机构的才能修改课程  使用机构id来辨别
        if (!companyId.equals(baseCompanyId)) {
            throw new XueChengException("只有本机构的才能修改信息");
        }

        boolean updateBase = this.updateById(courseBase);
        if (!updateBase) {
            throw new XueChengException("课程信息更新失败");
        }


        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(updateCourseDto, courseMarket);

        // 查询该课程营销信息
        CourseMarket marketServiceById = courseMarketService.getById(updateCourseDto.getId());
        if (marketServiceById == null) {
            // 若为空则新增
            boolean save = courseMarketService.save(courseMarket);
            if (!save){
                throw new XueChengException("课程营销更新失败");
            }
        }else {
            // 否则，修改课程营销表
            boolean updateMarket = courseMarketService.updateById(courseMarket);
            if (!updateMarket){
                throw new XueChengException("课程营销更新失败");
            }
        }


        // 查询课程信息并返回
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfo(updateCourseDto.getId());
        return courseBaseInfo;
    }

    /**
     * 根据id查询课程信息
     * @param courseId 课程id
     * @return
     */
    private CourseBaseInfoDto getCourseBaseInfo(Long courseId){

        // 查询课程基本信息
        CourseBase courseBase = this.getById(courseId);
        if (courseBase == null) {
            return null;
        }
        // 查询课程营销信息
        CourseMarket courseMarket = courseMarketService.getById(courseId);
        // 定义CourseBaseInfoDto对象
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        // 拷贝
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

        // 查询课程分类
        CourseCategory courseCategorySt = courseCategoryService.getById(courseBase.getSt());
        // 设置子节点名称
        courseBaseInfoDto.setStName(courseCategorySt.getName());
        // 查询该课程分类的父节点
        CourseCategory courseCategoryMt = courseCategoryService.getById(courseCategorySt.getParentid());
        // 设置父节点名称
        courseBaseInfoDto.setMtName(courseCategoryMt.getName());

        return courseBaseInfoDto;
    }
}
