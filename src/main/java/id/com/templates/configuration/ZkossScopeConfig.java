package id.com.templates.configuration;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zkoss.spring.web.context.request.ComponentScope;
import org.zkoss.spring.web.context.request.DesktopScope;
import org.zkoss.spring.web.context.request.ExecutionScope;
import org.zkoss.spring.web.context.request.IdSpaceScope;
import org.zkoss.spring.web.context.request.PageScope;

@Configuration
public class ZkossScopeConfig {
    @Bean
    public static DesktopScope desktopScope(){
        return new DesktopScope();
    }

    @Bean
    public static PageScope pageScope(){
        return new PageScope();
    }

    @Bean
    public static IdSpaceScope idSpaceScope(){
        return new IdSpaceScope();
    }

    @Bean
    public static ComponentScope componentScope(){
        return new ComponentScope();
    }

    @Bean
    public static ExecutionScope executionScope(){
        return new ExecutionScope();
    }

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer(){
        CustomScopeConfigurer scopeConfigurer = new CustomScopeConfigurer();
        scopeConfigurer.addScope("desktop",desktopScope());
        scopeConfigurer.addScope("idspace",idSpaceScope());
        scopeConfigurer.addScope("page",pageScope());
        scopeConfigurer.addScope("component",componentScope());
        scopeConfigurer.addScope("execution", executionScope());
        return scopeConfigurer;
    }
}