package com.library.lendit_book_kiosk.Security.Custom;

import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
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
    private UserRepository userRepository;

/**
 * Takes an <code>authentication</code> (token|payload|object) and compares the username 
 * and password against a datastore or static values. We use the AuthenticationProvider,
 * which provides a mechanism for getting user details, with which authentication can be performed.
 * @param authentication the authentication token provided by AuthenticationProvider interface
 * @return a CustomAuthentication token for future communication
 */
@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    List<User> users = userRepository.findAll();
    for(User u : users)
    {
        if (u.getEmail().equalsIgnoreCase(username.trim())
                && u.getPassword().equalsIgnoreCase(password.trim())){
            log.info("\n\nUSER FOUND: {}\n\n",u);
            return new UsernamePasswordAuthenticationToken(u, Collections.emptyList());
        }
    }
    log.info("USER NOT FOUND: new BadCredentialsException(\"Authentication failed\"");

//    if ("jane@gmail.com".equals(username) && "password".equals(password)) {
//      return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
//    } else {
//      throw new
//        BadCredentialsException("Authentication failed");
//    }

    throw new BadCredentialsException("Authentication failed");
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return aClass.equals(UsernamePasswordAuthenticationToken.class);
  }
}
