package com.mailang.tquery.bean;


import com.mailang.tquery.uenum.GetTypeEnum;

public class ContentBean
{
	private String key;
	private GetTypeEnum getType;
	private String value;
	private String dataAdapter;
	private String sysParam;
	private String plugin;

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public GetTypeEnum getGetType()
	{
		return getType;
	}

	public void setGetType(GetTypeEnum getType)
	{
		this.getType = getType;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getDataAdapter()
	{
		return dataAdapter;
	}

	public void setDataAdapter(String dataAdapter)
	{
		this.dataAdapter = dataAdapter;
	}

	public String getSysParam()
	{
		return sysParam;
	}

	public void setSysParam(String sysParam)
	{
		this.sysParam = sysParam;
	}

	public String getPlugin()
	{
		return plugin;
	}

	public void setPlugin(String plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public String toString()
	{
		return "ContentBean [key=" + key + ", getType=" + getType + ", value=" + value + ", dataAdapter=" + dataAdapter
				+ "]";
	}
}
