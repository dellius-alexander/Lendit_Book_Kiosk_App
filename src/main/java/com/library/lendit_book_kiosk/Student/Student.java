package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.time.LocalDate;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Set;
import javax.persistence.*;

// import com.library.lendit_book_kiosk.Role.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.User.User;

// import com.library.lendit_book_kiosk.Role.Role;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
/**
 * This class is a model of the Student Table
 */
// Tells Hibernate to make a table out of this class
///////////////////////////////////////////////////////

//@NoArgsConstructor
//@AllArgsConstructor
//@Data
@Entity  // Tells Hibernate to make a table out of this class
//@Table(name = "Student") // Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: com.library.lendit_book_kiosk.Student.Student
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Student extends User implements StudentInterface {
    private final static Logger log = LoggerFactory.getLogger(Student.class);
    ///////////////////////////////////////////////////////
    // Table outline/fields
    @Id
    @Column(name = "student_id")
    @SequenceGenerator(
        name = "student_sequence",
        sequenceName = "student_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        // strategy = AUTO
        strategy = GenerationType.IDENTITY,
        generator = "student_sequence"
    )
    private Long id;
    // TODO: Expand String to Class Major to Create Set<Major>
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "student_majors",
            joinColumns = @JoinColumn (name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "major_id")
    )
    private Set<Major> majors;
    @ManyToMany(
            targetEntity = User.class,
            mappedBy = "students",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<User> users;
    ///////////////////////////////////////////////////////

    public Student(
            String name,
            String email,
            String password,
            String gender,
            LocalDate dob,
            String profession,
            Set<Role> roles,
            Set<Major> majors,
            Set<Student> students
    ){
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.setGender(gender);
        this.setDob(dob);
        this.setProfession(profession);
        this.setRoles(roles);
        this.setMajors(majors);
        this.setStudents(students);
    }

    public Student() {}

    public Student(
            User user,
            Set<Major> majors
    ){
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setGender(user.getGender());
        this.setDob(user.getDob());
        this.setProfession(user.getProfession());
        this.setRoles(user.getRoles());
        this.setMajors(majors);
    }

    @Override
    public boolean canEqual(final Object other) {
        return other instanceof Student;
    }

    @Override
    public Set<Major> getMajors() {
        return this.majors;
    }

    @Override
    public Set<User> getUser() {
        return this.users;
    }

    @Override
    public void setMajors(Set<Major> majors) {
        this.majors = majors;
    }

    @Override
    public void setUser(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\":" + id +
                ",\n\"majors\":\"" + majors + "\"" +
                ",\n\"user\":\"" + users + "\"" +
                "\n}";
    }
}
/////////////////////////////////////////////////////////////////////