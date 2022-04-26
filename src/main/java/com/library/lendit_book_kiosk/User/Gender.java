package com.library.lendit_book_kiosk.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");
    private static final Logger log = LoggerFactory.getLogger(Gender.class);
    private final String label;
    // static inference
    private static final Map<String, Gender> BY_LABEL = new HashMap<>();
    /**
     * Create new UserRole with label = label
     * @param label
     */
    Gender(String label){
        this.label = label;
    }
    /**
     * static inference collection of ROLES
     */
    static {
        for (Gender e: values()) {
            BY_LABEL.put(e.label, e);
        }
        log.info(BY_LABEL.values().stream().collect(Collectors.toList()).toString());
    }
    /**
     * Static inference: Creates a UserRole Object with label = label.
     * @param label
     * @return
     */
    public static Gender valueOfLabel(String label) {
        Gender gender = null;
        for (Gender g : values()) {
            if (g.label.equals(label)) {
                log.info(g.label);
                gender = g;
                return g;
            }
        }
        return gender;
    }
}
