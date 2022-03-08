package com.library.lendit_book_kiosk.Security.Config;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk.User"})
public class WebApplicationConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext ctx;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
    }

    /**
     * Stores registrations of resource handlers for serving static resources
     * such as images, css files and others through Spring MVC including setting
     * cache headers optimized for efficient loading in a web browser.
     * Resources can be served out of locations under web application root, from
     * the classpath, and others. In short, we can add a resource handler to serve
     * static resources. The handler is invoked for requests that match one of the
     * specified URL path patterns. Patterns such as "/static/**" or
     * "/css/{filename:\\w+\\.css}" are supported. For pattern syntax see PathPattern
     * when parsed patterns are enabled or AntPathMatcher otherwise. The syntax is
     * largely the same with PathPattern more tailored for web usage and more efficient.
     * @param registry resource handler registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/images/**",
                        "/templates/**",
                        "/css/**",
                        "/js/**")
                .addResourceLocations(
                        "classpath:/static/images/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}