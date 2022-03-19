package com.library.lendit_book_kiosk.User;

import com.library.lendit_book_kiosk.Role.UserRole;
import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Student.Student;
import org.hibernate.annotations.SortNatural;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/////////////////////////////////////////////////////////////////

/**
 * User class
 */
@Entity  // Tells Hibernate to make a table out of this class
@Table(name = "User")
public class User implements UserInterface {
    private final static Logger log = LoggerFactory.getLogger(User.class);
    ///////////////////////////////////////////////////////
    @Id
    @SequenceGenerator(  // LendIT Book Kiosk
            name = "TGVuZElUIEJvb2sgS2lvc2s_sequence",
            sequenceName = "TGVuZElUIEJvb2sgS2lvc2s_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            // strategy = AUTO
            strategy = GenerationType.SEQUENCE,
            generator = "TGVuZElUIEJvb2sgS2lvc2s_sequence"
    )
    @Column(
            name = "user_id",
            unique = true,
            columnDefinition = "bigint",
            nullable = false)
    private Long id;
    private String name;
    @Column(  // customize this field to support unique key
            name = "email",
            nullable = false,
            columnDefinition = "varchar(224)",
            unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "gender",
            columnDefinition = "varchar(8)"
    )
    private GENDER gender;
    @Column(
            name = "dob",
            columnDefinition = "date"
    )
    private LocalDate dob;
    private String profession;
    @ManyToMany(
            targetEntity = Role.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn (name = "user_id_fk", nullable = false,table = "user"),
            inverseJoinColumns = @JoinColumn(name = "role_id_fk", nullable = false, table = "role"))
    private Set<Role> roles;
    @ManyToMany(
            targetEntity = Student.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_student",
            joinColumns = @JoinColumn (name = "user_id_fk", nullable = false, table = "user"),
            inverseJoinColumns = @JoinColumn(name = "student_id_fk", nullable = false, table = "student"))
    @SortNatural
    private Set<Student> students;
    ///////////////////////////////////////////////////////

    /**
     * Default Constructor.
     */
    public User(){}

    /**
     * All Args Constructor
     * @param id
     * @param name
     * @param email
     * @param password
     * @param gender
     * @param dob
     * @param profession
     * @param roles
     * @param students
     */
    public User(
            Long id,
            String name,
            String email,
            String password,
            GENDER gender,
            LocalDate dob,
            String profession,
            Set<Role> roles,
            Set<Student> students
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.profession = profession;
        this.roles = roles;
        this.students = students;
    }

    /**
     * All but [id] Args Constructor
     * @param name
     * @param email
     * @param password
     * @param gender
     * @param dob
     * @param profession
     * @param roles
     * @param students
     */
    public User(
            String name,
            String email,
            String password,
            GENDER gender,
            LocalDate dob,
            String profession,
            Set<Role> roles,
            Set<Student> students
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.profession = profession;
        this.roles = roles;
        this.students = students;
    }

    /**
     *
     * @param name
     * @param email
     * @param password
     * @param gender
     * @param dob
     * @param profession
     * @param roles
     */
    public User(
            String name,
            String email,
            String password,
            GENDER gender,
            LocalDate dob,
            String profession,
            Set<Role> roles
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.profession = profession;
        this.roles = roles;
    }


    public User(
            String name,
            String email,
            String password,
            GENDER gender,
            LocalDate dob,
            String profession) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.profession = profession;
    }



    public Long getId() {
        return this.id;
    }
    /**
     * Set user id
     *
     * @param id
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getEmail(){
        return this.email;
    }

    /**
     * Get the user password
     *
     * @return the user password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Get the user gender
     *
     * @return the user gender
     */
    @Override
    public GENDER getGender() {
        return this.gender;
    }

    @Override
    public void  setRole(Role role){
        this.roles.addAll((Collection<? extends Role>) role);
    }
    @Override
    public Integer getAge() {

        return Period.between(this.getDob(), LocalDate.now()).getYears();
    }

    /**
     * Get the user dob
     *
     * @return the user dob
     */
    @Override
    public LocalDate getDob() {
        return this.dob;
    }

    /**
     * Get the user profession
     *
     * @return the user profession
     */
    @Override
    public String getProfession() {
        return this.profession;
    }

    @Override
    public void setUser(User user){
        this.setId(user.getId());
        this.setName(user.getName());
        this.setPassword(user.getPassword());
        this.setRoles(user.getRoles());
        this.setDob(user.getDob());
        this.setEmail(user.getEmail());
        this.setGender(user.getGender());
        this.setProfession(user.getProfession());
    }

    /**
     * Set username
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set user email
     *
     * @param email
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set user password
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set user gender
     *
     * @param gender
     */
    @Override
    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    /**
     * Set user dob
     *
     * @param dob
     */
    @Override
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    /**
     * Set user profession
     *
     * @param profession
     */
    @Override
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * Set user roles
     *
     * @param roles
     */
    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    @Override
    public boolean equals(final Object o) {

        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id))
            return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (!Objects.equals(this$name, other$name))
            return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (!Objects.equals(this$email, other$email))
            return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (!Objects.equals(this$password, other$password))
            return false;
        final Object this$gender = this.getGender();
        final Object other$gender = other.getGender();
        if (!Objects.equals(this$gender, other$gender))
            return false;
        final Object this$dob = this.getDob();
        final Object other$dob = other.getDob();
        if (!Objects.equals(this$dob, other$dob))
            return false;
        final Object this$profession = this.getProfession();
        final Object other$profession = other.getProfession();
        if (!Objects.equals(this$profession, other$profession))
            return false;
        final Object this$roles = this.getRoles();
        final Object other$roles = other.getRoles();
        if (!Objects.equals(this$roles, other$roles))
            return false;
        final Object this$students = this.getStudents();
        final Object other$students = other.getStudents();
        if (!Objects.equals(this$students, other$students))
            return false;
        return true;
    }

    public void setStudents(Set<Student> students){
        this.students = students;
    }
    public void addStudent(Student student){
        this.students = Set.of(student);
    }

    public Set<Student> getStudents(){
        return this.students;
    }
    @Override
    public boolean canEqual(final Object other) {
        return other instanceof User;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $gender = this.getGender();
        result = result * PRIME + ($gender == null ? 43 : $gender.hashCode());
        final Object $dob = this.getDob();
        result = result * PRIME + ($dob == null ? 43 : $dob.hashCode());
        final Object $profession = this.getProfession();
        result = result * PRIME + ($profession == null ? 43 : $profession.hashCode());
        final Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        return result;
    }

    /**
     * Get the user roled
     *
     * @return the user roles
     */
    @Override
    public Set<Role> getRoles() {
        return this.roles;
    }
    @Override
    public String toString() {
        String json = "{" +
                "\n\"id\":" + id +
                ",\n\"name\":\"" + name + "\"" +
                ",\n\"email\":\"" + email + "\"" +
                ",\n\"password\":\"" + password + "\"" +
                ",\n\"gender\":\"" + gender + "\"" +
                ",\n\"dob\":\"" + dob + "\"" +
                ",\n\"profession\":\"" + profession + "\"" +
                ",\n\"roles\":\"" + roles + "\"" +
                ",\n\"student\":\""+ students + "\"" +
                "\n}";
        log.info(json);
        return json;
    }


}
