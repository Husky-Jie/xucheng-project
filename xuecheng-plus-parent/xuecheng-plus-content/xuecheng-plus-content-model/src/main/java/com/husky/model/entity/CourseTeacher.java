package com.husky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/** 课程-教师关系表 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "course_teacher")
public class CourseTeacher{
    private static final long serialVersionUID = 1L;
    private Long courseId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String introduction;
    private String photograph;
    private String position;
    private String teacherName;
    
    public CourseTeacher(CourseTeacher courseTeacher) {
        if (Objects.nonNull(courseTeacher)) {
            this.courseId=courseTeacher.courseId;
            this.createDate=courseTeacher.createDate;
            this.id=courseTeacher.id;
            this.introduction=courseTeacher.introduction;
            this.photograph=courseTeacher.photograph;
            this.position=courseTeacher.position;
            this.teacherName=courseTeacher.teacherName;
        }
    }
}
