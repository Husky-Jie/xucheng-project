package com.husky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.husky.model.dto.TeachplanDto;
import com.husky.model.entity.Teachplan;

import java.util.List;


public interface TeachplanMapper extends BaseMapper<Teachplan> {

    List<TeachplanDto> selectTeachplanDto(Long courseId);
}
