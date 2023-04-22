package com.husky.content.api;

import com.husky.base.exception.CommonError;
import com.husky.base.exception.RestErrorResponse;
import com.husky.base.exception.XueChengException;
import com.husky.base.model.R;
import com.husky.model.dto.SaveTeachplanDto;
import com.husky.model.dto.TeachplanDto;
import com.husky.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/12
 * Time: 16:41
 * Description: 课程管理计划的接口
 */
@Slf4j
@Api(value = "课程计划编辑接口",tags = "课程计划编辑接口")
@RestController
public class TeachplanController {

    @Autowired
    private TeachplanService teachplanService;

    // 查询课程计划
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    @ApiOperation("查询课程计划树形结构")
    @ApiImplicitParam(value = "courseId",name = "课程Id",required = true,dataType = "Long",paramType = "path")
    public List<TeachplanDto> teachplanDto(@PathVariable("courseId") Long courseId) {
        List<TeachplanDto> teachPlanDtos = teachplanService.queryTeachPlan(courseId);
        return teachPlanDtos;
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachPlan(@RequestBody SaveTeachplanDto saveTeachplanDto){
        teachplanService.addOrEditTeachPlan(saveTeachplanDto);
    }

    @ApiOperation("删除课程计划")
    @DeleteMapping("/teachplan/{id}")
    public R<String> deleteTeachPlan(@PathVariable Long id){
        Boolean removeTeachPlan = teachplanService.removeTeachPlan(id);
        if (removeTeachPlan) {
            return R.success(null, "200", "删除课程计划成功");
        }else {
            throw new XueChengException(CommonError.HAS_CHILDNODES.getErrMessage());
//            return new RestErrorResponse(CommonError.HAS_CHILDNODES.getErrCode(), CommonError.HAS_CHILDNODES.getErrMessage());
        }
    }

    @ApiOperation("课程计划排序-向下移动")
    @PostMapping("/teachplan/movedown/{id}")
    public void moveDown(@PathVariable Long id) {
        teachplanService.moveDown(id);
    }

    @ApiOperation("课程计划排序-向上移动")
    @PostMapping("/teachplan/moveup/{id}")
    public void moveUp(@PathVariable Long id) {
        teachplanService.moveUp(id);
    }
}
