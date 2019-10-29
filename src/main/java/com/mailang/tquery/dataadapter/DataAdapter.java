package com.mailang.tquery.dataadapter;

import com.mailang.tquery.bean.ContentBean;

import java.util.List;
import java.util.Map;


public interface DataAdapter
{
	public Object adapter(ContentBean contentBean, List<Map<String, Object>> dataList);
}
