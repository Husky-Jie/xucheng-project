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
@TableName(value = "course_publish_pre")
public class CoursePublishPre{
    private static final long serialVersionUID = 1L;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditDate;
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
    
    public CoursePublishPre(CoursePublishPre coursePublishPre) {
        if (Objects.nonNull(coursePublishPre)) {
            this.auditDate=coursePublishPre.auditDate;
            this.charge=coursePublishPre.charge;
            this.companyId=coursePublishPre.companyId;
            this.companyName=coursePublishPre.companyName;
            this.createDate=coursePublishPre.createDate;
            this.description=coursePublishPre.description;
            this.grade=coursePublishPre.grade;
            this.id=coursePublishPre.id;
            this.market=coursePublishPre.market;
            this.mt=coursePublishPre.mt;
            this.mtName=coursePublishPre.mtName;
            this.name=coursePublishPre.name;
            this.originalPrice=coursePublishPre.originalPrice;
            this.pic=coursePublishPre.pic;
            this.price=coursePublishPre.price;
            this.remark=coursePublishPre.remark;
            this.st=coursePublishPre.st;
            this.stName=coursePublishPre.stName;
            this.status=coursePublishPre.status;
            this.tags=coursePublishPre.tags;
            this.teachers=coursePublishPre.teachers;
            this.teachmode=coursePublishPre.teachmode;
            this.teachplan=coursePublishPre.teachplan;
            this.username=coursePublishPre.username;
            this.users=coursePublishPre.users;
            this.validDays=coursePublishPre.validDays;
        }
    }
}
