package com.husky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/** 课程分类 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "course_category")
public class CourseCategory{
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private String id;
    private Integer isLeaf;
    private Integer isShow;
    private String label;
    private String name;
    private Integer orderby;
    private String parentid;
    
    public CourseCategory(CourseCategory courseCategory) {
        if (Objects.nonNull(courseCategory)) {
            this.id=courseCategory.id;
            this.isLeaf=courseCategory.isLeaf;
            this.isShow=courseCategory.isShow;
            this.label=courseCategory.label;
            this.name=courseCategory.name;
            this.orderby=courseCategory.orderby;
            this.parentid=courseCategory.parentid;
        }
    }
}
