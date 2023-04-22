package com.husky.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/** 数据字典 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dictionary")
public class Dictionary {
    private static final long serialVersionUID = 1L;
    private String code;
    @TableId()
    private Long id;
    private String itemValues;
    private String name;
    
    public Dictionary(Dictionary dictionary) {
        if (Objects.nonNull(dictionary)) {
            this.code=dictionary.code;
            this.id=dictionary.id;
            this.itemValues=dictionary.itemValues;
            this.name=dictionary.name;
        }
    }
}
