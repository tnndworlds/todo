package com.mailang.bean.qmodel;

import com.mailang.jdbc.mybatis.bean.QCondition;

import java.util.List;


/**
 * UI--查询/删除使用Model
 * 作者： chengsongsong
 * 类名：org.sony.request.modelQModel
 * 版本：[V01R01C01]
 * 日期：2017年2月23日
 */
public class QModel
{
	private String type;
	
	private String isDBColumn = "false";
	
	private List<QCondition> conList;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Boolean getIsDBColumn()
	{
		return Boolean.valueOf(isDBColumn);
	}

	public void setIsDBColumn(String isDBColumn)
	{
		this.isDBColumn = isDBColumn;
	}

	public List<QCondition> getConList()
	{
		return conList;
	}

	public void setConList(List<QCondition> conList)
	{
		this.conList = conList;
	}

	@Override
	public String toString()
	{
		return "QModel{" + "type='" + type + '\'' + ", isDBColumn=" + isDBColumn + ", conList=" + conList + '}';
	}
}
