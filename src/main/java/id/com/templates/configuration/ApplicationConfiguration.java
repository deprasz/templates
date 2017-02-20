package id.com.templates.configuration;

import javax.sql.DataSource;

import id.com.templates.service.ScheduleService;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.ejb.HibernatePersistence;
import org.hibernate.envers.strategy.ValidityAuditStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.Collections;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "id.com.templates")
@PropertySource("classpath:/application.properties")
@Import({SecuritiesConfiguration.class})
@EnableJpaRepositories(basePackages = {"id.com.templates"})
@EnableJpaAuditing
@EnableScheduling
public class ApplicationConfiguration {
    @Autowired
    Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();

    }

    @Bean
    DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(env.getRequiredProperty("jdbc.password"));
        return dataSource;
    }


    @Bean
    JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    PlatformTransactionManager platformTransactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name="entityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactory(){
    	LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
    	HibernateJpaVendorAdapter jpaVendor = new HibernateJpaVendorAdapter();
    	jpaVendor.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
    	jpaVendor.setShowSql(env.getRequiredProperty("hibernate.show.sql").equals("1") ? true : false);
    	entityManager.setDataSource(dataSource());
        entityManager.setJpaPropertyMap(jpaProperties());
    	entityManager.setPersistenceProvider(new HibernatePersistence());
    	entityManager.setJpaVendorAdapter(jpaVendor);
    	entityManager.setPackagesToScan("id.com.templates");
    	return entityManager;
    }

    @Bean(name="transactionManager")
    PlatformTransactionManager jpaTransactionManager(){
    	return new JpaTransactionManager(entityManagerFactory().getObject());
    }
    private Map<String, String> jpaProperties() {
        return Collections.singletonMap("org.hibernate.envers.audit_strategy", ValidityAuditStrategy.class.getName());
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

  }
