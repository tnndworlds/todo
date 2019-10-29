package com.mailang.bean.qmodel;

import java.util.Map;

public class RTemplateModel
{
	private String userId;
	
	private String queryId;
	
	private Map<String, Object> conMap;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getQueryId()
	{
		return queryId;
	}

	public void setQueryId(String queryId)
	{
		this.queryId = queryId;
	}

	public Map<String, Object> getConMap()
	{
		return conMap;
	}

	public void setConMap(Map<String, Object> conMap)
	{
		this.conMap = conMap;
	}
	
}
