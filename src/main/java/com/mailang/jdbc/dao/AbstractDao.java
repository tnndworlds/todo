package com.mailang.jdbc.dao;

import com.mailang.cons.ERRCode;
import com.mailang.jdbc.mybatis.bean.DBean;
import com.mailang.jdbc.mybatis.bean.QCondition;
import com.mailang.jdbc.mybatis.mapper.AbstractDaoMapper;
import com.mailang.jdbc.persist.DBUtils;
import com.mailang.jdbc.persist.TableFactory;
import com.mailang.jdbc.persist.meta.ColumnMeta;
import com.mailang.jdbc.persist.meta.TableMeta;
import com.mailang.log.XSLogger;
import com.mailang.xsexception.XSException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体类数据库访问公共抽象方法
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public abstract class AbstractDao<T>
{
	private static XSLogger LOG = XSLogger.getLogger(AbstractDao.class);
	
	@Autowired
	protected AbstractDaoMapper abstractDaoMapper;

	protected DBean dBean = null;

	private Class<T> entityClass;

	public AbstractDao()
	{
		this.entityClass = null;
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType)
		{
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
		dBean = new DBean();
		dBean.setClazz(this.entityClass);
	}

	public AbstractDaoMapper getAbstractDaoMapper()
	{
		return abstractDaoMapper;
	}

	public void setAbstractDaoMapper(AbstractDaoMapper abstractDaoMapper)
	{
		this.abstractDaoMapper = abstractDaoMapper;
	}
	
	public Map<String, Object> queryById(String id)
	{
		dBean.setId(id);
		try
		{
			return this.abstractDaoMapper.queryById(dBean);
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_QUERY_ERROR, ERRCode.FAILED);
		}
	}

	public List<Map<String, Object>> query(List<QCondition> qConditionsList)
	{
		dBean.setConList(qConditionsList);
		try
		{
			return this.abstractDaoMapper.queryByConList(dBean);
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_QUERY_ERROR, ERRCode.FAILED);
		}
	}

	/**
	 * Save Data Map
	 * @param dataMap data
	 * @return 0:保存成功， -1：保存失败
	 */
	public Object save(Map<String, Object> dataMap)
	{
		DBean dbean = new DBean();
		dbean.setClazz(entityClass);
		dbean.setDataMap(dataMap);
		try
		{
			this.abstractDaoMapper.save(dbean);
			return dbean.getPrimaryValue();
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_SAVE_ERROR, ERRCode.FAILED);
		}
	}

	public Object save(T t)
	{
		DBean dbean = new DBean();
		dbean.setClazz(entityClass);
		Map<String, Object> dataMap = DBUtils.beanToMap(entityClass, t);
		if (null == dataMap)
		{
			return dbean.getPrimaryValue();
		}
		dbean.setDataMap(dataMap);
		try
		{
			this.abstractDaoMapper.save(dbean);
			return dbean.getPrimaryValue();
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_SAVE_ERROR, ERRCode.FAILED);
		}
	}

	public int update(Map<String, Object> dataMap)
    {
        DBean dbean = new DBean();
        dbean.setClazz(entityClass);
        dbean.setDataMap(dataMap);
        try
        {
            this.abstractDaoMapper.update(dbean);
            return 0;
        }
        catch (Exception e)
        {
            throw new XSException(ERRCode.DB_UPDATE_ERROR, ERRCode.FAILED);
        }
    }

	public void update(T t)
	{
		DBean dbean = new DBean();
		dbean.setClazz(entityClass);
		Map<String, Object> dataMap = DBUtils.beanToMap(entityClass, t);
		dbean.setDataMap(dataMap);
		try
		{
			this.abstractDaoMapper.update(dbean);
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_UPDATE_ERROR, ERRCode.FAILED);
		}
	}

	public void saveOrUpdate(T t)
	{
		Map<String, Object> dataMap = DBUtils.beanToMap(entityClass, t);
		this.saveOrUpdate(dataMap);
	}

	public List<T> queryRetEntity(List<QCondition> qConditionsList)
	{
		dBean.setConList(qConditionsList);
		try
		{
			List<Map<String, Object>> retList = this.abstractDaoMapper.queryByConList(dBean);
			if (retList == null || retList.isEmpty())
			{
				return new ArrayList<T>();
			}
			return DBUtils.convertToBean(entityClass, retList);
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_QUERY_ERROR, ERRCode.FAILED);
		}
	}

	public void saveOrUpdate(Map<String, Object> dataMap)
	{
		Map<String, Object> conMap = new HashMap<String, Object>();
		TableMeta metaTable = TableFactory.getInstance().getMetaByClazz(this.entityClass.getName());
		List<ColumnMeta> primaryColList = metaTable.getPrimaryKeys();
		List<Map<String, Object>> retList = null;
		boolean existPrimaryKey = false;
		for (ColumnMeta key : primaryColList)
		{
			if (null != dataMap.get(key.getColumnName()))
			{
				conMap.put(key.getColumnName(), dataMap.get(key.getColumnName()));
				existPrimaryKey = true;
			}
		}

		retList = existPrimaryKey ? this.query(DBUtils.getQListFromMap(conMap)) : null;
		if (null == retList || retList.isEmpty())
		{
			this.save(dataMap);
		}
		else
		{
			this.update(dataMap);
		}
	}

	/**
	 * delete Data Map
	 * @return 0:删除成功， -1：删除失败
	 */
	public int deleteById(String id)
	{
		DBean dbean = new DBean();
		dbean.setClazz(entityClass);
		dbean.setId(id);
		try
		{
			this.abstractDaoMapper.deleteById(dbean);
			return 0;
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.DB_SAVE_ERROR, ERRCode.FAILED);
		}
	}

	public void delete(List<QCondition> conditionList)
	{
		DBean dbean = new DBean();
		dbean.setClazz(entityClass);
		dbean.setConList(conditionList);
		try
		{
			this.abstractDaoMapper.delete(dbean);
		}
		catch (Exception e)
		{
			throw new XSException(ERRCode.FAILED);
		}
	}
}
