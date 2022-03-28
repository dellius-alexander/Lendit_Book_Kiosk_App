package com.library.lendit_book_kiosk.Security.UserDetails;

import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.User.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// Tells Hibernate to make a table out of this class
@Component
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
        this.setAuthorities(authorities);
        this.setDisplayName(displayName);
        this.setUsername(username);
        this.setId(id);
        this.setPassword(password);
        log.info(toString());
    }

    public UserLoginDetails(User user) {
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> authoritiesList = new HashSet<>();
        for (Role r : roles){
            authoritiesList.add(new SimpleGrantedAuthority("ROLE_"+r.getRole().name()));
//            this.authorities.add(new SimpleGrantedAuthority("ROLE_"+r.getRole().name()));
        }
        this.setAuthorities(authoritiesList);
        this.setDisplayName(user.getName());
        this.setUsername(user.getEmail());
        this.setId(user.getId());
        this.setPassword(user.getPassword());
        log.info(toString());
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserLoginDetails)) return false;
        final UserLoginDetails other = (UserLoginDetails) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (!Objects.equals(this$username, other$username)) return false;
        final Object this$displayName = this.getDisplayName();
        final Object other$displayName = other.getDisplayName();
        if (!Objects.equals(this$displayName, other$displayName))
            return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (!Objects.equals(this$password, other$password)) return false;
        final Object this$authorities = this.getAuthorities();
        final Object other$authorities = other.getAuthorities();
        if (!Objects.equals(this$authorities, other$authorities))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserLoginDetails;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $displayName = this.getDisplayName();
        result = result * PRIME + ($displayName == null ? 43 : $displayName.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $authorities = this.getAuthorities();
        result = result * PRIME + ($authorities == null ? 43 : $authorities.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return "{\n" +
                "\"id\":" + this.getId() + "," +
                "\"username\":\"" + this.getUsername() + "\",\n" +
                "\"displayName\":\"" + this.getDisplayName() + "\",\n" +
                "\"password\":\"" + this.getPassword() + "\",\n" +
                "\"authorities\":\"" + this.getAuthorities() + "\"" +
                "\n}";
    }
}