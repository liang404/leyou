package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liang
 * @create 2020/5/23 20:58
 */
@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> QueryGroupByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> groups=specificationService.queryGroupByCid(cid);
        if (CollectionUtils.isEmpty(groups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groups);
    }

    /**
     * 根据gid查询参数组
     * @param gid
     * @return
     */

    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> QueryParam(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "generic",required = false) Boolean generic,
            @RequestParam(value = "searching",required = false) Boolean searching
    ){
        List<SpecParam> params=this.specificationService.queryParam(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 修改参数组信息
     * @param specGroup
     * @return
     */
    @PutMapping("/group")
    public ResponseEntity<Void> EditGroup(@RequestBody SpecGroup specGroup){
        System.out.println(specGroup);
        if (ObjectUtils.isEmpty(specGroup)) {
            System.out.println("失败");
            return ResponseEntity.badRequest().build();
        }
        this.specificationService.editGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 新增参数组信息
     * @param specGroup
     * @return
     */
    @PostMapping("/group")
    public ResponseEntity<Void> AddGroup(@RequestBody SpecGroup specGroup){
        if (ObjectUtils.isEmpty(specGroup)) {
            System.out.println("失败");
            return ResponseEntity.badRequest().build();
        }
        this.specificationService.addGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 删除参数组信息
     * @param gid
     * @return
     */
    @DeleteMapping("/group/{gid}")
    public ResponseEntity<Void> DeleteGroup(@PathVariable(value = "gid") Long gid){
        this.specificationService.deleteGroup(gid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
