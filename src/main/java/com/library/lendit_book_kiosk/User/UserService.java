package com.library.lendit_book_kiosk.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Role.RoleRepository;

import org.json.JSONObject;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import lombok.RequiredArgsConstructor;


//@RequiredArgsConstructor
@Transactional
@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Service access layer connects to Data access layer to retrieve students
     * @param userRepository user repository
     * @param roleRepository getRoleByRolename repository
     */
    @Autowired // needed to automatically connect to StudentRepository
    public UserService(UserRepository userRepository,RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public User saveUser(User user){
        log.info("Saving new user: {}", user);
        return userRepository.save(user);
    }


    public Role saveRole(Role role){
        log.info("Saving new getRoleByRolename: {}", role);
        return roleRepository.save(role);
    }


    /**
     * Adds getRoleByRolename to user.
     * @param username
     * @param roleName
     */
    public void addRoleToUser(String username, String roleName){
        // TODO: REMEMBER TO INCLUDE MORE VALIDATION CODE
        log.info("Adding ROLE: {} to USER: {}",roleName, username);
        Optional<User> userOptional = userRepository
            .findByUsername(username);
        Optional<Role> roleOptional = roleRepository.findRoleByName(roleName);
        // the user is not present in the User table
        if (userOptional.isEmpty()){
            throw new IllegalStateException("Username " + username + " does not exists.");
        }
        // the user has already been assigned this getRoleByRolename
        if (roleOptional.isPresent() && userOptional.get().getRoles().contains(roleOptional.get())){
            throw new IllegalStateException(" Role:" + roleName + " already assigned to Username: " + username  );
        }
        // the getRoleByRolename does not exist as a predefined getRoleByRolename
        if (roleOptional.isEmpty()){
            throw new IllegalStateException(roleName + " is not a Role defined for USERS.");
        }
        // check if user is assigned the getRoleByRolename
        for (Role role : userOptional.get().getRoles()) {
            if (role.equals(roleOptional.get())) {
                throw new IllegalStateException(" Role:" + roleName + " already assigned to Username: " + username  );
            }
        }
        // add getRoleByRolename to user
        userOptional.get().setRole(roleOptional.get().getRole());

        // update the database
        userRepository.save(userOptional.get());
        // userOptional.get().setRole(roleName);
        JSONObject response = new JSONObject(
            "\"response\":\"Successfully added Role\",\n" +
            "\"getRoleByRolename\":\"" + roleName + "\",\n" +
            "\"username\":\"" + username
            );
        log.info(response.toString());
    }

    // TODO: REMEMBER TO REMOVE ON PRODUCTION DEPLOY

    /**
     * Gets the user by username
     * @param username username
     */
    public User getUser(String username){
        log.info("Fetching USER: {}", username);
        Optional<User> userOptional = userRepository.findByUsername(username);
        // return User or else return null
        return userOptional.orElse(null);
    }

    // TODO: REMEMBER TO REMOVE ON PRODUCTION DEPLOY
    /**
     * Gets a user, if user exists
     */

    public List<User> getUsers(){
        log.info("Fetching all USERS. FOR TESTING PURPOSES ONLY.......");
        return userRepository.findAll();
    }

    public User getByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        log.info("\n\nUSER FOUND: {}\n\n",user.toString());

        return user.get();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) getUser(username); 
    }



}
