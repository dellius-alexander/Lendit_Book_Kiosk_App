package com.library.lendit_book_kiosk.Security.Custom;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
//@Component(value = "com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationFilter")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * Custom authentication filter is used to authenticate user credentials.
     * @param authenticationManager
     * @param customAuthenticationProvider
     */
    public CustomAuthenticationFilter(
            AuthenticationManager authenticationManager,
            CustomAuthenticationProvider customAuthenticationProvider) {
        this.authenticationManager = authenticationManager;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        /* compiled code */
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)));
    }

    /**
     * Successfully authenticated users receive a Json Web Token (JWT)
     * @param request
     * @param response
     * @param chain
     * @param authentication
     * @throws AuthenticationException
     * @throws ServletException
     * @throws IOException
     */
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) throws AuthenticationException, ServletException, IOException {
        super.successfulAuthentication(request,response,chain,authentication);
        UserLoginDetails user = (UserLoginDetails) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes(StandardCharsets.UTF_8));
        String access_token = JWT.create()
                .withSubject(user.getDisplayName())
                .withExpiresAt(new Date(System.currentTimeMillis() * 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getDisplayName())
                .withExpiresAt(new Date(System.currentTimeMillis() * 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);

    }
//    @org.springframework.lang.Nullable
//    protected java.lang.String obtainPassword(javax.servlet.http.HttpServletRequest request) { /* compiled code */ }
//
//    @org.springframework.lang.Nullable
//    protected java.lang.String obtainUsername(javax.servlet.http.HttpServletRequest request) { /* compiled code */ }
//
//    protected void setDetails(javax.servlet.http.HttpServletRequest request, org.springframework.security.authentication.UsernamePasswordAuthenticationToken authRequest) { /* compiled code */ }
//
//    public void setUsernameParameter(java.lang.String usernameParameter) { /* compiled code */ }
//
//    public void setPasswordParameter(java.lang.String passwordParameter) { /* compiled code */ }
//
//    public void setPostOnly(boolean postOnly) { /* compiled code */ }

}
