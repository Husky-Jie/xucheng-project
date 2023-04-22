package com.husky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.husky.model.dto.AddCourseDto;
import com.husky.model.entity.CourseMarket;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/9
 * Time: 16:07
 * Description: 描述该类的功能
 */
public interface CourseMarketService extends IService<CourseMarket> {

    // 课程营销新增接口
    void addCourseMarket(AddCourseDto addCourseDto, Long id);
}
