package com.library.lendit_book_kiosk.User;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Role.RoleRepository;


// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//import lombok.RequiredArgsConstructor;


//@RequiredArgsConstructor

/**
 * Service Access Layer
 */
@Transactional
@Service(value = "UserService")
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

    /**
     * Save a list of users: [ List<<code>User</code>> ]
     * @param users
     * @return List<<code>User</code>>
     */
    public List<User> saveAll(List<User> users){
        return userRepository.saveAll(users);
    }
    /**
     * Saves a single user
     * @param user
     * @return
     */
    public User saveUser(User user){
        log.info("Saving new user: {}", user);
        return userRepository.save(user);
    }
    /**
     * Adds getRoleByRolename to user.
     * @param username
     * @param roleName
     */
    public void addRoleToUser(String username, String roleName){
        // TODO: REMEMBER TO INCLUDE MORE VALIDATION CODE
        log.info("Adding UserRole: {} to USER: {}",roleName, username);
        Optional<User> userOptional = userRepository
                .findUserByName(username);
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
        userOptional.get().setRole(roleOptional.get());

        // update the database
        userRepository.save(userOptional.get());
        // userOptional.get().setRole(roleName);
        String response = "{\n\"response\":\"Successfully added Role\",\n" +
                "\"getRoleByRolename\":\"" + roleName + "\",\n" +
                "\"username\":\"" + username +
                "\n}";
        log.info(response);
    }
    // TODO: REMEMBER TO REMOVE ON PRODUCTION DEPLOY
    /**
     * Gets the user by username
     * @param username username
     */
    public User getUser(String username){
        log.info("Fetching USER: {}", username);
        Optional<User> userOptional = userRepository.findUserByName(username);
        // return User or else return null
        return userOptional.orElse(null);
    }
    // TODO: REMEMBER TO REMOVE ON PRODUCTION DEPLOY
    /**
     * Get all users
     * @return
     */
    public List<User> findAll(){
        log.info("Fetching all USERS. FOR TESTING PURPOSES ONLY.......");
        List<User> users = userRepository.findAll();
        log.info("\nUsers Received from DB: {}\n",users.toString());
        return users;
    }
    /**
     * Get <code>User</code> by email
     * @param email user email
     * @return User
     */
    public User findUserByEmail(String email){
        log.info("\nUSERNAME: {}\n", email);
        email = email.trim();
        Optional<User> user = userRepository.findUserByEmail(email);
        if (!user.isPresent())
        {
            throw new IllegalStateException("User with email " +
                    email + " was not found.");
        }
        log.info("\n\nUSER FOUND: {}\n\n",user.get());
        return user.get();
    }
    /**
     * Get <code>User</code> by id
     * @param id user id
     * @return <code>User</code>
     */
    public User findUserById(Long id){
        log.info("\nUSER ID: {}\n", id);
        Optional<User> user = userRepository.findUserById(id);
        if (!user.isPresent())
        {
            throw new IllegalStateException("User with user_id " +
                    id + " was not found.");
        }
        log.info("\n\nUSER FOUND: {}\n\n",user.get());
        return user.get();
    }
    /**
     * Update a <code>User</code> account
     * @param user a <code>User</code>
     * @return <code>User</code>
     */
    public User updateUser(User user){
        log.info("\nUser: {}\n", user.toString());
        return userRepository.save(user);
    }
    /**
     * Loads UserDetails by username search
     * @param username user email
     * @return <code>UserDetails</code>
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        log.info("\nUSERNAME: {}\n", username);
        return (UserDetails) getUser(username);
    }
    /**
     * Delete a <code>User</code> by user id
     * @param id the user id
     * @return the response status
     */
    public ResponseEntity<?> deleteUser(Long id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }
}
