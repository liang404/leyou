package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author liang
 * @create 2020/6/8 11:42
 */
public interface GoodsApi {
    /**
     * 根据SpuId查询商品详情（SpuDetail）
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);

    @GetMapping("/spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "page",required = true) Integer page,
            @RequestParam(value = "rows",required = true) Integer rows
    );

    /**
     * 根据spuId获取Sku的信息
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id")Long spuId);

}
