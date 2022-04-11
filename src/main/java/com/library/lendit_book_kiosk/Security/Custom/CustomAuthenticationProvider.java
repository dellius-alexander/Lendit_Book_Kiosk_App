package com.library.lendit_book_kiosk.Security.Custom;


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
import java.util.regex.Pattern;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Creates a CustomAuthenticationProvider by implementing the AuthenticationProvider interface.
 * @see com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails
 */
@Configuration("CustomAuthenticationProvider")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk"})
public class CustomAuthenticationProvider  implements AuthenticationManager, AuthenticationProvider {
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private  UserService userService;
    @Autowired
    private UserLoginDetails userLoginDetails;

    public CustomAuthenticationProvider(){}

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
        log.info("\nPassword: {}", password);
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
        if (!Password.matches(password, user.getPasswordClass())) {
            log.info("USER NOT FOUND: {}",
                new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }

        log.info("\nUSERNAME: {}\nPASSWORD: {}\nAUTHENTICATION: {}\n",
                username,
                password,
                authentication);
        return userLoginDetails.getUsernamePasswordAuthenticationToken( user );
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
        String username = userLoginDetails.getUsername();
        Password password = userLoginDetails.getPasswordClass();
        log.info("\nPassword: {}", password);

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
        if (!Password.matches(password, user.getPasswordClass())) {
            log.info("USER NOT FOUND: {}",
                    new BadCredentialsException("Authentication failed/1000"));
            throw new BadCredentialsException("Authentication failed/1000");
        }

        log.info("\nUSERNAME: {}\nPASSWORD: {}\n",
                username,
                password);
        return userLoginDetails.getUsernamePasswordAuthenticationToken( user );


    }
  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }


}
