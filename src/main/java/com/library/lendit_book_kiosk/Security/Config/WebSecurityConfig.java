package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@ComponentScan(basePackageClasses = CustomAuthenticationProvider.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Configures Authentication for handler for application.
     * @param auth AuthenticationManagerBuilder.class
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Initializing AuthenticationManagerBuilder ...");
        // Custom authentication provider - Order 1
        auth.authenticationProvider(customAuthenticationProvider);
        /////////////////////////////////////////////////////////////
//        // Built-in authentication provider - Order 2
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

    /**
     * Service interface for encoding passwords.
     * The preferred implementation is BCryptPasswordEncoder.
     * @return BCryptPasswordEncoder.class
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Initializing passwordEncoder...");
        return new BCryptPasswordEncoder();
    }

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
            .antMatchers("/login").permitAll()
            .antMatchers(  // You must define all URL/URI path here to be accessible via http|api call
                    "/**",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/login",
                    "/index",
                    "/verify",
                    "/error",
                    // TODO: CREATE Role based access for api/v1/**
                    "/api/v1/**").hasAnyRole("ADMIN","USER","FACULTY","SUPERUSER")
                .antMatchers("/**").hasAnyRole("ADMIN","SUPERUSER")
                .and().formLogin()
                .and().logout().logoutSuccessUrl("/login").permitAll()
                .and().csrf().disable();
//                    .antMatchers(
//                            "/index",
//                            "api/v1/user/**",
//                            "api/v1/student/**"
//                    ).hasAnyRole("ADMIN","USER","SUPERUSER")
//                    .anyRequest().authenticated()
//            .and()
//                .formLogin()
//                    .loginPage("/login")
//                .defaultSuccessUrl("/index", true).failureUrl("/login")
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
//                .and()
//                .csrf().disable();

        // Cross-Site Request Forgery (CSRF) is an attack that
        // forces an end user to execute unwanted actions on a
        // web application in which they’re currently authenticated.
    }



}