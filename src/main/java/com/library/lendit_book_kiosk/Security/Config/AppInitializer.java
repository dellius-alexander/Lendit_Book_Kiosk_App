package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import com.library.lendit_book_kiosk.Security.Custom.CustomPasswordEncoder;
import com.library.lendit_book_kiosk.Security.UserDetails.CustomUserDetailsService;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration("AppInitializer")
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk"})
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     *
     * @return root configuration classes: [HibernateConfig.class,
     *                 WebSecurityConfig.class]
     */
    /**
     * Defines root configuration classes
     * @return list of configuration classes: AppDataSource.class,
     *                 WebSecurityConfig.class,
     *                 CustomAuthenticationProvider.class,
     *                 CustomAccessDenialHandler.class,
     *                 OpenApiConfig.class,
     *                 SpringSecurityInitializer.class,
     *                 WebApplicationInitializer.class,
     *                 CustomAuthenticationProvider.class,
     *                 CustomPasswordEncoder.class,
     *                 CustomUserDetailsService.class,
     *                 UserLoginDetails.class
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                AppDataSource.class,
                WebSecurityConfig.class,
                CustomAuthenticationProvider.class,
                CustomAccessDenialHandler.class,
                OpenApiConfig.class,
                SpringSecurityInitializer.class,
                WebApplicationInitializer.class,
                CustomAuthenticationProvider.class,
                CustomPasswordEncoder.class,
                CustomUserDetailsService.class,
                UserLoginDetails.class
        };
    }

    /**
     * Initialize servlet configuration files.  This includes but not
     * limited to: thymeleaf viewResolvers, Url paths and endpoints.
     * @return servlet configuration classes: WebMvcConfig.class
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