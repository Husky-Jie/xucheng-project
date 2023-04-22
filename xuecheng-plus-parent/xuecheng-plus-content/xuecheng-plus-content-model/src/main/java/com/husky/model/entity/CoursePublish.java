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

/** 课程发布 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "course_publish")
public class CoursePublish{
    private static final long serialVersionUID = 1L;
    private String charge;
    private Long companyId;
    private String companyName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private String description;
    private String grade;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String market;
    private String mt;
    private String mtName;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime offlineDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime onlineDate;
    private Float originalPrice;
    private String pic;
    private Float price;
    private String remark;
    private String st;
    private String stName;
    private String status;
    private String tags;
    private String teachers;
    private String teachmode;
    private String teachplan;
    private String username;
    private String users;
    private Integer validDays;
    
    public CoursePublish(CoursePublish coursePublish) {
        if (Objects.nonNull(coursePublish)) {
            this.charge=coursePublish.charge;
            this.companyId=coursePublish.companyId;
            this.companyName=coursePublish.companyName;
            this.createDate=coursePublish.createDate;
            this.description=coursePublish.description;
            this.grade=coursePublish.grade;
            this.id=coursePublish.id;
            this.market=coursePublish.market;
            this.mt=coursePublish.mt;
            this.mtName=coursePublish.mtName;
            this.name=coursePublish.name;
            this.offlineDate=coursePublish.offlineDate;
            this.onlineDate=coursePublish.onlineDate;
            this.originalPrice=coursePublish.originalPrice;
            this.pic=coursePublish.pic;
            this.price=coursePublish.price;
            this.remark=coursePublish.remark;
            this.st=coursePublish.st;
            this.stName=coursePublish.stName;
            this.status=coursePublish.status;
            this.tags=coursePublish.tags;
            this.teachers=coursePublish.teachers;
            this.teachmode=coursePublish.teachmode;
            this.teachplan=coursePublish.teachplan;
            this.username=coursePublish.username;
            this.users=coursePublish.users;
            this.validDays=coursePublish.validDays;
        }
    }
}
