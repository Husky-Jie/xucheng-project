package com.husky.model.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/12
 * Time: 18:14
 * Description: TODO
 */
@Data
@ToString
public class SaveTeachplanDto {

    /***
     * 教学计划id
     */
    @NotEmpty(message = "教学计划id不能为空")
    private Long id;

    /**
     * 课程计划名称
     */
    private String pname;

    /**
     * 课程计划父级Id
     */
    @NotEmpty(message = "课程计划父级Id不能为空")
    private Long parentid;

    /**
     * 层级，分为1、2、3级
     */
    @NotEmpty(message = "层级不能为空")
    private Integer grade;

    /**
     * 课程类型:1视频、2文档
     */
    private String mediaType;


    /**
     * 课程标识
     */
    @NotEmpty(message = "课程标识不能为空")
    private Long courseId;

    /**
     * 课程发布标识
     */
    private Long coursePubId;


    /**
     * 是否支持试学或预览（试看）
     */
    private String isPreview;

}
