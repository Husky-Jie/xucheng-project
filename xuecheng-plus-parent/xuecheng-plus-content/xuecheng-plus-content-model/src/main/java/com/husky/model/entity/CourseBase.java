package com.husky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

/** 课程基本信息 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "课程基本信息类")
@TableName(value = "course_base")
public class CourseBase{
    private static final long serialVersionUID = 1L;
    private String auditStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changeDate;
    private String changePeople;
    private Long companyId;
    private String companyName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private String createPeople;
    private String description;
    private String grade;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String mt;
    private String name;
    private String pic;
    private String st;
    private String status;
    private String tags;
    private String teachmode;
    private String users;
    
    public CourseBase(CourseBase courseBase) {
        if (Objects.nonNull(courseBase)) {
            this.auditStatus=courseBase.auditStatus;
            this.changeDate=courseBase.changeDate;
            this.changePeople=courseBase.changePeople;
            this.companyId=courseBase.companyId;
            this.companyName=courseBase.companyName;
            this.createDate=courseBase.createDate;
            this.createPeople=courseBase.createPeople;
            this.description=courseBase.description;
            this.grade=courseBase.grade;
            this.id=courseBase.id;
            this.mt=courseBase.mt;
            this.name=courseBase.name;
            this.pic=courseBase.pic;
            this.st=courseBase.st;
            this.status=courseBase.status;
            this.tags=courseBase.tags;
            this.teachmode=courseBase.teachmode;
            this.users=courseBase.users;
        }
    }
}
