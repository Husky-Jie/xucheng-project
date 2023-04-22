package com.husky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.husky.model.entity.CourseTeacher;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/15
 * Time: 23:31
 * Description: TODO
 */
public interface CourseTeacherService extends IService<CourseTeacher> {

    List<CourseTeacher> getCourseTeacher(Long courseId);
}
