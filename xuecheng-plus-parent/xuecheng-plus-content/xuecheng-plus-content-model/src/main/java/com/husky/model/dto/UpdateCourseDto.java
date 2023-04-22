package com.husky.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/10
 * Time: 19:00
 * Description: 描述该类的功能
 */
@Data
@ApiModel(value="UpdateCourseDto", description="更新课程基本信息")
public class UpdateCourseDto extends AddCourseDto{

    @NotEmpty(message = "id不能为空")
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
