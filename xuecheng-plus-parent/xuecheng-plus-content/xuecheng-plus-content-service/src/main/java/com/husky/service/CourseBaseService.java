package com.husky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.husky.base.model.PageParams;
import com.husky.base.model.PageResult;
import com.husky.model.dto.AddCourseDto;
import com.husky.model.dto.CourseBaseInfoDto;
import com.husky.model.dto.QueryCourseParamsDto;
import com.husky.model.dto.UpdateCourseDto;
import com.husky.model.entity.CourseBase;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/7
 * Time: 16:45
 * Description: 描述该类的功能
 */
public interface CourseBaseService extends IService<CourseBase> {

    // 课程查询接口
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto CourseParams);

    // 课程新增接口

    /**
     *
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return 课程详细信息
     */
    CourseBaseInfoDto addCourseBase(Long companyId, AddCourseDto addCourseDto);

    // 根据id查询课程信息接口
    CourseBaseInfoDto queryCourseBase(Long id);

    // 修改课程信息接口
    CourseBaseInfoDto putCourseBase(UpdateCourseDto updateCourseDto, Long companyId);
}
