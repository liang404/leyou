package com.leyou.item.service.Impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liang
 * @create 2020/5/23 20:56
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup record = new SpecGroup();
        record.setCid(cid);
        return this.specGroupMapper.select(record);
    }

    @Override
    public List<SpecParam> queryParam(Long gid,Long cid,Boolean generic,Boolean searching) {
        SpecParam recrod = new SpecParam();
        recrod.setGroupId(gid);
        recrod.setCid(cid);
        recrod.setGeneric(generic);
        recrod.setSearching(searching);
        return this.specParamMapper.select(recrod);
    }

    /**
     * 修改参数组信息
     * @param specGroup
     */
    @Override
    public void editGroup(SpecGroup specGroup) {
        this.specGroupMapper.updateByPrimaryKey(specGroup);
    }
    /**
     * 新增参数组信息
     * @param specGroup
     */
    @Override
    public void addGroup(SpecGroup specGroup) {
        this.specGroupMapper.insert(specGroup);
    }

    /**
     * 删除参数组信息
     * @param gid
     */

    @Override
    public void deleteGroup(Long gid) {
        //删除specGroup
        this.specGroupMapper.deleteByPrimaryKey(gid);

    }
}
