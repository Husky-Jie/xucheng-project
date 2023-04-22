package com.husky.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/15
 * Time: 23:07
 * Description: 添加师资信息
 */
@Data
@ApiModel(value="AddCourseTeacherDto", description="添加师资信息")
public class AddCourseTeacherDto {

    @NotEmpty(message = "课程id不能为空")
    @ApiModelProperty(name = "课程id", required = true)
    private Long courseId;

    @ApiModelProperty(name = "教师简介")
    private String introduction;

    @NotEmpty(message = "教师职位不能为空")
    @ApiModelProperty(name = "教师职位", required = true)
    private String position;

    @NotEmpty(message = "教师标识不能为空")
    @ApiModelProperty(name = "教师标识", required = true)
    private String teacherName;

    @ApiModelProperty(name = "教师照片")
    private String photograph;
}
