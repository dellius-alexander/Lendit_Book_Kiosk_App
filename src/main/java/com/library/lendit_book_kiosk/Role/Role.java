package com.library.lendit_book_kiosk.Role;
// LOGGING CLASSES

import com.library.lendit_book_kiosk.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/////////////////////////////////////////////////////////////////////



/** 
 * This tells Hibernate to make a table out of this class.
 * And saves the Data.
 * */

/**
 * This class defines the USER ROLES.</br>
 * ROLES: 
 *   ADMIN,
 *   STUDENT,
 *   FACULTY,
 *   GUEST,
 *   SUPERUSER
 */
@Entity
@Table(name = "Role")
public class Role implements RoleInterface, Serializable {

    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.
    getLogger(Role.class);
    /////////////////////////////////////////////////////////////////
    @Id
//
    @GeneratedValue(
            // strategy = AUTO
            strategy = GenerationType.SEQUENCE,
            generator = "TGVuZElUIEJvb2sgS2lvc2s_sequence"
    )
    @Column(name = "role_id",
            unique = true,
            columnDefinition = "bigint",
            nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    // TODO: fix duplicate role names
    @Column(
//            unique = true,
            columnDefinition = "varchar(224)"
    )
    private UserRole name;
    private String description;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(Long id, UserRole name) {
        this.id = id;
        this.name = name;
        log.info("UserRole: {}", this.toString());
    }

    public Role(UserRole name,String description) {
        this.name = name;
        this.description = description;
    }

    public Role(
            Long id,
            UserRole name,
            String description
//            Set<User> users
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
//        this.users = users;
    }

    public Role() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public UserRole getRole() {
        return this.name;
    }

    @Override
    public void setRole(UserRole name) {
        this.name = name;
    }
    @Override
    public void setRole(Role role) {
        this.name = role.name;
    }
    @Override
    public void setDescription(String description){
        this.description = description; }

    @Override
    public String getDescription(){ return this.description; }

    @Override
    public Role getRoleById(Long id) {
        return null;
    }

    @Override
    public Role getRoleByName(UserRole name) {
        return this;
    }

    @Override
    public Role getRoleByid(Long id) {
        if (this.id == id)
            return this;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Role)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(this.id, role.id)
                && Objects.equals(this.name, role.name);
    }

    
    @Override
    public boolean equals(UserRole name1, UserRole name2){
        return  Pattern.compile(Pattern.quote(name1.name()),
                Pattern.CASE_INSENSITIVE).matcher(name2.name()).find();
    }

    @Override
    public boolean equals(
            Role role1,
            Role role2
    ){
        return  Pattern.compile(Pattern.quote(role1.getRole().name()),
                        Pattern.CASE_INSENSITIVE).matcher(role2.getRole().name()).find();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        String json = String.format("{\n" +
            "\"id\":" + getId() +
            ",\n\"name\":\"" + getRole() + "\"" +
            ",\n\"description\":\"" + getDescription() + "\"" +
            "\n}");
        log.info("\nUserRole: {}\n",json);
        return json;
    }

}
