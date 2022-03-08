package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
// import logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomAuthenticationProvider.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private LoggingAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Initializing Http handler...");
        http
//                .csrf().disable()
                .authenticationProvider(customAuthenticationProvider)
                .authorizeRequests()
                    .antMatchers(  // You must define all URL/URI path here to be accessable via http|api call
                            "/css/**",
                            "/js/**",
                            "/images/**",
                            "/login",
                            "/index",
                            "/verify",
                            "/error",
//                            "login-error",
                            // TODO: CREATE Role based access for api/v1/**
                            "api/v1/**").permitAll()
                    .antMatchers("/index").hasAnyRole("ADMIN","USER")
                    .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                .defaultSuccessUrl("/index", true).failureUrl("/login")
                    .permitAll()
            .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);
        // Cross-Site Request Forgery (CSRF) is an attack that
        // forces an end user to execute unwanted actions on a
        // web application in which they’re currently authenticated.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Initializing AuthenticationManagerBuilder ...");
        // Custom authentication provider - Order 1
        auth.authenticationProvider(customAuthenticationProvider);
//        // Built-in authentication provider - Order 2
//        auth.inMemoryAuthentication()
//                .withUser("jane@gmail.com")
//                .password(passwordEncoder().encode("{noop}password"))
//                //{noop} makes sure that the password encoder doesn't do anything
//                .roles("ADMIN") // Role of the user
//            .and()
//                .withUser("john@gmail.com")
//                .password(passwordEncoder().encode("{noop}password"))
//                .credentialsExpired(true)
//                .accountExpired(true)
//                .accountLocked(true)
//                .roles("USER");
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.info("Initializing passwordEncoder...");
        return new BCryptPasswordEncoder();
    }


}