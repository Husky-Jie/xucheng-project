package com.husky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.husky.base.exception.CommonError;
import com.husky.base.exception.XueChengException;
import com.husky.mapper.TeachplanMapper;
import com.husky.mapper.TeachplanMediaMapper;
import com.husky.model.dto.SaveTeachplanDto;
import com.husky.model.dto.TeachplanDto;
import com.husky.model.entity.Teachplan;
import com.husky.model.entity.TeachplanMedia;
import com.husky.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/12
 * Time: 17:56
 * Description: TODO
 */
@Service
public class TeachplanServiceImpl extends ServiceImpl<TeachplanMapper, Teachplan> implements TeachplanService {

    @Autowired
    private TeachplanMapper teachPlanMapper;

    @Autowired
    private TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> queryTeachPlan(Long courseId) {
        List<TeachplanDto> teachPlanDtoList = teachPlanMapper.selectTeachplanDto(courseId);
        return teachPlanDtoList;
    }

    @Override
    public void addOrEditTeachPlan(SaveTeachplanDto saveTeachplanDto) {
        Teachplan teachplan = new Teachplan();
        BeanUtils.copyProperties(saveTeachplanDto, teachplan);
        // 通过课程计划id判断 新增和修改
        Long teachPlanId = saveTeachplanDto.getId();
        if (teachPlanId == null) {
            // 设置修改时间
            teachplan.setCreateDate(LocalDateTime.now());
            // 获取最新的排序号
            int count = getCount(saveTeachplanDto.getCourseId(), saveTeachplanDto.getParentid());
            // 确定排序字段 SELECT count(*) FROM teachplan WHERE course_id=117 AND parentid=268;
            teachplan.setOrderby(count + 1);
            // 新增课程计划
            boolean save = this.save(teachplan);
            if (!save) {
                throw new XueChengException("新增课程计划失败");
            }

            // 获取成功新增课程计划的id，就是子章节的parentId
            Long parentId = teachplan.getId();

            // 若新增大章节，则额外添加一个新小节，若不是新增大章节，而是添加小节，则不需要再额外添加一个新小节
            // 通过层级判断是否额外添加一个新小节，层级为大章节(grade==1)，则添加，反之则不添加
            if (saveTeachplanDto.getGrade() == 1) {
                // 自定义Teachplan对象，存放新增章节的子章节
                Teachplan teachPlanChildren = new Teachplan();
                // 设置子章节的parentId
                teachPlanChildren.setParentid(parentId);
                // 设置子章节的CourseId
                teachPlanChildren.setCourseId(teachplan.getCourseId());
                // 设置子章节的创建时间
                teachPlanChildren.setCreateDate(LocalDateTime.now());
                // 设置子章节的层级
                teachPlanChildren.setGrade(2);
                // 设置子章节的初始名字
                teachPlanChildren.setPname("新小节名称 [点击修改]");
                // 设置排序字段
                teachPlanChildren.setOrderby(1);
                boolean saveChildren = this.save(teachPlanChildren);
                if (!saveChildren) {
                    throw new XueChengException("新增课程计划失败");
                }
            }
        }else {
            teachplan.setChangeDate(LocalDateTime.now());
            boolean update = this.updateById(teachplan);
            if (!update) {
                throw new XueChengException("修改课程计划失败");
            }
        }
    }

    @Transactional
    @Override
    public Boolean removeTeachPlan(Long id) {
        // 通过id查询课程计划
        Teachplan teachplan = this.getById(id);
        if (teachplan == null) {
            throw new XueChengException("该课程计划不存在");
        }
        // 通过课程id和子章节的parentId查询父章节下子章节个数
        int countChild = getCount(teachplan.getCourseId(), teachplan.getParentid());
        // 通过层级等于2，删除子章节
        if (teachplan.getGrade() == 2 && countChild > 0) {
            boolean childRemove = this.removeById(id);
            // 删除子章节的课程媒资
            LambdaQueryWrapper<TeachplanMedia> mediaLambdaQueryWrapper = new LambdaQueryWrapper<>();
            mediaLambdaQueryWrapper.eq(TeachplanMedia::getTeachplanId, id);
            TeachplanMedia teachplanMedia = teachplanMediaMapper.selectOne(mediaLambdaQueryWrapper);
            if (teachplanMedia != null) {
                int i = teachplanMediaMapper.deleteById(teachplanMedia);
               if (i <= 0) {
                   throw new XueChengException("该子章节的媒资删除失败");
               }
            }
            // 若删除的该子节点为最后一个子节点，则将其的父节点一起删除
            if (countChild == 1) {
                // 其子节点的父节点id
                Long parentId = teachplan.getParentid();
                boolean remove = this.removeById(parentId);
                if (!remove) {
                    throw new XueChengException("该父章节删除失败");
                }
            }
            if (!childRemove) {
                throw new XueChengException("该子章节删除失败");
            }
            return true;
        }

        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, id);
        // 通过父章节id查询子章节个数
        int count = Long.valueOf(this.count(queryWrapper)).intValue();
        // 通过层级等于1，删除父章节，若父章节下还有子章节，则不删除
        if (teachplan.getGrade() == 1) {
            if (count > 0) {
                return false;
            }
        }
        throw new XueChengException(CommonError.UNKOWN_ERROR.getErrMessage());
    }

    @Override
    public void moveDown(Long id) {
        // 根据id查询课程计划
        Teachplan teachplan = this.getById(id);
        if (teachplan == null) {
            throw new XueChengException("该课程计划不存在");
        }

        // 该课程计划的排序序号
        Integer orderBy = teachplan.getOrderby();
        // 修改该课程计划的排序序号+1
        int orderByAdd = orderBy + 1;
        String cause = "下移失败";
        exchange(teachplan, orderBy, orderByAdd, cause);
    }

    @Override
    public void moveUp(Long id) {
        // 根据id查询课程计划
        Teachplan teachplan = this.getById(id);
        if (teachplan == null) {
            throw new XueChengException("该课程计划不存在");
        }

        // 该课程计划的排序序号
        Integer orderBy = teachplan.getOrderby();
        // 修改该课程计划的排序序号-1
        int removeNum = orderBy - 1;
        String cause = "上移失败";
        exchange(teachplan, orderBy, removeNum, cause);
    }


    /**
     * 课程计划排序——序号交换
     * @param teachplan     根据id查询的课程计划
     * @param orderBy       该课程计划的排序序号
     * @param removeNum     移动后的排序序号(上移减一，下移加一)
     * @param cause         交换失败异常信息
     */
    private void exchange(Teachplan teachplan, Integer orderBy, int removeNum, String cause) {

        // 拼接条件
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid, teachplan.getParentid());
        queryWrapper.eq(Teachplan::getCourseId, teachplan.getCourseId());
        queryWrapper.eq(Teachplan::getOrderby, removeNum);
        // 查询出原本是orderBy +/- 1的课程计划
        Teachplan one = this.getOne(queryWrapper);

        // 若为空，说明teachplan对象为最下边/最上边的课程计划，直接返回
        if (one == null) {
            throw new XueChengException("该课程为最下边或最上边的课程计划");
        }
        // 设置为Teachplan的排序序号
        one.setOrderby(orderBy);
        // 设置修改时间
        one.setChangeDate(LocalDateTime.now());
        // 更新
        boolean updateOne = this.updateById(one);
        if (!updateOne) {
            throw new XueChengException(cause);
        }

        // 设置为移动后的排序序号
        teachplan.setOrderby(removeNum);
        // 设置修改时间
        teachplan.setChangeDate(LocalDateTime.now());
        // 更新该课程
        boolean updateTeachPlan = this.updateById(teachplan);
        if (!updateTeachPlan) {
            throw new XueChengException(cause);
        }
    }



    /**
     * 获取最新的排序号
     * @param
     * @return
     */
    private int getCount(Long courseId, Long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId, courseId);
        queryWrapper.eq(Teachplan::getParentid, parentId);
        int count = Long.valueOf(this.count(queryWrapper)).intValue();
        return count;
    }
}
