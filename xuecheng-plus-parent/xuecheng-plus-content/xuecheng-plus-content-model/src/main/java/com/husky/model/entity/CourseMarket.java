package com.husky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/** 课程营销信息 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "course_market")
public class CourseMarket{
    private static final long serialVersionUID = 1L;
    private String charge;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Float originalPrice;
    private String phone;
    private Float price;
    private String qq;
    private Integer validDays;
    private String wechat;
    
    public CourseMarket(CourseMarket courseMarket) {
        if (Objects.nonNull(courseMarket)) {
            this.charge=courseMarket.charge;
            this.id=courseMarket.id;
            this.originalPrice=courseMarket.originalPrice;
            this.phone=courseMarket.phone;
            this.price=courseMarket.price;
            this.qq=courseMarket.qq;
            this.validDays=courseMarket.validDays;
            this.wechat=courseMarket.wechat;
        }
    }
}
