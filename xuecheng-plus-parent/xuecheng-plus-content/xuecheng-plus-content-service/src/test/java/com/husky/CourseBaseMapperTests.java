package com.husky;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.husky.mapper.CourseBaseMapper;
import com.husky.model.entity.CourseBase;
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
public class CourseBaseMapperTests {

    @Autowired
    private CourseBaseMapper courseBaseMapper;
    @Test
    void testCourseBaseMapper() {
        CourseBase courseBase = courseBaseMapper.selectById(1L);
        System.out.println(courseBase);
    }

    @Test
    void testPageCourseBaseMapper() {
        Page<CourseBase> page = new Page<>(1L, 30L);
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(CourseBase::getCreateDate);
        Page<CourseBase> basePage = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> records = basePage.getRecords();
        System.out.println(records);
        System.err.println("一共"+records.size());
    }
}
