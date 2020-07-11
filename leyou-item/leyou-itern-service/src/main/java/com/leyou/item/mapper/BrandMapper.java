package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author liang
 * @create 2020/4/23 15:22
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand(category_id,brand_id) values(#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);


    @Delete("")
    void deleteCategoryByBid(Long bid);

    @Select("select * from tb_brand a  inner join tb_category_brand b on a.id = b.brand_id where b.category_id = #{cid}")
    List<Brand> queryBrandsByCid(Long cid);
}
