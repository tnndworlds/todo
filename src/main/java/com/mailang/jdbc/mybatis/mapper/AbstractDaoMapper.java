package com.mailang.jdbc.mybatis.mapper;

import com.mailang.jdbc.mybatis.bean.DBean;
import com.mailang.jdbc.mybatis.sqlprovider.SQLProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 实体类访问数据库Mapper对象，为AbstractDao提供SQL以及执行
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public interface AbstractDaoMapper
{
	@SelectProvider(type=SQLProvider.class, method="save")
    void save(DBean reqBean);
	
	@SelectProvider(type=SQLProvider.class, method="queryById")
    Map<String, Object> queryById(DBean reqBean);

    @SelectProvider(type=SQLProvider.class, method="queryByConList")
    List<Map<String, Object>> queryByConList(DBean reqBean);

    @SelectProvider(type=SQLProvider.class, method="update")
    List<Map<String, Object>> update(DBean reqBean);

    @SelectProvider(type=SQLProvider.class, method="deleteById")
    void deleteById(DBean reqBean);

    @SelectProvider(type=SQLProvider.class, method="delete")
    void delete(DBean reqBean);
}
