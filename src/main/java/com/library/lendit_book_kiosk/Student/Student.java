package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
// import java.util.List;
// import java.util.ArrayList;
// import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

// import com.library.lendit_book_kiosk.Role.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.library.lendit_book_kiosk.User.User;

// import com.library.lendit_book_kiosk.Role.Role;
// LOGGING CLASSES
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
public class Student implements StudentInterface{
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
        strategy = GenerationType.SEQUENCE,
        generator = "student_sequence"
    )
    private Long id;
    private boolean enrolled;
    // TODO: Expand String to Class Major to Create Set<Major>
    @ManyToMany(
            targetEntity = Major.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinTable(
            name = "student_majors",
            joinColumns = @JoinColumn (name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "major_id"))
    private Set<Major> majors;
    @ManyToMany(
            mappedBy = "students",
//            targetEntity = User.class,
//            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
//    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private Set<User> users;
    ///////////////////////////////////////////////////////


    public Student(boolean enrolled, Set<Major> majors){
        this.enrolled = enrolled;
        this.majors = majors;
    }

    public Student() {

    }

//    public User getUser(){
//        return this.users.stream().findFirst().get();
//    }
//    public void setUsers(Set<User> users){
//        this.users = users;
//    }

//    public  void setUser(User user){
//        this.users = Set.of(user);
//    }

//    public Set<User> getUsers(){ return this.users;}

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Set<Major> getMajors() {
        return this.majors;
    }

//    @Override
//    public User getUser() {
//        return this.user;
//    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setMajors(Set<Major> majors) { this.majors = majors; }

//    @Override
//    public void setUser(User user) { this.user = user; }

    @Override
    public boolean canEqual(final Object other) {
        return other instanceof Student;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return getId().equals(student.getId()) && getMajors().equals(student.getMajors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.majors, this.users);
    }

    @Override
    public int compareTo(Student s) {
        return this.compareTo(s);
    }

    @Override
    public int compareTo(Object s) {
        return this.compareTo(s);
    }
}
/////////////////////////////////////////////////////////////////////