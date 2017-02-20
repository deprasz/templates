package id.com.templates;

import id.com.templates.configuration.LoggingFilter;
import id.com.templates.configuration.SessionListenerInit;
import id.com.templates.service.ScheduleService;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public abstract class AbstractInitializer implements WebApplicationInitializer{

	public abstract Class getMVCConfigurationClass();

    public abstract Class getConfigurationClass();

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(getConfigurationClass());
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(new RequestContextListener());
        context.register(getMVCConfigurationClass());
        DispatcherServlet servletDispatcher = new DispatcherServlet(context);
        servletDispatcher.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", servletDispatcher);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");

        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        securityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        FilterRegistration.Dynamic loggingFilter = servletContext.addFilter("loggingFilter", LoggingFilter.class);
        loggingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        ServletRegistration.Dynamic jasperReportsImageServlet = servletContext.addServlet("JasperReportsImageServlet", new ImageServlet());
        jasperReportsImageServlet.addMapping("/jriservlet/image");
        servletContext.addListener(new HttpSessionEventPublisher());
        servletContext.addListener(new SessionListenerInit());
    }


}
