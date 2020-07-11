package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/22 14:38
 */
public interface CategoryService {


    List<Category> queryCategoryByPid(Long pid);

    List<Category> queryByBrandId(Long bid);

    List<String> queryNameByIds(List<Long> ids);




}
