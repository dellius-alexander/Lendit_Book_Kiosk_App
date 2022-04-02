package com.library.lendit_book_kiosk.Role;

//import com.fasterxml.jackson.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.persistence.EntityManagerFactory;

@Service(value = "RoleService")
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;


    private enum ROLE {
        GUEST,
        STUDENT,
        FACULTY,
        ADMIN,
        SUPERUSER
    }

    @Autowired
    public RoleService(
            RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    
    public List<Role> getRoles(){
        List<Role> roles = roleRepository.findAll();
        log.info("ROLES: {}",roles);
        return roles;
    }

    public Role getRole(String roleName){
        Optional<Role> role = roleRepository.findRoleByName(roleName);
        if (!role.isPresent()){
            throw new IllegalStateException(
                "Sorry this getRoleByRolename: [" + roleName + "] does not exist."
            );
        }
        log.info("Role: [{}]",role);
        return role.get();
    }
    /**
     * Adds a new Role to getRoleByRolename table.
     * Access to this FUNCTION restricted to ADMIN & SUPERUSER.
     * ROLES: [ ADMIN | STUDENT | FACULTY | GUEST | SUPERUSER ]
     * @param role defines a getRoleByRolename
     * @return a list of roles
     */
    public List<String> setRole(Role role){
        boolean exists = false;
        // get all Roles
        List<Role> roles = roleRepository.findAll();
        List<String> missingRoles = new ArrayList<>();
        // create json response
        String results = null;
        int roleCnt = roles.size(); // for binary comparison
        // check if incoming role exists
        for (Role r : roles) {
            if (
                // item.getRole().equalsIgnoreCase(getRoleByRolename.trim())
                    Pattern.compile(Pattern.quote(r.getRole().name()),
                            Pattern.CASE_INSENSITIVE).matcher(role.getRole().name()).find()
            ) {
                log.info("\nRole exists: Role => {}\n", role);
                roleCnt+=1;  // role found: 1
                break;
            }
            // role not found: 0
            roleCnt-=1;
        }
        // only save roles that do not exist, duplicates are not saved
        if (roleCnt == 0){ // role not found, save role
                roleRepository.save(role);
                log.info("\nAdded new role: {}\n", role);
                results = response(false);
        } else if (roleCnt == 1){ // duplicate role found in table
            // do nothing
            results = response(true);
        }

        return List.of(results.toString());
    }
    /**
     * Responds to POST or PUT request to add or
     * UPDATE getRoleByRolename. If the getRoleByRolename does not exist, then
     * we create it as long as it is one of the approved
     * static roles. If getRoleByRolename does then we don't create it.
     * @param exists defines if getRoleByRolename exists
     * @return json reponse to the existence to getRoleByRolename:
     * {'exists': boolean,'created': boolean}
     */
    private String response(boolean exists){
        String results = null;
        // write response message
        String created = "{\n" +
                "\"created\":\"" + Boolean.TRUE +"\",\n" +
                "\"exists\":\"" + Boolean.FALSE +
                "\"\n}";
        String notcreated = "{\n" +
                "\"created\":\"" + Boolean.FALSE +"\",\n" +
                "\"exists\":\"" +Boolean.TRUE +
                "\"\n}";

        if (exists){
            results = notcreated;
            }
        else {
            results = created;
        }
        return results;
    }
}
