package com.library.lendit_book_kiosk.User;

import com.library.lendit_book_kiosk.User.Role.Role;
import com.library.lendit_book_kiosk.Security.Secret.Secret;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface UserInterface extends Serializable {

    /**
     * Assigns the given userRole
     * @param role
     */
    void setRole(Role role);


    /**
     * Check if 'Object' 'user' is an instance of 'this' object
     * @param user
     * @return [ true if equal | false if not equal ] to this object
     */
    @Override
    boolean equals(Object user);

    /**
     * Checks if 'Object' 'user' is equal to 'this' user.
     * @param user the other User.
     * @return
     */
    boolean canEqual(Object user);

    /**
     * Get the hashcode of the class
     * @return integer representing the hashcode of the class
     */
    int hashCode();

    /**
     * Get Granted Authorities
     * @return a Collection GrantedAuthority
     */
    Collection<GrantedAuthority> getAuthorities();

    /**
     * Get the user id
     * @return  the user id
     */
    Long getId();

    /**
     * Get the username
     * @return the username
     */
    String getName();

    /**
     * Get the user email
     * @return the user email
     */
    String getEmail();
    String getPassword();

    /**
     * Get the user gender
     * @return the user gender
     */
    Gender getGender();

    /**
     * Get the user age
     * @return the user age
     */
    Integer getAge();

    /**
     * Get the user dob
     * @return the user dob
     */
    java.time.LocalDate getDob();

    /**
     * Get the user profession
     * @return the user profession
     */
    String getProfession();

    /**
     * To String method
     * @return the toString() representation of the object
     */
    String toString();

    /**
     * Get the user roled
     * @return the user roles
     */
    java.util.Set<Role> getRoles();


    /**
     * Set user id
     * @param id
     */
    void setId(Long id);

    /**
     * Set username
     * @param name
     */
    void setName(String name);

    /**
     * Set user email
     * @param email
     */
    void setEmail(String email);

    /**
     * Set user secret
     * @param secret
     */
    void setPassword(Secret secret);

    /**
     * Set user gender
     * @param gender
     */
    void setGender(Gender gender);

    /**
     * Set user dob
     * @param dob
     */
    void setDob(java.time.LocalDate dob);

    /**
     * Set user profession
     * @param profession
     */
    void setProfession(String profession);

    /**
     * Set user roles
     * @param roles
     */
    void setRoles(java.util.Set<Role> roles);


}
