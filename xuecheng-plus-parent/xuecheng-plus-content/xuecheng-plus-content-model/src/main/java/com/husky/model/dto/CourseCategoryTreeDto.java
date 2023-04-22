package com.husky.model.dto;

import com.husky.model.entity.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/8
 * Time: 16:45
 * Description: 描述该类的功能
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    List<CourseCategoryTreeDto> childrenTreeNodes;
}
