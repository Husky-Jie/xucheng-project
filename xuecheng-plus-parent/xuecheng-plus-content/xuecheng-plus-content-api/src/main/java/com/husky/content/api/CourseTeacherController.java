package com.husky.content.api;

import com.husky.base.model.R;
import com.husky.model.dto.AddCourseTeacherDto;
import com.husky.model.entity.CourseTeacher;
import com.husky.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/15
 * Time: 23:03
 * Description: TODO
 */
@Slf4j
@Api(value = "师资管理接口",tags = "师资管理接口")
@RestController
public class CourseTeacherController {

    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping("/courseTeacher/list/{courseId}")
    @ApiOperation("查询教师接口")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    public List<CourseTeacher> getTeacher(@PathVariable Long courseId){
        return courseTeacherService.getCourseTeacher(courseId);
    }

    @PostMapping("/courseTeacher")
    @ApiOperation("添加教师接口")
    public CourseTeacher addTeacher(@RequestBody AddCourseTeacherDto addCourseTeacherDto){
        return null;
    }
}
