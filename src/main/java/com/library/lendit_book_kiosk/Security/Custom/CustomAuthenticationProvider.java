package com.library.lendit_book_kiosk.Security.Custom;


//import com.library.lendit_book_kiosk.User.UserRepository;
import com.library.lendit_book_kiosk.Security.UserDetails.CustomUserDetailsService;
import com.library.lendit_book_kiosk.Security.UserDetails.UserLoginDetails;
import com.library.lendit_book_kiosk.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
//import java.util.Collections;
//import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.User;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * Creates a CustomAuthenticationProvider by implementing the AuthenticationProvider interface.
 * @implements AuthenticationProvider
 */
@Component(value = "com.library.lendit_book_kiosk.Security.Custom.CustomAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private  UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginDetails userLoginDetails;

//    @Autowired
    public CustomAuthenticationProvider(){ }
/**
 * Takes an <code>authentication</code> (token|payload|object) and validates the username
 * and password against a datastore of static values. We use the AuthenticationProvider,
 * which provides a mechanism for getting user details, with which authentication can be performed.
 * @param authentication the authentication token provided by AuthenticationProvider interface
 * @return a CustomAuthentication token for future communication
 */
@Override
public UsernamePasswordAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    com.library.lendit_book_kiosk.User.User user = userService.getByEmail(username);
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
    if (!Pattern.matches(password, user.getPassword())) {
        log.info("USER NOT FOUND: {}",
            new BadCredentialsException("Authentication failed/1000"));
        throw new BadCredentialsException("Authentication failed/1000");
    }
    log.info("\nUSERNAME: {}\nPASSWORD: {}\nAUTHENTICATION: {}\n",
            username,
            password,
            authentication);

    final UserDetails principal = customUserDetailsService.loadUserByUsername(username);
    this.userLoginDetails = new UserLoginDetails(user);
//            new org.springframework.security.core.userdetails.User(
//            username,
//            password,
//            authentication
//                    .getAuthorities()
//                    .stream()
//                    .map(   x -> new SimpleGrantedAuthority(
//                                    x.getAuthority()
//                            )).collect(Collectors.toSet())
//    );

    return new UsernamePasswordAuthenticationToken(
            principal,
            password,
            this.userLoginDetails.getAuthorities()
//            authentication
//                    .getAuthorities()
//                    .stream()
//                    .map(   x -> new SimpleGrantedAuthority(
//                            x.getAuthority()
//                    )).collect(Collectors.toSet())
    );
  }

  @Override
  public boolean supports(Class<?> auth) {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }
}
