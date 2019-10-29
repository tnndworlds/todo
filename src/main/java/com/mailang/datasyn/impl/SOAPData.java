package com.mailang.datasyn.impl;

import com.mailang.bean.URLBean;
import com.mailang.datasyn.DataSynchronized;
import com.mailang.utils.FreemarkerAdapter;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class SOAPData extends DataSynchronized
{
    private Logger LOG = LoggerFactory.getLogger(SOAPData.class);

    private XMLSerializer xmlSerializer = new XMLSerializer();

    public SOAPData()
    {
        xmlSerializer.setSkipNamespaces(true);
        xmlSerializer.clearNamespaces();
        xmlSerializer.setRemoveNamespacePrefixFromElements(true);
    }

    @Override
    protected JSONObject getResponse(URLBean urlBean, Map<String, Object> paramMap)
    {
        try
        {
            //参数插件，获取最终的reqBody
            String rawReqBody = urlBean.getRequestBody();
            String reqBody = FreemarkerAdapter.getInstance().getResult(rawReqBody, paramMap);
            if (reqBody == null)
            {
                return null;
            }
            URL url = new URL(urlBean.getUrl());
            URLConnection conn = url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

            // 调用的接口方法是
            conn.setRequestProperty(String.valueOf(paramMap.get("method")), "");
            conn.setRequestProperty("Content-Length", Integer.toString(reqBody.length()));
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            osw.write(reqBody);
            osw.flush();
            osw.close();

            InputStream is = conn.getInputStream();
            String retStr = new String(readInputStream(is), "UTF-8");
            return JSONObject.fromObject(xmlSerializer.read(retStr).toString());
        }
        catch (Exception e)
        {
            LOG.error("Get response from dts system failed. Error: {}.", e);
        }
        return null;
    }

    private byte[] readInputStream(InputStream inStream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
