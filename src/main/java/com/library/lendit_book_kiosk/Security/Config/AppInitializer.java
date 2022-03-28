package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk.Security.Config"})
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Defines root configuration classes
     * @return root configuration classes: [HibernateConfig.class,
     *                 WebSecurityConfig.class]
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                ApplicationConfig.class,
                WebSecurityConfig.class,
                CustomAuthenticationProvider.class
        };
    }

    /**
     * Initialize servlet configuration files.  This includes but not
     * limited to: thymeleaf viewResolvers, Url paths and endpoints.
     * @return servlet configuration classes
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
            return new Class[]{
                    WebMvcConfig.class


            };
        }

    /**
     * Initialize root servlet mapping
     * @return "/"
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}