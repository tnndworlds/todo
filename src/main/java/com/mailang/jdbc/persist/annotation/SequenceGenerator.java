package com.mailang.jdbc.persist.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定实体类属性是否自增长
 * 若指定某属性为自增长，则当该属性为空时自动给该属性赋一个自增长的值。
 * @author     [c00241496]
 * @version    [V1R1C00, 2016年6月21日]
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented  
@Inherited
public @interface SequenceGenerator
{
    
}
