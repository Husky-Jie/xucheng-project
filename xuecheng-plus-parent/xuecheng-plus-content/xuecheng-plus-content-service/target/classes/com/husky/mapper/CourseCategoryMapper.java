package com.husky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.husky.model.dto.CourseCategoryTreeDto;
import com.husky.model.entity.CourseCategory;

import java.util.List;


public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
