package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/23 15:24
 */
public interface BrandService {
    PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void saveBrand(Brand brand, List<Long> cids);


    void editBrand(Brand brand, List<Long> cids);

    void deleteBrandById(Long bid);

    List<Brand> queryBrandsByCid(Long cid);

    Brand queryBrandsById(Long id);
}
