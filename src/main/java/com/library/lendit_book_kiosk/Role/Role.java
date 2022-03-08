package com.library.lendit_book_kiosk.Role;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.*;

import com.library.lendit_book_kiosk.User.User;

import lombok.*;

/////////////////////////////////////////////////////////////////////


// @NoArgsConstructor 
// @AllArgsConstructor 

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
@NoArgsConstructor
@AllArgsConstructor
@Entity 
@Table(name = "Role")
public class Role implements RoleInterface {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.
    getLogger(Role.class);

    // Table outline/fields
    @Id
    @Column(name = "role_id")
    @SequenceGenerator(
        name = "role_sequence",
        sequenceName = "role_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        // strategy = AUTO
        strategy = GenerationType.SEQUENCE,
        generator = "role_sequence"
        )
    private Long id;
    private String name;
    private String description;
    @ManyToMany(
            targetEntity = User.class,
            fetch = FetchType.EAGER,
            mappedBy = "roles",
            cascade = CascadeType.ALL
    )
    private Set<User> users;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
        log.info("ROLE: {}", this.toString());
    }

    public Role(String name) {
        this.name = name;
    }
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) { this.id = id; }

    @Override
    public String getRole() {
        return this.name;
    }

    @Override
    public void setRole(String name) {
        this.name = name;
    }

    @Override
    public void setDescription(String description){ this.description = description; }

    @Override
    public String getDescription(){ return this.description; }

    @Override
    public Role getRoleById(Long id) {
        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        return null;
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
        return Objects.equals(this.id, role.id) && Objects.equals(this.name, role.name);
    }

    
    @Override
    public boolean equals(String name1, String name2){
        return  Pattern.compile(Pattern.quote(name1), 
                Pattern.CASE_INSENSITIVE).matcher(name2).find();
    }

    @Override
    public boolean equals(Role role1, Role role2){
        return  Pattern.compile(Pattern.quote(role1.getRole()), 
                Pattern.CASE_INSENSITIVE).matcher(role2.getRole()).find();
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
        log.info("\nROLE: {}\n",json);
        return json;
    }

}
