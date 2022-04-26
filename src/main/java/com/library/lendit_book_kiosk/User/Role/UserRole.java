package com.library.lendit_book_kiosk.User.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UserRole policies defines access rights and privileges
 */
public enum UserRole {
    USER("USER"),
    GUEST("GUEST"),
    STUDENT("STUDENT"),
    FACULTY("FACULTY"),
    STAFF("STAFF"),
    ADMIN("ADMIN"),
    SUPERUSER("SUPERUSER");
    private static final Logger log = LoggerFactory.getLogger(UserRole.class);
    private final String label;
    // static inference
    private static final Map<String, UserRole> BY_LABEL = new HashMap<>();
    /**
     * Create new UserRole with label = label
     * @param label
     */
    UserRole(String label){
        this.label = label;
    }
    /**
     * static inference collection of ROLES
     */
    static {
        for (UserRole e: values()) {
            BY_LABEL.put(e.label, e);
        }
        log.info(BY_LABEL.values().stream().collect(Collectors.toList()).toString());
    }
    /**
     * Static inference: Creates a UserRole Object with label = label.
     * @param label
     * @return
     */
    public static UserRole valueOfLabel(String label) {
        UserRole r = null;
        for (UserRole role : values()) {
            if (role.label.equals(label)) {
                log.info(role.label);
                r = role;
                return role;
            }
        }
        return r;
    }
//    public static void main(String[] args) {
//        UserRole role = UserRole.valueOfLabel("ADMIN");
//        System.out.println("ROLE_" + role);
//    }
}
