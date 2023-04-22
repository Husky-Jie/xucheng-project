package com.husky.model.dto;

import com.husky.model.entity.Teachplan;
import com.husky.model.entity.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/12
 * Time: 16:38
 * Description: 描述该类的功能
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {

    // 与媒资管理的信息
    private TeachplanMedia teachplanMedia;

     // 小章节list
    private List<TeachplanDto> teachPlanTreeNodes;
}
