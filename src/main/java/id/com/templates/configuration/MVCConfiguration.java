package id.com.templates.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableWebMvc
@Configuration
@ComponentScan("id.com.templates")
public class MVCConfiguration extends WebMvcConfigurerAdapter {
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler(new String[]{"/asset/**"}).addResourceLocations(new String[]{"asset/"});
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addRedirectViewController("/", "/login.zul");
    }
}
