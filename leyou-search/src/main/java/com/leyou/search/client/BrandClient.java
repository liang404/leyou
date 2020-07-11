package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author liang
 * @create 2020/6/8 12:01
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
