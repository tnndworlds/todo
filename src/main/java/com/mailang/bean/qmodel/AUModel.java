package com.mailang.bean.qmodel;

import java.util.List;
import java.util.Map;

/**
 * UI--新增或更新使用数据模型
 * 作者： chengsongsong
 * 类名：org.sony.request.modelAUModel
 * 版本：[V01R01C01]
 * 日期：2017年2月23日
 */
public class AUModel
{
	private String type;
	
	/**
	 * 联合主键使用逗号隔开
	 */
	private String primaryKey;
	
	private boolean isDBColumn;

	private Map<String, Object> data;

	private List<Map<String, Object>> dataList;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey)
	{
		this.primaryKey = primaryKey;
	}

	public boolean isDBColumn()
	{
		return isDBColumn;
	}

	public void setDBColumn(boolean isDBColumn)
	{
		this.isDBColumn = isDBColumn;
	}

	public List<Map<String, Object>> getDataList()
	{
		return dataList;
	}

	public void setDataList(List<Map<String, Object>> dataList)
	{
		this.dataList = dataList;
	}

	public Map<String, Object> getData()
	{
		return data;
	}

	public void setData(Map<String, Object> data)
	{
		this.data = data;
	}
}
