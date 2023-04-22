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

/** 课程计划 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "teachplan")
public class Teachplan{
    private static final long serialVersionUID = 1L;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changeDate;
    private Long courseId;
    private Long coursePubId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Integer grade;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String isPreview;
    private String mediaType;
    private Integer orderby;
    private Long parentid;
    private String pname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    private Integer status;
    private String timelength;
    
    public Teachplan(Teachplan teachplan) {
        if (Objects.nonNull(teachplan)) {
            this.changeDate=teachplan.changeDate;
            this.courseId=teachplan.courseId;
            this.coursePubId=teachplan.coursePubId;
            this.createDate=teachplan.createDate;
            this.description=teachplan.description;
            this.endTime=teachplan.endTime;
            this.grade=teachplan.grade;
            this.id=teachplan.id;
            this.isPreview=teachplan.isPreview;
            this.mediaType=teachplan.mediaType;
            this.orderby=teachplan.orderby;
            this.parentid=teachplan.parentid;
            this.pname=teachplan.pname;
            this.startTime=teachplan.startTime;
            this.status=teachplan.status;
            this.timelength=teachplan.timelength;
        }
    }
}
