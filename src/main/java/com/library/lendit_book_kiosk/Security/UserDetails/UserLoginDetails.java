package com.library.lendit_book_kiosk.Security.UserDetails;

import com.library.lendit_book_kiosk.Security.Custom.CustomPasswordEncoder;
import com.library.lendit_book_kiosk.User.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Objects;
import org.slf4j.Logger;
import java.util.Set;

@Configuration(value = "UserLoginDetails")
@ComponentScan(basePackages = {"com.library.lendit_book_kiosk"})
public class UserLoginDetails extends UsernamePasswordAuthenticationToken implements UserDetails{
    private static final Logger log = LoggerFactory.getLogger(UserLoginDetails.class);
    private Long id;
    private String username;
    private String displayName;
    private String password;
    private Set<GrantedAuthority> authorities;

    public UserLoginDetails(){
        super("","");
        this.setUsername("");
        this.setPassword("");
        ;
    }

    public UserLoginDetails(
            Long id,
            String username,
            String displayName,
            String password,
            Set<GrantedAuthority> authorities){
        super(username,password,authorities);
        this.setAuthorities(authorities);
        this.setDisplayName(displayName);
        this.setUsername(username);
        this.setId(id);
        this.setPassword(new CustomPasswordEncoder().encode(password));
        super.setDetails(this);
        log.info(toString());
    }

    public UserLoginDetails(User user) {
        /**
         * MUST pass user credentials to super class holding auth token.
         * Auth tokens can only be authenticated upon creation.
         */
        super(
                user.getEmail(),
                new CustomPasswordEncoder().encode(user.getPassword()),
                user.getRoles().stream().map( x -> new SimpleGrantedAuthority(
                        "ROLE_" + x.getRole().name())).collect(Collectors.toSet())
        );
        this.setAuthorities(user.getRoles().stream().map( x -> new SimpleGrantedAuthority(
                "ROLE_" + x.getRole().name())).collect(Collectors.toSet()));
        this.setDisplayName(user.getName());
        this.setUsername(user.getEmail());
        this.setId(user.getId());
        this.setPassword(new CustomPasswordEncoder().encode(user.getPassword()));
        this.setDetails(user);
        log.info(toString());
    }
    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(){
        return new UsernamePasswordAuthenticationToken(
                        getPrincipal(), getCredentials(),getAuthorities());
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
        return password;
    }

    @Override
    public String getName(){return username;}

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

    /**
     * The password
     * @return
     */
    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }

    /**
     * The username
     * @return
     */
    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public void setDetails(Object details) {
        super.setDetails(this);
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

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
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
                // TODO: comment out password on production deploy
                "\"password\":\"" + this.getPassword() + "\",\n" +
                "\"authorities\":\"" + this.getAuthorities() + "\",\n" +
                "\"principal\":\"" + this.getPrincipal() + "\",\n" +
                "\n}";
    }
}