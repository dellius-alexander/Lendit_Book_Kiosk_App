package com.library.lendit_book_kiosk.Security.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration("OpenApiConfig")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk"})
public class OpenApiConfig {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    @Bean
    public OpenAPI lendITBookKioskOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("LendIT Book Kiosk API")
                        .description("The textbook for higher education is soaring every year. " +
                                "Clayton State University initiated campus textbook lending kiosk " +
                                "project to help students learning and reduce cost.")
                        .version("v0.2.0")
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(
                        new ExternalDocumentation()
                                .description("LendIT Book Kiosk Api Documentation Description.")
                                .url("http://localhost:8081/manage/swagger-ui/")
                )
//                .security()
                ;
    }
//    private ApiKey apiKey() {
//        return new ApiKey(AUTHORIZATION_HEADER, "JWT", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//
//    List<SecurityRequirement> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
//    }

}
