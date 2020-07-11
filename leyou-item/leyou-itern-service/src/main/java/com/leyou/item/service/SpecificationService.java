package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author liang
 * @create 2020/5/23 20:56
 */
public interface SpecificationService {
    List<SpecGroup> queryGroupByCid(Long cid);

    List<SpecParam> queryParam(Long gid,Long cid,Boolean generic,Boolean searching);

    void editGroup(SpecGroup specGroup);

    void addGroup(SpecGroup specGroup);

    void deleteGroup(Long gid);
}
