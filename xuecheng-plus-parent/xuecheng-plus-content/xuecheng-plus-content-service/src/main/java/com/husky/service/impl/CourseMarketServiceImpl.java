package com.husky.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.husky.base.exception.XueChengException;
import com.husky.mapper.CourseMarketMapper;
import com.husky.model.dto.AddCourseDto;
import com.husky.model.entity.CourseMarket;
import com.husky.service.CourseMarketService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/9
 * Time: 16:07
 * Description: 描述该类的功能
 */
@Service
public class CourseMarketServiceImpl extends ServiceImpl<CourseMarketMapper, CourseMarket> implements CourseMarketService {
    @Override
    public void addCourseMarket(AddCourseDto addCourseDto, Long id) {
        // 新增courseMarket对象
        CourseMarket courseMarketNew = new CourseMarket();
        // 将AddCourseDto中的courseMarket信息拷贝进课程营销对象中
        BeanUtils.copyProperties(addCourseDto, courseMarketNew);
        // 设置课程信息id
        courseMarketNew.setId(id);

        // 参数合法性校验
        //收费规则
        String charge = courseMarketNew.getCharge();
        if(StringUtils.isBlank(charge)){
            throw new XueChengException("收费规则没有选择");
        }
        //收费规则为收费
        if(charge.equals("201001")){
            if(courseMarketNew.getPrice() == null || courseMarketNew.getPrice()<=0){
                throw new XueChengException("课程为收费价格不能为空且必须大于0");
            }else if (courseMarketNew.getOriginalPrice() == null || courseMarketNew.getOriginalPrice() <= 0) {
                throw new XueChengException("课程为收费价格不能为空且必须大于0");
            }
        }

        // 根据id从课程营销表查询, 存在则更新， 反之则新增
        CourseMarket courseMarket = this.getById(courseMarketNew.getId());
        if (courseMarket == null){
            // 存入course_market表中
            boolean save = this.save(courseMarketNew);
            // 判断新增课程营销信息是否新增成功
            if (!save) {
                throw new XueChengException("新增课程营销信息失败");
            }
        }else {
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            courseMarket.setId(courseMarketNew.getId());
            boolean updateById = this.updateById(courseMarket);
            if (!updateById) {
                throw new XueChengException("更新课程营销信息失败");
            }
        }
    }
}
