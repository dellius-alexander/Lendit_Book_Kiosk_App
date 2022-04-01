package com.library.lendit_book_kiosk.User;

import java.net.URI;
import java.util.List;

import com.library.lendit_book_kiosk.Role.Role;
// LOGGING CLASSES
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;


/**
 * Api Layer <br/>
 * <code style="color:orange;font-style:bold;">/user</code>
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    // TODO: REMEMBER TO KILL THIS METHOD DURING PRODUCTION

    /**
     * Get a list of All Users
     * @return a list of all students
     */
    @RequestMapping(
            value = {"/findAll", "findall"},
            method = RequestMethod.GET,
            produces = {"application/json"}
    )
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        log.info("USERS: \n{}\n",users.toString());
        return ResponseEntity.ok().body(users);
    }
    /**
     * Find user by <code>user_Id</code>
     * @param user_id the user Id
     * @return the <code>User</code> or User not found status code
     */
    @RequestMapping(
            value = {"/findById"},
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<User> getUserById(
            @PathVariable("user_id") @RequestParam Long user_id){
        log.info("\nUser Id: {}\n");
        return ResponseEntity.ok().body(userService.getById(user_id));
    }
    /**
     * Save a new <code>User</code>
     * @param user a new <code>User</code>
     * @return the saved user and response status code
     */
//            (
//            value = {"/save/user={user}","/save/{user}"},
////            method = RequestMethod.POST,
//            consumes = {"*/*"},
//            produces = {"application/json"},
//            params = {"user"},
//            name = "user")
    @PostMapping(
            value = {"/save/{user}"},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    @Valid
    public ResponseEntity<User> saveUser(@RequestBody User user)
    {
        log.info("USER: \n{}\n", user.toString());
        return ResponseEntity.created(URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/user/save/*")
                            .toUriString())).body(userService.saveUser(user));
    }
    // TODO: Change visibility to protected
    /**
     * Assign <code>Role</code> to <code>User</code>
     * @param user_id the user id
     * @param role <code>Role</code>
     * @return the <code>User</code> and response status code
     */
    @RequestMapping(
            value = {"/assign-role/user_id={user_id}&role={role}"},
            method = RequestMethod.PATCH,
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<User> saveRole(
            @PathVariable("user_id") Long user_id,
            @PathVariable("role") @RequestBody Role role){

        log.info("\nUser Id: {}\nRole: {}\n", user_id, role.toString());
        if (user_id == null){
            throw new NullArgumentException("User id is NULL");
        }
        if (role == null){
            throw new NullArgumentException("Role is NULL");
        }
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/user/assign-role/*")
                        .toUriString());
        log.info("URI: \n{}\n",uri);
        User user = userService.getById(user_id);
        user.setRole(role);
        return ResponseEntity.created(uri).body(userService.updateUser(user));
    }
    /**
     * Delete a <code>User</code> by <code>user_id</code>
     * @param user_id the user id
     * @Return the response status
     */
    @DeleteMapping(
            value = {"/delete/{user_id}"},
            produces = {"application/json"})
    public ResponseEntity<HttpStatus> deleteUser(
            @PathVariable("user_id") Long user_id){
        log.info("Delete RequestedMethod POST: StudentId => {}", user_id);
        return  ResponseEntity.ok().body(userService.deleteUserById(user_id).getBody());
    }

//    /**
//     * Verify <code>UserLoginDetails</code> form
//     * @param form <code>UserLoginDetails</code> form
//     * @return response status of POST request
//     */
//    @RequestMapping(
//            value = {"/verify"},
//            method = RequestMethod.POST,
//            consumes = {"application/json"},
//            produces = {"application/json"},
//            path = "/verify")
//    public ResponseEntity<?> verify(
//            @PathVariable("role") @RequestBody UserLoginDetails form){
//        log.info(form.toString());
//        // return ResponseEntity.ok().build();
//        return null;
//    }

}


