package com.leyou.item.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/22 14:42
 */
@RequestMapping("category")
public interface CategoryApi {

    @GetMapping
    public List<String> queryNamesByIds(@RequestParam("ids")List<Long> ids);

}
