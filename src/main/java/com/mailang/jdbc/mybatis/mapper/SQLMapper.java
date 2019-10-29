package com.mailang.jdbc.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.mailang.jdbc.mybatis.bean.DBean;
import com.mailang.jdbc.mybatis.sqlprovider.SQLProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * 原生SQL执行Mapper对象：1，查询  2，无数据返回SQL执行
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public interface SQLMapper
{
    @SelectProvider(type=SQLProvider.class, method="getSql")
    List<Map<String, Object>> queryBySql(DBean reqBean);
    
    @SelectProvider(type=SQLProvider.class, method="getSql")
    void executeSql(DBean reqBean);
}
