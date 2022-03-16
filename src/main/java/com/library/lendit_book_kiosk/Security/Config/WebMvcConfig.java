package com.library.lendit_book_kiosk.Security.Config;


import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk"})
public class WebMvcConfig implements WebMvcConfigInterface {
    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private final SpringResourceTemplateResolver springResourceTemplateResolver;

    public WebMvcConfig(SpringResourceTemplateResolver springResourceTemplateResolver) {
//        super();
        this.springResourceTemplateResolver = springResourceTemplateResolver;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
    @Override
    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // with new spring security 5
    }
    @Bean
    @Override
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return  localeResolver;
    }
    @Override
    @Bean
    public LocaleChangeInterceptor localeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }
    /*
    This is optional, you need to define this in case you are using a different location for
    your resource bundles e.g in i18n folder etc.
     @Bean
     public MessageSource messageSource() {
         ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
         messageSource.setBasename("classpath:messages");
         messageSource.setDefaultEncoding("UTF-8");
         return messageSource;
     }*/

    @Override
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // TODO: create and add custom formatters to the FormatterRegistry
//        registry.addFormatter();
    }

    /* ******************************************************************* */
    /*  GENERAL CONFIGURATION ARTIFACTS                                    */
    /*  Static Resources, i18n Messages, Formatters (Conversion Service)   */
    /* ******************************************************************* */

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
                        "swagger-ui.html",
                        "/webjars/**",
                        "/images/**",
                        "/css/**",
                        "/js/**"
                )
                .addResourceLocations(
                        "classpath:/META-INF/resources/",
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/images/",
                        "classpath:/static/css/",
                        "classpath:/static/js/"

                );
//        //enabling swagger-ui part for visual documentation
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }


    /* **************************************************************** */
    /*  THYMELEAF-SPECIFIC ARTIFACTS                                    */
    /*  TemplateResolver <- TemplateEngine <- ViewResolver              */
    /* **************************************************************** */

    @Override
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(this.ctx);
//        templateResolver.setPrefix("classpath:/templates/");
//        templateResolver.setSuffix(".html");
//        // HTML is the default value, added here for the sake of clarity.
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        // Template cache is true by default. Set to false if you want
//        // templates to be automatically updated when modified.
//        templateResolver.setCacheable(true);

        this.springResourceTemplateResolver.setApplicationContext(this.ctx);
        this.springResourceTemplateResolver.setPrefix("classpath:/templates/");
        this.springResourceTemplateResolver.setSuffix(".html");
        this.springResourceTemplateResolver.setTemplateMode(TemplateMode.HTML);
        this.springResourceTemplateResolver.setCacheable(true);
        return this.springResourceTemplateResolver;
    }

    @Override
    @Bean
    public SpringTemplateEngine templateEngine(){
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        Set<IDialect> additionalDialects = new HashSet<>();
        additionalDialects.add(layoutDialect());
        additionalDialects.add(securityDialect());
        // Add additional dialects as needed above
        templateEngine.setAdditionalDialects(additionalDialects);
        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    @Override
    @Bean
    public ThymeleafViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new
//                InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setApplicationContext(this.ctx);
        return viewResolver;
    }

    @Override
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    @Bean
    public LayoutDialect layoutDialect(){
        return new LayoutDialect();
    }
}