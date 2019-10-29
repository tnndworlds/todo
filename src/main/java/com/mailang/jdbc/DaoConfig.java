package com.mailang.jdbc;

import com.mailang.jdbc.dao.AbstractDao;
import com.mailang.jdbc.mybatis.SQLDao;
import com.mailang.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @Author chengsongsong
 * @Date 2019/9/3
 */
@Configuration
public class DaoConfig
{
    @Autowired
    public ConfigurableApplicationContext applicationContext;

    @Bean
    public SQLDao sqlDao()
    {
        return new SQLDao();
    }

    @PostConstruct
    public void loadDao()
    {
        Set<Class<? extends AbstractDao>> daos = Utils.getAllClass(AbstractDao.class);

        for (Class clazz : daos)
        {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
            beanFactory.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
        }
    }

}
