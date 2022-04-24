package com.library.lendit_book_kiosk.Security.Custom;


import com.library.lendit_book_kiosk.Security.Secret.Secret;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserService;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;


/**
 * Creates a CustomAuthenticationProvider by implementing the AuthenticationProvider interface.
 * @see com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails
 */
@Configuration("CustomAuthenticationProvider")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk"})
public class CustomAuthenticationProvider extends UsernamePasswordAuthenticationToken  implements AuthenticationManager, AuthenticationProvider {
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserService userService;

    public CustomAuthenticationProvider(){
        super("","");
    }
    public CustomAuthenticationProvider(User user) {
        super(user.getEmail(),user.getPassword(),user.getAuthorities());


    }
    public CustomAuthenticationProvider(Object principal, Object credentials, Set<GrantedAuthority> authorities){
        super(principal,credentials,authorities);
        super.setDetails(new UserLoginDetails(principal.toString(),principal.toString(),credentials.toString(),authorities));

    }
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(){
        return new UsernamePasswordAuthenticationToken(
                getPrincipal(), getCredentials(),getAuthorities());
    }

    public  UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(User user) {
//        auth = getAuthenticaticationToken(new UserLoginDetails(user));
        return new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword(),user.getAuthorities());
    }

    public Set<GrantedAuthority> getAuthorities(){
        return  super.getAuthorities().stream().collect(Collectors.toSet());
    }
    /**
     * The secret
     * @return
     */
    @Override
    public String getCredentials() {
        return super.getCredentials().toString();
    }

    /**
     * The username
     * @return
     */
    @Override
    public String getPrincipal() {
        return super.getPrincipal().toString();
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public String getName(){return super.getName();}

    @Override
    public void setDetails(Object details) {
        super.setDetails(details);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }
    /**
     * Takes an <code>authentication</code> (token|payload|object) and validates the username
     * and password against a datastore of static values. We use the AuthenticationProvider,
     * which provides a mechanism for getting user details, with which authentication can be performed.
     * @param authentication the authentication token provided by AuthenticationProvider interface
     * @return a CustomAuthentication token for future communication
     */
    @Override
    public UsernamePasswordAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null){
            throw new NullArgumentException("Authentication Object is null: " + authentication.toString());
        }
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("\nUsername: {}\n, Secret: {}",username, password);
        User user = userService.getByEmail(username);
        log.info("User found: {}", user);
        if (user == null) {
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }
        if (!Pattern.matches(username, user.getEmail())){
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }
        if (!Secret.matches(password, user.getPasswordClass())) {
            log.info("USER NOT FOUND: {}",
                new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }

        log.info("\nUSERNAME: {}\nPASSWORD: {}\nAUTHENTICATION: {}\n",
                username,
                password,
                authentication);
        return new UsernamePasswordAuthenticationToken(
                user.getEmail(),user.getPassword(),user.getAuthorities()
        );
      }
    /**
     * Takes an <code>UserLoginDetails</code> (token|payload|object) and validates the username
     * and password against a datastore of static values. We use the AuthenticationProvider,
     * which provides a mechanism for getting user details, with which authentication can be performed.
     * @param userLoginDetails the userLoginDetails object containing the username and password to be authenticated
     * @return a CustomAuthentication token for future communication
     */
    public Authentication getAuthenticaticationToken(UserLoginDetails userLoginDetails) throws AuthenticationException {
        if (userLoginDetails == null){
            throw new NullArgumentException("Authentication Object is null: " + userLoginDetails.toString());
        }
        String username = userLoginDetails.getUsername();
        Secret secret = userLoginDetails.getPasswordClass();
        log.info("\nUsername: {}\n, Secret: {}",username, secret);

        User user = userService.getByEmail(username);
        log.info("User found: {}", user);
        if (user == null) {
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }
        if (!Pattern.matches(username, user.getEmail())){
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }
        if (!Secret.matches(secret, user.getPasswordClass())) {
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }

        log.info("\nUSERNAME: {}\nPASSWORD: {}\nUser Authentication Successful............\n",
                username,
                secret);
        return new UsernamePasswordAuthenticationToken(
                user.getEmail(),user.getPassword(),user.getAuthorities()
        );
    }
    /**
     * Takes an <code>UserLoginDetails</code> (token|payload|object) and validates the username
     * and password against a datastore of static values. We use the AuthenticationProvider,
     * which provides a mechanism for getting user details, with which authentication can be performed.
     * @param userLoginDetails the userLoginDetails object containing the username and password to be authenticated
     * @return a CustomAuthentication token for future communication
     */
    public UsernamePasswordAuthenticationToken authenticate(UserLoginDetails userLoginDetails) throws AuthenticationException {
        if (userLoginDetails == null){
            throw new NullArgumentException("Authentication Object is null: " + userLoginDetails.toString());
        }
        log.info(userLoginDetails.toString());
        String username = userLoginDetails.getUsername();
        Secret secret = userLoginDetails.getPasswordClass();
        log.info("\nUsername: {}\n, Secret: {}",username, secret);

        User user = userService.getByEmail(username);
        log.info("User found: {}", user);
        if (user == null) {
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }
        if (!Pattern.matches(username, user.getEmail())){
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }
        if (!Secret.matches(secret, user.getPasswordClass())) {
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }

        log.info("\nUSERNAME: {}\nPASSWORD: {}\nUser Authentication Successful............\n",
                username,
                secret);
        return new UsernamePasswordAuthenticationToken(
                user.getEmail(),user.getPassword(),user.getAuthorities()
        );
    }
    @Override
    public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
    }


}
