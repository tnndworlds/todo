package com.mailang.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * JSON操作类
 * 
 * @Description: [一句话描述该类的功能]
 * @Author:      [c00241496]
 * @Date:        [2017/11/23]
 * @Version:     [iManagerU2000V200R018C10]  
 */
public class JSONUtils
{
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static XmlMapper xmlMapper = new XmlMapper();

    static {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * 对象转字符串
     * @param obj object
     * @return objectStr
     */
    public static String objToString(Object obj)
    {
        try
        {
            if (null == obj)
            {
                return "";
            }
            return objectMapper.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     * XML To Pojo
     * @param xmlFile
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T xmlToPojo(File xmlFile, Class<T> clazz)
    {
        try
        {
            XMLInputFactory f = XMLInputFactory.newFactory();
            XMLStreamReader sr = f.createXMLStreamReader(new FileInputStream(xmlFile));
            return xmlMapper.readValue(xmlFile, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }


    public static JSONObject xmlToJson(String xmlPath)
    {
        StringWriter sw = new StringWriter();
        try
        {
            JsonParser jp = xmlMapper.getFactory().createParser(new File(xmlPath));
            JsonGenerator jg = objectMapper.getFactory().createGenerator(sw);
            while(jp.nextToken() != null){
                jg.copyCurrentEvent(jp);
            }
            jp.close();
            jg.close();
            return JSONObject.fromObject(sw.toString());
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static <T> List<T> xmlToPojo(String jsonStr, TypeReference<List<T>> clazz)
    {
        try
        {
            return xmlMapper.readValue(jsonStr, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static <T> List<T> readValue(String jsonStr, TypeReference<List<T>> clazz)
    {
        try
        {
            return objectMapper.readValue(jsonStr, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static <T> List<T> readValue(File xmlFile, TypeReference<List<T>> clazz)
    {
        try
        {
            XMLInputFactory f = XMLInputFactory.newFactory();
            XMLStreamReader sr = f.createXMLStreamReader(new FileInputStream(xmlFile));
            sr.next();

            return xmlMapper.readValue(sr, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static <T> T readValue(String jsonStr, Class<T> clazz)
    {
        try
        {
            return objectMapper.readValue(jsonStr, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static <T> T readValue(byte[] jsonBytes, Class<T> clazz)
    {
        try
        {
            return objectMapper.readValue(jsonBytes, clazz);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static byte[] toJsonBytes(Object obj)
    {
        try
        {
            return objectMapper.writeValueAsBytes(obj);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static JSONObject getJSONByKeyPath(JSONObject dataObj, String keyPath)
    {
        try
        {
            String[] keys = keyPath.split("\\.");
            JSONObject tmpReqObj = dataObj;
            for (int i = 0; i < keys.length; i++)
            {
                tmpReqObj = tmpReqObj.getJSONObject(keys[i]);
            }
            return tmpReqObj;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static JSONArray getArrayByKeyPath(JSONObject dataObj, String keyPath)
    {
        try
        {
            String[] keys = keyPath.split("\\.");
            JSONObject tmpReqObj = dataObj;
            int i = 0;
            for (; i < keys.length - 1; i++)
            {
                tmpReqObj = tmpReqObj.getJSONObject(keys[i]);
            }

            Object keyValue = tmpReqObj.get(keys[i]);
            if (keyValue instanceof JSONObject)
            {
                JSONArray retArray = new JSONArray();
                retArray.add(keyValue);
                return retArray;
            }
            else
            {
                return tmpReqObj.getJSONArray(keys[i]);
            }
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Object getValueByKeyPath(JSONObject dataObj, String keyPath)
    {
        try
        {
            String[] keys = keyPath.split("\\.");
            JSONObject tmpReqObj = dataObj;
            int i = 0;
            for (; i < keys.length - 1; i++)
            {
                tmpReqObj = tmpReqObj.getJSONObject(keys[i]);
                if (tmpReqObj.isNullObject())
                {
                    return null;
                }
            }
            if (tmpReqObj.isNullObject())
            {
                return null;
            }
            return tmpReqObj.get(keys[i]);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}