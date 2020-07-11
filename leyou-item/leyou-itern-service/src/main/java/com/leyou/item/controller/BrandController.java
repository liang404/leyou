package com.leyou.item.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/23 15:25
 */
@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy",defaultValue = "id")String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc
    ){
        PageResult<Brand> result=this.brandService.queryBrandsByPage(key,page,rows,sortBy,desc);
        if (CollectionUtils.isEmpty(result.getItems())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
    /**
     * 新增品牌
     * @param brand
     * @param cids
     */

    @PostMapping//因为请求路径是：http://api.leyou.com/api/item/brand，所以方法上没有路径
    public ResponseEntity<Void> saveBrand( Brand brand, @RequestParam("cids") List<Long> cids){
        cids.forEach(cid->{
            System.out.println(cid);
        });
        System.out.println(brand);
        this.brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> edit(Brand brand, @RequestParam("cids") List<Long> cids){
        this.brandService.editBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/delete/{bid}")
    public ResponseEntity<Void> delete(@PathVariable("bid") Long bid){
        this.brandService.deleteBrandById(bid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据cid查询品牌列表
     * @param cid
     * @return
     */

    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid(@PathVariable(value = "cid") Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand=this.brandService.queryBrandsById(id);
        if (brand ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }




}
