package com.husky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.husky.model.dto.CourseCategoryTreeDto;
import com.husky.model.entity.CourseCategory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/8
 * Time: 16:34
 * Description: 描述该类的功能
 */
public interface CourseCategoryService extends IService<CourseCategory> {

    /**
     * 课程分类树形结构查询
     *
     * @return
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
