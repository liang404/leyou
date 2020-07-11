package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author liang
 * @create 2020/6/8 12:02
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
