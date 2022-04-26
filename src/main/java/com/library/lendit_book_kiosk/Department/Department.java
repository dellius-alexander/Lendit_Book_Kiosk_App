package com.library.lendit_book_kiosk.Department;

import com.library.lendit_book_kiosk.Department.Course.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "department")
public class Department implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Department.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "department",
            unique = true
    )
    private String department;
    @OneToMany(
            mappedBy = "department"
    )
    private Set<Course> course;

    public Department(String department) {
        this.department = department;
    }

    public Department(String department, Set<Course> course) {
        this.course = course;
        this.department = department;
    }
    public Department() {
    }
    /////////////////////////////////////////////////////////////////
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getDepartment() {
        return this.department;
    }

    public Set<Course> getCourse() {
        return this.course;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCourse(Set<Course> course) {
        this.course = course;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Department)) return false;
        final Department other = (Department) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$department = this.getDepartment();
        final Object other$department = other.getDepartment();
        if (!Objects.equals(this$department, other$department))
            return false;
        final Object this$course = this.getCourse();
        final Object other$course = other.getCourse();
        if (!Objects.equals(this$course, other$course)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Department;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $department = this.getDepartment();
        result = result * PRIME + ($department == null ? 43 : $department.hashCode());
        final Object $course = this.getCourse();
        result = result * PRIME + ($course == null ? 43 : $course.hashCode());
        return result;
    }

    public String toString() {
        return "Department(department=" + this.getDepartment() + ", course=" + this.getCourse() + ")";
    }
}
