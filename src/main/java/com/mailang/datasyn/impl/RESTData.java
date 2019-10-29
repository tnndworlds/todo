package com.mailang.datasyn.impl;

import com.mailang.bean.URLBean;
import com.mailang.datasyn.DataSynchronized;
import com.mailang.utils.SpringUtils;
import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RESTData extends DataSynchronized
{
    private static RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);

    @Override
    protected JSONObject getResponse(URLBean urlBean, Map<String, Object> paramMap)
    {
        return null;
    }
}
