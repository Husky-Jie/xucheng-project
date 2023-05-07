package com.husky.content.api;

import com.husky.base.exception.ValidationGroups;
import com.husky.content.util.SecurityUtil;
import com.husky.model.dto.*;
import com.husky.model.entity.CourseBase;
import com.husky.base.model.PageParams;
import com.husky.base.model.PageResult;
import com.husky.model.entity.CourseCategory;
import com.husky.service.CourseBaseService;
import com.husky.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/6
 * Time: 17:29
 * Description: 课程信息管理接口
 */
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController
@RequestMapping
@Slf4j
public class CourseBaseInfoController {

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private CourseCategoryService courseCategoryService;


    @ApiOperation(value = "课程分页查询接口")
    @PostMapping("/course/list")
    @PreAuthorize("hasAuthority('')")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){
        log.info("课程查询");
        return courseBaseService.queryCourseBaseList(getCompanyId(), pageParams, queryCourseParamsDto);
    }

    @ApiOperation(value = "课程分类查询接口")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> categoryList(){
        return courseCategoryService.queryTreeNodes("1");
    }

    /**
     *
     * @param addCourseDto   @Validated：启动JSR303校验
     * @return
     */
    @ApiOperation(value = "新增课程信息接口")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(
            @RequestBody
            @Validated
            AddCourseDto addCourseDto) {
        return courseBaseService.addCourseBase(getCompanyId(), addCourseDto);
    }

    @ApiOperation(value = "根据id查询课程信息接口")
    @GetMapping("/course/{id}")
    public CourseBaseInfoDto queryCourseDto(@PathVariable("id") Long id){
        return courseBaseService.queryCourseBase(id);
    }

    @ApiOperation(value = "修改课程信息接口")
    @PutMapping("/course")
    public CourseBaseInfoDto updateCourseDto(@RequestBody UpdateCourseDto updateCourseDto){
        return courseBaseService.putCourseBase(updateCourseDto, getCompanyId());
    }

    /**
     * 获取当前用户机构id
     * @return
     */
    @NotNull
    private Long getCompanyId() {
        SecurityUtil.XcUser user = SecurityUtil.getUser();
        Long companyId = Long.valueOf(user.getCompanyId());
        return companyId;
    }
}
