package com.leyou.search.client;

import com.leyou.LeyouSearchApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author liang
 * @create 2020/6/20 15:18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouSearchApplication.class)
public class CategoryClientTest {
    @Autowired
    private CategoryClient categoryClient;
    @Test
    public void testQueryCategories(){
        List<String> strings = this.categoryClient.queryNamesByIds(Arrays.asList(1L, 2L, 3L));
        strings.forEach(System.out::println);
    }
}