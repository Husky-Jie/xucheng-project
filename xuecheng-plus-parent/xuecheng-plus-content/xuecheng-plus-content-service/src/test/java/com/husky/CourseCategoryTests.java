package com.husky;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.husky.mapper.CourseBaseMapper;
import com.husky.mapper.CourseCategoryMapper;
import com.husky.model.dto.CourseCategoryTreeDto;
import com.husky.model.entity.CourseBase;
import com.husky.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/7
 * Time: 16:06
 * Description: 描述该类的功能
 */
@SpringBootTest
public class CourseCategoryTests {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Autowired
    private CourseCategoryService courseCategoryService;

    @Test
    void name() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }

    @Test
    void list() {
        List<CourseCategoryTreeDto> categoryTreeDtoList = courseCategoryService.queryTreeNodes("1");
        System.out.println();
    }
}
