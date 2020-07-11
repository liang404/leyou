package com.leyou.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/23 15:24
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
       //初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter","key");
        }
        //根据查询条件查询：模糊查询，按首字母查询

        //添加分页条件
        PageHelper.startPage(page,rows);
        //添加排序条件
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc ? "desc":"asc"));
        }
        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装成为pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        //包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */

    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //先新增品牌
        this.brandMapper.insertSelective(brand);
        //新增中间表
        cids.forEach(cid->{
            //因为中间表无法使用通用mapper
            this.brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
    }

    @Override
    public void editBrand(Brand brand, List<Long> cids) {
        this.brandMapper.updateByPrimaryKey(brand);
        //修改List里面

    }

    @Override
    @Transactional
    public void deleteBrandById(Long bid) {
        //删除当前品牌
        this.brandMapper.delete(this.brandMapper.selectByPrimaryKey(bid));
        //删除中间表的关系(首先要将对应的两个id找出来才能删除)
        this.brandMapper.deleteCategoryByBid(bid);
    }

    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.queryBrandsByCid(cid);
    }

    @Override
    public Brand queryBrandsById(Long id) {
        return this.brandMapper.selectByPrimaryKey(id);
    }


}
