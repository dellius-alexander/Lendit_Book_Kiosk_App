package com.library.lendit_book_kiosk.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import java.util.regex.*;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    
    public List<Role> getRoles(){
        List<Role> roles = roleRepository.findAll();
        log.info("ROLES: {}",roles.toString());
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
        // create json response
        JSONObject results = null;
        // check if getRoleByRolename exists
        for (Role item : roles) {
            if (
                // item.getRole().equalsIgnoreCase(getRoleByRolename.trim())
                Pattern.compile(Pattern.quote(role.getRole().trim()), 
                Pattern.CASE_INSENSITIVE).matcher(item.getRole().trim()).find()
                ){
                exists = true;
                
            }
        }
        // add new getRoleByRolename if it does not exist
        if (!exists){
            /**
             * This system comes with some preconfigured 
             * options for roles that are activated when
             * added by admin
             */ 
            switch (role.getRole().toUpperCase()) {
                case "GUEST":
                    roleRepository.save(role);
                    results = response(exists);
                    break;
                case "STUDENT":
                    roleRepository.save(role);
                    results = response(exists);
                    break;
                case "FACULTY":
                    roleRepository.save(role);
                    results = response(exists);
                    break;
                case "ADMIN":
                    roleRepository.save(role);
                    results = response(exists);
                    break;
                case "SUPERUSER":
                    roleRepository.save(role);
                    results = response(exists);
                    break;
                default:
                    results = response(exists);
                    // do nothing
                    break;
            }
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
    private JSONObject response(boolean exists){
        JSONObject results = new JSONObject();
        // write response message
        if (exists){
            results.put("exists",Boolean.TRUE);
            results.put("created",Boolean.FALSE);
            }
        else {
            results.put("exists",Boolean.FALSE);
            results.put("created",Boolean.TRUE);
        }
        return results;
    }
}
