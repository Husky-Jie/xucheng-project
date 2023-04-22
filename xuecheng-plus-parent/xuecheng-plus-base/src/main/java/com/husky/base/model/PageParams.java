package com.husky.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by IntelliJ IDEA.
 * User: 周圣杰
 * Date: 2023/4/6
 * Time: 17:06
 * Description: 分页查询
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageParams {

    private Long pageNo = 1L;
    private Long pageSize = 30L;

}
