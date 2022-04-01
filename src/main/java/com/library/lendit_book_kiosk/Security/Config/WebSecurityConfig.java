package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationFilter;
import com.library.lendit_book_kiosk.Security.UserDetails.CustomUserDetailsService;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// import logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
///////////////////////////////////////////////////////////////////////////////
@Configuration(
        value = "WebSecurityConfig"
)
@EnableGlobalMethodSecurity(  // TODO: disable debug mode on production
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@EnableWebSecurity
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk.Security.Config"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private  CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs",
            "/webjars/**"
    };

    /**
     * Http Security Configuration Options
     * @param http  HttpSecurity.class
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Initializing Http handler...");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean(), authenticationProviderBean()));
        // Apply options to our http client:
        http.authorizeRequests()    // authorize each request
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/verify",
                        "/login",
                        "/error",
                        "/index"
                )
                .permitAll()
                   // For OPENAPI callers and urs
//                .antMatchers(  // You must define all URL/URI path here to be accessible via http|api call
//                        "/logout",
//                        "/index",
//                        "/hello",
//                        // TODO: CREATE Role based access for api
//                        "/user/**",
//                        "/student/**",
//                        "/book/**")
//                .hasAnyAuthority("GUEST","ADMIN","USER","FACULTY","SUPERUSER")
//
//                .antMatchers("/**")
//                .hasAnyAuthority("GUEST","ADMIN","USER","FACULTY","SUPERUSER")
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
//                    .logoutSuccessUrl("/login")
//                    .permitAll()
//            .and()
//                .exceptionHandling()
            ;

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
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select email, password from user where email = ?")
                .getUserDetailsService()
//                .inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
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
        ;
        /////////////////////////////////////////////////////////////
    }

    /**
     * Web Security configuration.
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                AUTH_WHITELIST
        );
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    public CustomAuthenticationProvider authenticationProviderBean() throws Exception{
        return customAuthenticationProvider;
    }
}