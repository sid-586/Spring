package ru.sd;

import org.apache.log4j.Logger;
import org.h2.server.web.WebServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ru.sd.app.config.AppContextConfig;
import ru.sd.web.config.WebContextConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletContext;

public class WebAppInitializer implements WebApplicationInitializer {
    private final Logger logger =
            Logger.getLogger(WebAppInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        logger.info("AppConfig creating");
        AnnotationConfigWebApplicationContext appContext =
                new AnnotationConfigWebApplicationContext();
        servletContext.addListener(new ContextLoaderListener(appContext));
        appContext.register(AppContextConfig.class);

        logger.info("WebConfig creating");
        AnnotationConfigWebApplicationContext webContext =
                new AnnotationConfigWebApplicationContext();
        webContext.register(WebContextConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);

        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        logger.info("dispatcher ready");

        ServletRegistration.Dynamic dbServlet = servletContext.addServlet("h2" +
                "-console", new WebServlet());
        dbServlet.setLoadOnStartup(2);
        dbServlet.addMapping("/console/*");
        logger.info("H2 ready");
    }
}
