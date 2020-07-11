package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/23 15:25
 */
@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);



}
