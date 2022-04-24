package com.library.lendit_book_kiosk.Security.UserDetails;

import com.library.lendit_book_kiosk.Security.Secret.Secret;
import com.library.lendit_book_kiosk.User.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Objects;
import org.slf4j.Logger;
import java.util.Set;

@Configuration(value = "UserLoginDetails")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk.Security"})
public class UserLoginDetails implements UserDetails{
    private static final Logger log = LoggerFactory.getLogger(UserLoginDetails.class);
    private Long id = null;
    private String username = null;
    private String displayName = null;
    private Secret password = null;
    private Set<GrantedAuthority> authorities = null;

    public UserLoginDetails(){
        this.setUsername("");
        this.setPassword("");

    }
    public UserLoginDetails(
            Long id,
            String username,
            String displayName,
            Secret password,
            Set<GrantedAuthority> authorities){
//        super(username, password,authorities);
        this.setAuthorities(authorities);
        this.setDisplayName(displayName);
        this.setUsername(username);
        this.setId(id);
        this.setPassword(password);
        log.info(toString());
    }
    public UserLoginDetails(
            String username,
            String displayName,
            String password,
            Set<GrantedAuthority> authorities){
//        super(username, password,authorities);
        this.setAuthorities(authorities);
        this.setDisplayName(displayName);
        this.setUsername(username);
        this.setPassword(password);
        log.info(toString());
    }
    public UserLoginDetails(User user) {
        /**
         * MUST pass user credentials to super class holding auth token.
         * Auth tokens can only be authenticated upon creation.
         */
        this.setAuthorities(user.getRoles().stream().map( x -> new SimpleGrantedAuthority(
                "ROLE_" + x.getRole().name())).collect(Collectors.toSet()));
        this.setDisplayName(user.getName());
        this.setUsername(user.getEmail());
        this.setId(user.getId());
        this.setPassword(user.getPasswordClass());
        log.info(user.toString());
        log.info(toString());
    }

    /**
     * Returns true if, and only if, length() of the Object is 0.
     * @returns:
     * true if length() is 0, otherwise false
     */
    public boolean isEmpty(){
        if(username.isEmpty()){
            return true;
        }
        return false;
    }
    /**
     * Set by an AuthenticationManager to indicate the authorities that the principal has been granted.
     * Note that classes should not rely on this value as being valid unless it has been set by a trusted
     * AuthenticationManager.
     * @return the authorities granted to the principal, or an empty collection if the token has not been
     * authenticated. Never null.
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password.getPasswordToString();
    }

    public Secret getPasswordClass(){return this.password;}

    @Override
    public String getUsername() {
        return username;
    }
    // TODO: add variables to replace hard coded values below
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

    public void setPassword(Secret secret) {
        this.password = secret;
    }

    public void setPassword(String password){
        this.password = new Secret(password);
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
                "\"username\":\"" + this.getUsername() + "\",\n" +
                "\"displayName\":\"" + this.getDisplayName() + "\",\n" +
                // TODO: comment out secret on production deploy
                "\"secret\":\"" + this.getPassword() + "\",\n" +
                "\"authorities\":\"" + this.getAuthorities() + "\",\n" +
                "\n}";
    }

}