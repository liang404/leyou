package com.leyou.search.client;

import com.leyou.LeyouSearchApplication;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @create 2020/7/7 16:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test(){
        this.elasticsearchTemplate.createIndex(Goods.class);
        this.elasticsearchTemplate.putMapping(Goods.class);

        Integer page =1;
        Integer rows =100;
        do {
//            分页查询，获取当前页数据
            PageResult<SpuBo> result = goodsClient.querySpuByPage(null, null, page, rows);
            List<SpuBo> items = result.getItems();
//            处理List<spuBo>转换为ListGoods
            List<Goods> goodsList = items.stream().map(spuBo -> {
                try {
                    return this.searchService.buildGoods(spuBo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
//            调用新增数据的方法
            this.goodsRepository.saveAll(goodsList);

            rows = items.size();
            page++;
        }while (rows == 100);

    }






}
