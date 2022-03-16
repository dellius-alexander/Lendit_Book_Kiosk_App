package com.library.lendit_book_kiosk.Security.UserDetails;

import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.User.User;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

// Tells Hibernate to make a table out of this class
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class UserLoginDetails implements UserDetails{
    private static final Logger log = LoggerFactory.getLogger(UserLoginDetails.class);
    private Long id;
    private String username;
    private String displayName;
    private String password;
    private Set<GrantedAuthority> authorities;

    public UserLoginDetails(){}

    public UserLoginDetails(
            Long id,
            String username,
            String displayName,
            String password,
            Set<GrantedAuthority> authorities){
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.authorities = authorities;

    }

    public UserLoginDetails(User user) {
        this.id = user.getId();
        this.username = user.getEmail();
        this.displayName = user.getName();
        this.password = user.getPassword();
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> authoritiesList = new HashSet<>();
        for (Role r : roles){
            authoritiesList.add(new SimpleGrantedAuthority("ROLE_"+r.getRole().name()));
//            this.authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRole().name()));
        }
        this.authorities = authoritiesList;

    }

    /**
     * Set by an AuthenticationManager to indicate the authorities that the principal has been granted.
     * Note that classes should not rely on this value as being valid unless it has been set by a trusted
     * AuthenticationManager.
     * @return the authorities granted to the principal, or an empty collection if the token has not been
     * authenticated. Never null.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }
}