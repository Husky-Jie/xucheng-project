package com.husky;

import com.husky.mapper.TeachplanMapper;
import com.husky.model.dto.TeachplanDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/12
 * Time: 17:45
 * Description: 描述该类的功能
 */
@Slf4j
@SpringBootTest
public class TeachplanTest {

    @Autowired
    private TeachplanMapper teachplanMapper;

    @Test
    void name() {

        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTeachplanDto(117L);
        log.info("查询课程计划");
        System.out.println(teachplanDtos);

    }
}
