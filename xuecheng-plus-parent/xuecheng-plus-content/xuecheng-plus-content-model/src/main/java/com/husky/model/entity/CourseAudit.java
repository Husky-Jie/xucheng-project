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

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "course_audit")
public class CourseAudit{
    private static final long serialVersionUID = 1L;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime auditDate;
    private String auditMind;
    private String auditPeople;
    private String auditStatus;
    private Long courseId;
    @TableId(type = IdType.AUTO)
    private Long id;
    
    public CourseAudit(CourseAudit courseAudit) {
        if (Objects.nonNull(courseAudit)) {
            this.auditDate=courseAudit.auditDate;
            this.auditMind=courseAudit.auditMind;
            this.auditPeople=courseAudit.auditPeople;
            this.auditStatus=courseAudit.auditStatus;
            this.courseId=courseAudit.courseId;
            this.id=courseAudit.id;
        }
    }
}
