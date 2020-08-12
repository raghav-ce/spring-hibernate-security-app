package com.project.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.aop.target.CommonsPool2TargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.project.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties")
public class DemoAppConfig {
	
	//Variable to hold the properties
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	
	//define a bean for view resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	//Define a bean for datasource
	@Bean
	public DataSource securityDataSource() {
		
		//Create the connection Pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		//Logging the connection props
		logger.info(">>>jdbc URL: "+env.getProperty("jdbc.url"));
		logger.info(">>>jdbc User: "+env.getProperty("jdbc.user"));
		
		//Set the database connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		//Set the connection pool
		securityDataSource.setInitialPoolSize(toInt("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(toInt("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(toInt("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(toInt("connection.pool.maxIdleTime"));
		
		
		return securityDataSource;
	}

	//helper method for parsing int
	public int toInt(String propValue) {
		String propVal = env.getProperty(propValue);
		return Integer.parseInt(propVal);
	}
}
