package com.husky.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.husky.model.dto.SaveTeachplanDto;
import com.husky.model.dto.TeachplanDto;
import com.husky.model.entity.Teachplan;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/12
 * Time: 17:54
 * Description: TODO
 */
public interface TeachplanService extends IService<Teachplan> {

    // 根据课程id查询课程计划
    List<TeachplanDto> queryTeachPlan(Long courseId);

    // 课程计划创建或修改
    void addOrEditTeachPlan(SaveTeachplanDto saveTeachplanDto);

    // 根据id删除课程计划
    Boolean removeTeachPlan(Long id);

    // 课程计划排序-向下移动
    void moveDown(Long id);

    // 课程计划排序-向上移动
    void moveUp(Long id);
}
