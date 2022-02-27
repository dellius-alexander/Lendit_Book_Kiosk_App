package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.io.Serializable;
import javax.persistence.*;
/////////////////////////////////////////////////////////////////////
/**
 * This class is a model of the Student Table
 */
@Entity // This tells Hibernate to make a table out of this class
@Table  // This tells Hibernate to make a table out of this class
public class Student implements Serializable {


    // Table outline/fields
    @Id
    @SequenceGenerator(
        name = "student_sequence",
        sequenceName = "student_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "student_sequence"
        )
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    @Transient  // age will be calculated from dob
    private Integer age;
    private String major;
    /**
     * Default constructor
     */
    public Student() {}
    /**
     * Create a new Student with ID
     * @param id
     * @param name
     * @param email
     * @param dob
     * @param major
     */
    public Student(
                    Long id, 
                    String name, 
                    String email, 
                    LocalDate dob, 
                    String major
                    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.major = major;
    }
    /**
     * Create a new student and the Database will auto-generate the ID
     * @param name
     * @param email
     * @param dob
     * @param major
     */
    public Student(
                    String name, 
                    String email, 
                    LocalDate dob, 
                    String major
                    ) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.major = major;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.getDob(), LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Student id(Long id) {
        setId(id);
        return this;
    }

    public Student name(String name) {
        setName(name);
        return this;
    }

    public Student email(String email) {
        setEmail(email);
        return this;
    }

    public Student dob(LocalDate dob) {
        setDob(dob);
        return this;
    }

    // public Student age(Integer age) {
    //     setAge(age);
    //     return this;
    // }

    public Student major(String major) {
        setMajor(major);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return (Objects.equals(id, student.id) 
            && Objects.equals(name, student.name) 
            && Objects.equals(email, student.email) 
            && Objects.equals(dob, student.dob) 
            && Objects.equals(major, student.major));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, dob, major);
    }

    @Override
    public String toString() {
        return "{\n" +
            "\"id\":" + getId() + ",\n" +
            "\"name\":\"" + getName() + "\",\n" +
            "\"email\":\"" + getEmail() + "\",\n" +
            "\"dob\":\"" + getDob() + "\",\n" +
            "\"age\":" + getAge() + ",\n" +
            "\"major\":\"" + getMajor() + "\"" +
            "\n}";
    }
    



}
/////////////////////////////////////////////////////////////////////