package com.library.lendit_book_kiosk.Security.Config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


public interface WebMvcConfigInterface extends WebMvcConfigurer, ApplicationContextAware {
    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

    @Bean
    PasswordEncoder passwordEncoder();

    @Bean
    LocaleResolver localeResolver();

    @Bean
    LocaleChangeInterceptor localeInterceptor();

    @Override
    void addInterceptors(InterceptorRegistry registry);

    @Bean
    ITemplateResolver templateResolver();

    @Bean
    LocalValidatorFactoryBean validator(MessageSource messageSource);

    @Override
    void addFormatters(FormatterRegistry registry);

    @Override
    void addViewControllers(ViewControllerRegistry registry);

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
     *
     * @param registry resource handler registry
     */
    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry);

//    @Bean
//    SpringResourceTemplateResolver templateResolver();

    @Bean
    SpringTemplateEngine templateEngine();

    @Bean(name = "SpringSecurity")
    ThymeleafViewResolver viewResolver();

    @Bean
    SpringSecurityDialect securityDialect();

    @Bean
    LayoutDialect layoutDialect();
}
