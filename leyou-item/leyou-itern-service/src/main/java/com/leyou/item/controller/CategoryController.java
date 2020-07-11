package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/22 14:42
 */
@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点的id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> selectAll(@RequestParam(value = "pid",defaultValue = "0")Long pid){
//        try {
            if (pid == null || pid < 0) {
//            HttpStatus.BAD_REQUEST,相当于400:参数不合法，错误请求，意思就是响应一个错误请求返回给浏览器
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                return ResponseEntity.badRequest().build();
            }
            List<Category> categories = this.categoryService.queryCategoryByPid(pid);
            if (CollectionUtils.isEmpty(categories)) {
                //404:资源服务器未找到
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//                return ResponseEntity.notFound().build();
            }
            //200：说明查询成功
            return ResponseEntity.ok(categories);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //500：服务器内部错误,本来就会响应500，可以忽略
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("add")
    public ResponseEntity<Void> addCategory(@RequestBody Category category){
        System.out.println(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid") Long bid){
        //根据bid查询此商品的所有分类
        List<Category> list=this.categoryService.queryByBrandId(bid);
        if (list==null||list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names=this.categoryService.queryNameByIds(ids);
        if (CollectionUtils.isEmpty(names)) {
            //404:资源服务器未找到
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//                return ResponseEntity.notFound().build();
        }
        //200：说明查询成功
        return ResponseEntity.ok(names);
    }

}
