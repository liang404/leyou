package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author liang
 * @create 2020/7/7 16:14
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
