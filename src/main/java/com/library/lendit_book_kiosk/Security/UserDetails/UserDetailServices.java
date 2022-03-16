package com.library.lendit_book_kiosk.Security.UserDetails;

import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service(value = "UserDetailServices")
@Transactional
public class UserDetailServices implements UserDetailsService {
    private final static Logger log = LoggerFactory.getLogger(UserDetailServices.class);
    @Autowired
    private UserRepository userRepository;

    public UserDetailServices(){}

//    @Autowired
    public UserDetailServices(UserRepository userRepository){

        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        short ENABLED = 1;
        log.info("\nUSERNAME: {}\n",username);
        User userOptional = userRepository.findUserByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                String.format("\nUser with USERNAME: %s could not be found.\n",username)
                        )
                );
        log.info("\nUSER RETRIEVED: {}\n",userOptional.toString());
        return new UserLoginDetails(userOptional);
    }

}
