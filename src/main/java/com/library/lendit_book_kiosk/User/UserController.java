package com.library.lendit_book_kiosk.User;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import com.library.lendit_book_kiosk.Role.Role;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import lombok.Data;
//import lombok.RequiredArgsConstructor;

/**
 * Api data structure: [ api/v1/user/[noun/verb] | api/v1/user/[verb/noun] | api/v1/user/(noun|verb)/(noun[s]?|verb)/[REPEAT] ]
 * REPEAT: repeat the last regular expression.
 * - OPTIONS: <code>
 *     - users, save, getRoleByRolename/save, getRoleByRolename/add, verify
 * </code>
 *
 */
@RestController
@RequestMapping(value = "api/v1/user")
//@RequiredArgsConstructor
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    // TODO: REMEMBER TO KILL THIS METHOD DURING PRODUCTION
    @GetMapping(path = "/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        log.info("USERS: \n{}\n",users.toString());
        return ResponseEntity.ok().body(users);
    }

    @PostMapping(path = "/save/{user}")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        log.info("USER: \n{}\n",user.toString());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping(path = "/{user}/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        // TODO: Change visibility to protected
        log.info("ROLE: \n{}\n", role.toString());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/getRoleByRolename/save").toUriString());
        log.info("URI: \n{}\n",uri);
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping(path = "/{user}/role/add")
    public ResponseEntity<?> addRole(
            @RequestBody User user,
            @RequestBody RoleToUserForm form){
        log.info("\nUSER: {}\nFORM: {}\n",user.toString(), form.toString());
        userService.addRoleToUser(form.getUsername(), form.getRole());
        return ResponseEntity.ok().body(HttpStatus.ALREADY_REPORTED);
    }

    @PostMapping(path = "/verify")
    public ResponseEntity<?> verify(@RequestBody UserLoginDetails form){
        log.info(form.toString());
        // return ResponseEntity.ok().build();
        return null;
    }

}


