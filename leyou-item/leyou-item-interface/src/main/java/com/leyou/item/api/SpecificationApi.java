package com.leyou.item.api;


import com.leyou.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author liang
 * @create 2020/5/23 20:58
 */
@RequestMapping("spec")
public interface SpecificationApi {


    /**
     * 根据gid查询参数组
     * @param gid
     * @return
     */

    @GetMapping("/params")
    public List<SpecParam> QueryParam(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "generic",required = false) Boolean generic,
            @RequestParam(value = "searching",required = false) Boolean searching
    );
}
