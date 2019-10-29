package com.mailang.jdbc.persist.meta;


public enum DataType
{
    STRING(1),
    INTEGER(2),
    BOOLEAN(3),
    LONG(4),
    FLOAT(5);
    
    private final static int STRING_TYPE = 1;
    
    private final static int INTEGER_TYPE = 2;
    
    private final static int BOOLEAN_TYPE = 3;
    
    private final static int LONG_TYPE = 4;
    
    private final static int FLOAT_TYPE = 5;
    
    private int dataType;
    private DataType(int dataType)
    {
        this.dataType = dataType;
    }
    
    /** 
     * 获取当前属性对应的类
     * @return
     * @throws InnerException
     * @see [类、类#方法、类#成员]
     */
    public Class< ? > getType()
    {
        Class< ? > clazz = null;
        switch (this.dataType)
        {
            case STRING_TYPE:
            {
                clazz = String.class;
                break;
            }
            case INTEGER_TYPE:
            {
                clazz = Integer.class;
                break;
            }
            case BOOLEAN_TYPE:
            {
                clazz = Boolean.class;
                break;
            }
            case LONG_TYPE:
            {
                clazz = Long.class;
                break;
            }
            case FLOAT_TYPE:
            {
                clazz = Float.class;
                break;
            }
            default:
                return null;
        }
        return clazz;
    }
    
    /** 
     * 获取对应的属性类型对象
     * @param clazz
     * @return
     * @throws InnerException
     * @see [类、类#方法、类#成员]
     */
    public static DataType getDataType(Class< ? > clazz)
    {
        DataType type = null;
        
        if (clazz.equals(String.class))
        {
            type = DataType.STRING;
        }
        else if (clazz.equals(Integer.class))
        {
            type = DataType.INTEGER;
        }
        else if (clazz.equals(Boolean.class))
        {
            type = DataType.BOOLEAN;
        }
        else if (clazz.equals(Long.class))
        {
            type = DataType.LONG;
        }
        else if (clazz.equals(Float.class))
        {
            type = DataType.FLOAT;
        }
        else
        {
            return null;
        }
        return type;
    }
}
