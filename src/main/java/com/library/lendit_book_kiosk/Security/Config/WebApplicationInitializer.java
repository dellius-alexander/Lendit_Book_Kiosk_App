package com.library.lendit_book_kiosk.Security.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Configuration
@ComponentScan(basePackages= {"com.library.lendit_book_kiosk"})
public class WebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    // Nothing to be added here
}