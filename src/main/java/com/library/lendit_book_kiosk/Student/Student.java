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
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

// import com.library.lendit_book_kiosk.Role.Role;
// import com.library.lendit_book_kiosk.Role.Role;
// LOGGING CLASSES
/////////////////////////////////////////////////////////////////////
/**
 * This class is a model of the Student Table
 */
/////////////////////////////////////////////////////////////////
// Tells Hibernate to make a table out of this class
@Entity
@Table(name = "Student") // Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: com.library.lendit_book_kiosk.Student.Student
public class Student implements StudentInterface {
    private final static Logger log = LoggerFactory.getLogger(Student.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            // strategy = AUTO
            strategy = GenerationType.SEQUENCE,
            generator = "TGVuZElUIEJvb2sgS2lvc2s_sequence"
    )
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "student_id",
            unique = true,
            columnDefinition = "bigint",
            nullable = false)
    private Long Id;
    private boolean enrolled;
    // TODO: Expand String to Class Major to Create Set<Major>
    @ManyToMany(
            targetEntity = Major.class,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_majors",
            joinColumns = @JoinColumn(name = "student_id_fk", nullable = false, table = "student"),
            inverseJoinColumns = @JoinColumn(name = "major_id_fk", nullable = false, table = "major")
    )
    private Set<Major> majors;

//            = new AbstractSet<>() {
//        @Override
//        public Iterator<Major> iterator() {
//            return majors.iterator();
//        }
//        @Override
//        public int size() {
//            return majors.size();
//        }
//    };
    @ManyToMany(
            mappedBy = "students"
    )
    private Set<User> users;
    ///////////////////////////////////////////////////////
    public Student() {}

    public Student(boolean enrolled, Set<Major> majors){
        this.enrolled = enrolled;
        this.majors = majors;
    }

    public Student(
            Long id,
            boolean enrolled,
            Set<Major> majors
    ) {
        this.Id = id;
        this.enrolled = enrolled;
        this.majors = majors;
    }

    public Long getStudentId() {
        return Id;
    }

    @Override
    public void setStudentId(Long Id) {
        this.Id = Id;
    }

    @Override
    public Set<Major> getMajors() {
        return this.majors;
    }


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

    @Override
    public boolean canEqual(final Object other) {
        return other instanceof Student;
    }

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


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Student)) return false;
        final Student other = (Student) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getStudentId();
        final Object other$id = other.getStudentId();
        if (!Objects.equals(this$id, other$id)) return false;
        if (this.isEnrolled() != other.isEnrolled()) return false;
        final Object this$majors = this.getMajors();
        final Object other$majors = other.getMajors();
        if (!Objects.equals(this$majors, other$majors)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getStudentId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + (this.isEnrolled() ? 79 : 97);
        final Object $majors = this.getMajors();
        result = result * PRIME + ($majors == null ? 43 : $majors.hashCode());
        return result;
    }

    public String toString() {
        return "{\n" +
                "\"id\":" + this.getStudentId() + ",\n" +
                "\"enrolled\":\"" + this.isEnrolled() + "\",\n" +
                "\"majors\":\"" + this.getMajors() + "\"\n" +
                "}";
    }


}
/////////////////////////////////////////////////////////////////////