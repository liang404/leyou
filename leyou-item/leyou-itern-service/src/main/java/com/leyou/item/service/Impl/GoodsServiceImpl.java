package com.leyou.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jcajce.provider.digest.Skein;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @create 2020/5/24 14:50
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMappper stockMappper;

    @Override
    public PageResult<SpuBo> querySpuBo(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //过滤查询条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        //过滤是否为上架下架查询条件
        if (saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
//        分页
        PageHelper.startPage(page,rows);
//        查询Spu集合
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
//        把Spu集合转化为SpuBo集合
        List<SpuBo> spuBos = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            //把源属性复制到目标属性中
            BeanUtils.copyProperties(spu, spuBo);
            //查询品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            //查询分类名称
            List<String> names = this.categoryService.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names,"-"));
            return spuBo;
        }).collect(Collectors.toList());
//        返回PageResult<SpuBo>
        return new PageResult<>(pageInfo.getTotal(),spuBos);
    }

    @Override
    public void saveGoods(SpuBo spuBo) {
        //先新增spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        //新增spu_detail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);


        saveSkuAndStock(spuBo);
    }


    /**
     * 使用ctrl+alt+M可以选中一段代码变成一个方法
     * @param spuBo
     */
    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus().forEach(sku -> {
            //新增sku
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            //新增stock
            Stock stock =new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMappper.insert(stock);
        });
    }

    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.spuDetailMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku recode = new Sku();
        recode.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(recode);
        skus.forEach(sku -> {
            Stock stock = this.stockMappper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        });
        return skus;
    }

    @Override
    public void updateGoods(SpuBo spuBo) {
        Sku record = new Sku();
        record.setSpuId(spuBo.getId());
//        根据Sup_id查询要删除的sku
        List<Sku> skus = this.skuMapper.select(record);
        skus.forEach(sku -> {
            //删除stock
            this.stockMappper.deleteByPrimaryKey(sku.getId());
            //删除sku
            this.skuMapper.deleteByPrimaryKey(sku.getId());
        });
        //新增sku和stock
        this.saveSkuAndStock(spuBo);

        //更新spu和spuDetail,设置为null就是不修改数据库的数据
        spuBo.setCreateTime(null);
        spuBo.setSaleable(null);
        spuBo.setSaleable(null);
        spuBo.setLastUpdateTime(new Date());
        this.spuMapper.updateByPrimaryKeySelective(spuBo);

        this.spuDetailMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }
}
