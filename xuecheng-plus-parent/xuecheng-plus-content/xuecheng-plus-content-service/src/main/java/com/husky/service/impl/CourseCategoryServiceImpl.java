package com.husky.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.husky.mapper.CourseCategoryMapper;
import com.husky.model.dto.CourseCategoryTreeDto;
import com.husky.model.entity.CourseCategory;
import com.husky.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/8
 * Time: 16:35
 * Description: 描述该类的功能
 */
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Autowired
    private CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 将list转map,以备使用,排除根节点
        Map<String, CourseCategoryTreeDto> map = courseCategoryTreeDtos
                .stream()
                .filter(item->!id.equals(item.getId()))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

        // 定义一个list，作为最终结果
        List<CourseCategoryTreeDto> categoryList = new ArrayList<>();

        //依次遍历每个元素,排除根节点
        courseCategoryTreeDtos
                .stream()
                .filter(item->!id.equals(item.getId()))
                .forEach(item->{
                    if (item.getParentid().equals(id)){
                        // 找到所有的父节点，排除根节点
                        categoryList.add(item);
                    }
                    // 找到该节点的父节点
                    CourseCategoryTreeDto courseCategoryParent = map.get(item.getParentid());
                    if (courseCategoryParent != null) {
                        // 如果父节点中的ChildrenTreeNodes属性为空，则new一个集合，向该集合中存放该节点的子节点
                        if (courseCategoryParent.getChildrenTreeNodes() == null) {
                            courseCategoryParent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        }
                        // 存放父节点的字节点
                        courseCategoryParent.getChildrenTreeNodes().add(item);
                    }
                });
        return categoryList;
    }
}
