package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.UserDetails.UserDetailServices;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// import logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@Import(BeanValidatorPluginsConfiguration.class)
@Configuration(
        value = "WebSecurityConfig"
)
//@EnableGlobalMethodSecurity(  // TODO: disable debug mode on production
//        securedEnabled = true,
//        jsr250Enabled = true,
//        prePostEnabled = true
//)
@EnableWebSecurity
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private  CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailServices userDetailServices;

    /**
     * Http Security Configuration Options
     * @param http  HttpSecurity.class
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Initializing Http handler...");
        // Apply options to our http client:
        http.authorizeRequests()    // - authorize each request
                .antMatchers("/login",
                        "/css/**",
                        "/js/**",
                        "/static/images/**",
                        "/verify",
                        "/api/v1/**",
                        "/swagger-ui.html",
                        "/index",
                        "/**")
                    .permitAll()
//                .antMatchers(  // You must define all URL/URI path here to be accessible via http|api call
//
//                        "/login",
//                        "/index",
//                        "/error",
//                        // TODO: CREATE Role based access for api/v1/**
//                        "/**",
//                        "/api/v1/user/users").access("hasAnyRole('GUEST','ADMIN','USER','FACULTY','SUPERUSER')")
//                .antMatchers("/**").access("hasAnyRole('ADMIN','SUPERUSER')")
//            .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/index", true).failureUrl("/login")
//                    .permitAll()
//            .and()
//                .logout()
//                    .invalidateHttpSession(true)
//                    .clearAuthentication(true)
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/login?logout")
//                    .permitAll()
//            .and()
//                .exceptionHandling()
            .and()
                .csrf().disable();

        // Cross-Site Request Forgery (CSRF) is an attack that
        // forces an end user to execute unwanted actions on a
        // web application in which they’re currently authenticated.
    }

    /**
     * Configures Authentication for handler for application.
     * @param auth AuthenticationManagerBuilder.class
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Initializing AuthenticationManagerBuilder ...");
        /////////////////////////////////////////////////////////////
        // authenticate user via database using our:
        // - CustomAuthenticationProvider.class - Method 1
        auth.authenticationProvider(customAuthenticationProvider)
                .userDetailsService(userDetailServices)
                .passwordEncoder(passwordEncoder);
//                .inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder);
        /////////////////////////////////////////////////////////////
        // Creates a demo user for testing:
        // - Built-in authentication provider - Method 2
//        auth.inMemoryAuthentication()
//                .withUser("jane@gmail.com")
//                .password(passwordEncoder().encode("{noop}password"))
//                //{noop} makes sure that the password encoder doesn't do anything
//                .roles("ADMIN") // Role of the user
//                .and()
//                .withUser("john@gmail.com")
//                .password(passwordEncoder().encode("{noop}password"))
//                .credentialsExpired(true)
//                .accountExpired(true)
//                .accountLocked(true)
//                .roles("USER");
        /////////////////////////////////////////////////////////////
    }




    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


}