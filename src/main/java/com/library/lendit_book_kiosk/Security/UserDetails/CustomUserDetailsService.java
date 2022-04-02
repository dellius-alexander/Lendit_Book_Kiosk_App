package com.library.lendit_book_kiosk.Security.UserDetails;

import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "CustomUserDetailsService")
@Transactional
@Component
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final static Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserLoginDetails userLoginDetails;
    public CustomUserDetailsService(){}
    public CustomUserDetailsService(String username){loadUserByUsername(username);}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        short ENABLED = 1;
        log.info("\nUSERNAME: {}\n",username);
        User user = userService.getByEmail(username);
        if (user == null){
                throw  new UsernameNotFoundException(
                        String.format("\nUser with USERNAME: %s could not be found.\n",username));

        }
        log.info("\nUSER RETRIEVED: {}\n",user.toString());
        this.userLoginDetails = new UserLoginDetails(user);
        return (UserDetails) this.userLoginDetails;
    }

}
