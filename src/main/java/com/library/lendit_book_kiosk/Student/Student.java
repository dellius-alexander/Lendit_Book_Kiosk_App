package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
// import java.util.List;
// import java.util.ArrayList;
// import java.util.List;

import com.library.lendit_book_kiosk.User.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

// import com.library.lendit_book_kiosk.Role.Role;
// import com.library.lendit_book_kiosk.Role.Role;
// LOGGING CLASSES
/////////////////////////////////////////////////////////////////////
/**
 * This class is a model of the Student Table
 */
// Tells Hibernate to make a table out of this class
///////////////////////////////////////////////////////
// Tells Hibernate to make a table out of this class
@Entity
@Table(name = "Student") // Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: com.library.lendit_book_kiosk.Student.Student
public class Student implements StudentInterface {
    private final static Logger log = LoggerFactory.getLogger(Student.class);
    ///////////////////////////////////////////////////////
    // Table outline/fields
    //    @Id
    //    @Column(name = "student_id")
    //    @SequenceGenerator(
    //        name = "student_sequence",
    //        sequenceName = "student_sequence",
    //        allocationSize = 1
    //    )
    //    @GeneratedValue(
    //        // strategy = AUTO
    //        strategy = GenerationType.SEQUENCE,
    //        generator = "student_sequence"
    //    )
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "student_id",
            unique = true
    )
    private Long id;
    private boolean enrolled;
    // TODO: Expand String to Class Major to Create Set<Major>
    @OneToMany(
            targetEntity = Major.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})

    private Set<Major> majors;
    @ManyToMany(
//            targetEntity = User.class,
//            fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL
            mappedBy = "student"
    )
    private Set<User> users;
    ///////////////////////////////////////////////////////


    public Student(boolean enrolled, Set<Major> majors){
        this.enrolled = enrolled;
        this.majors = majors;
    }

    public Student(
            Long id,
            boolean enrolled,
            Set<Major> majors
//            Set<User> users
    ) {
        this.id = id;
        this.enrolled = enrolled;
        this.majors = majors;
//        this.users = users;
    }

    public Student() {
    }

//    @Override
//    public User getUser(){
//        return this.users.stream().findFirst().get();
//    }
//    @Override
//    public void setUsers(Set<User> users){
//        this.users = users;
//    }
//    @Override
//    public  void setUser(User user){
//        this.users = Set.of(user);
//    }
//    @Override
//    public Set<User> getUsers(){ return this.users;}

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Set<Major> getMajors() {
        return this.majors;
    }

//    @Override
//    public Set<User> getUsers() {
//        return this.users;
//    }
//    @Override
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    @Override
    public void setEnrolled(boolean enrolled){
        this.enrolled = enrolled;
    }

    @Override
    public boolean getEnrolled(){
        return this.enrolled;
    }

    @Override
    public void setMajors(Set<Major> majors) { this.majors = majors; }

//    @Override
//    public void setUser(User user) { this.user = user; }

    @Override
    public boolean canEqual(final Object other) {
        return other instanceof Student;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Student)) return false;
//        Student student = (Student) o;
//        return getId().equals(student.getId()) && getMajors().equals(student.getMajors());
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(this.id, this.majors, this.enrolled);
//    }

    public boolean isEnrolled(){
        return this.enrolled;
    }

    @Override
    public int compareTo(Student s) {
        return this.compareTo(s);
    }

    @Override
    public int compareTo(Object s) {
        return this.compareTo(s);
    }

//    public Set<User> getUsers() {
//        return this.users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Student)) return false;
        final Student other = (Student) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        if (this.isEnrolled() != other.isEnrolled()) return false;
        final Object this$majors = this.getMajors();
        final Object other$majors = other.getMajors();
        if (!Objects.equals(this$majors, other$majors)) return false;

//        final Object this$users = this.getUsers();
//        final Object other$users = other.getUsers();
//        if (!Objects.equals(this$users, other$users)) return false;

        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + (this.isEnrolled() ? 79 : 97);
        final Object $majors = this.getMajors();
        result = result * PRIME + ($majors == null ? 43 : $majors.hashCode());

//        final Object $users = this.getUsers();
//        result = result * PRIME + ($users == null ? 43 : $users.hashCode());

        return result;
    }

    public String toString() {
        return "{\n\"id\":" + this.getId() +
                ",\n\"enrolled\":\"" + this.isEnrolled() +
                "\",\n\"majors\":\"" + this.getMajors() +
//                "\",\n\"users\":\"" + this.getUsers() +
                "\"\n}";
    }


}
/////////////////////////////////////////////////////////////////////