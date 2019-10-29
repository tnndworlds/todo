package com.mailang.tquery.dataadapter;

import com.mailang.cons.XSCons;
import com.mailang.log.XSLogger;
import com.mailang.tquery.bean.ContentBean;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public class RecordToListAdapter implements DataAdapter
{
    private static XSLogger LOG = XSLogger.getLogger(RecordToListAdapter.class);

    @Override
    public Object adapter(ContentBean contentBean, List<Map<String, Object>> dataList)
    {
        if (null == dataList || dataList.isEmpty())
        {
            return null;
        }
        try
        {
            String colStr = contentBean.getDataAdapter().split(XSCons.COLON)[1];
            JSONArray retObj = new JSONArray();
            for (Map<String, Object> data : dataList)
            {
                retObj.add(data.get(colStr));
            }
            return retObj;
        }
        catch (Exception e)
        {
            LOG.error("Convert data failed. Error:{}.", e.getMessage());
        }

        return null;
    }
}
