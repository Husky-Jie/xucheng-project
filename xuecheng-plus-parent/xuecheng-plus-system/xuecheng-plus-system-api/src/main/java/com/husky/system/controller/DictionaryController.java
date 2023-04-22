package com.husky.system.controller;

import com.husky.system.model.entity.Dictionary;
import com.husky.system.service.IDictionaryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/8
 * Time: 15:34
 * Description: 描述该类的功能
 */
@Slf4j
@Api(value = "词典相关接口", tags = "词典相关接口")
@RestController
public class DictionaryController {

    @Autowired
    private IDictionaryService iDictionaryService;

    @GetMapping("/dictionary/all")
    public List<Dictionary> queryAll() {
        return iDictionaryService.queryAll();
    }

    @GetMapping("/dictionary/code/{code}")
    public Dictionary getByCode(@PathVariable String code) {
        return iDictionaryService.getByCode(code);
    }
}
