package com.husky.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/6
 * Time: 17:17
 * Description: 课程查询条件类
 */
@Data
@ToString
@ApiModel(value = "课程查询条件类")
public class QueryCourseParamsDto {

    // 审核状态
    @ApiModelProperty("审核状态")
    private String auditStatus;

    // 课程名称
    @ApiModelProperty("课程名称")
    private String courseName;

    // 发步状态
    @ApiModelProperty("发步状态")
    private String publishStatus;
}
