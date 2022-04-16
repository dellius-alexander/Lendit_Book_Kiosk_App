package com.library.lendit_book_kiosk.Security.Config;

import com.library.lendit_book_kiosk.Security.Custom.CustomPasswordEncoder;
import com.library.lendit_book_kiosk.Security.UserDetails.CustomUserDetailsService;
import com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// import logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
@EnableWebSecurity(debug = false) // TODO: Security debugging is enabled.
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk"})
//@EnableJpaRepositories(basePackages = {"com.library.lendit_book_kiosk"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private  CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AppDataSource dataSource;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs",
            "/webjars/**",
            "/swagger-ui"
    };

    /**
     * Http Security Configuration Options
     * @param http  HttpSecurity.class
     * @throws Exception HttpSecurity Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Initializing Http handler...");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.addFilter(new CustomAuthenticationFilter());
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
                        "/index",
                        "/verify",
                        "/searchbookby/**"
                )
                .permitAll()
                   // For OPENAPI callers and urs
                .antMatchers(  // You must define all URL/URI path here to be accessible via http|api call
                        // TODO: CREATE Role based access for api
                        "/**")
                .hasAnyAuthority("ROLE_STUDENT","ROLE_ADMIN","ROLE_USER","ROLE_FACULTY","ROLE_SUPERUSER")
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
                    .logoutSuccessUrl("/login")
                    .permitAll()
            .and()
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .accessDeniedPage("/errors"))
            ;

        // Cross-Site Request Forgery (CSRF) is an attack that
        // forces an end user to execute unwanted actions on a
        // web application in which they’re currently authenticated.
    }
    /**
     * Configures Authentication for handler for application.
     * @param auth AuthenticationManagerBuilder.class
     * @throws Exception AuthenticationManagerBuilder Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Initializing AuthenticationManagerBuilder ...");
        /////////////////////////////////////////////////////////////
        // authenticate user via database using our:
        // - CustomAuthenticationProvider.class - Method 1
        auth.authenticationProvider(authenticationManagerBean())
                .jdbcAuthentication()
                .dataSource(dataSource())
                .passwordEncoder(passwordEncoder())
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
     * @param web WebSecurity object
     * @throws Exception WebSecurity Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception { web.ignoring().antMatchers(  AUTH_WHITELIST ); }

    @Override
//    @Primary
//    @Bean(name = {"authenticationManagerBean"})
    public CustomAuthenticationProvider authenticationManagerBean() throws Exception{ return  customAuthenticationProvider; }

    @Bean(name = {"userDetailsService"})
    @Override
    public CustomUserDetailsService userDetailsService(){
        return customUserDetailsService;
    }

    @Bean(  name = {"passwordEncoder"})
    public CustomPasswordEncoder passwordEncoder(){ return customPasswordEncoder; }

    public DataSource dataSource(){
        return dataSource.getDataSource();
    }

//    @Override
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
//
//        return null;
//    }
}