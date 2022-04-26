package com.library.lendit_book_kiosk.Department.Course;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Department.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Course.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
        name = "course_id",
        unique = true
    )
    private Long id;
    private String course;  // name of course
    @ManyToOne(
        targetEntity = Department.class
    )
    @JoinColumn(
        name = "department",
        referencedColumnName = "department"
    )
    private Department department;
    @ManyToMany(
        targetEntity = Book.class
    )
    @JoinColumns(
        value = {
            @JoinColumn(
                    name = "isbn",
                    referencedColumnName = "isbn"
            ),
            @JoinColumn(
                    name = "subject",
                    referencedColumnName = "genres"
            )
        }
    )
    private Set<Book> books;

    public Course(Long id, String course, Department department) {
        this.id = id;
        this.course = course;
        this.department = department;
    }
    public Course(String course, Department department) {

        this.course = course;
        this.department = department;
    }
    public Course() {
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Long getId() {
        return this.id;
    }

    public String getCourse() {
        return this.course;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Course)) return false;
        final Course other = (Course) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$course = this.getCourse();
        final Object other$course = other.getCourse();
        if (!Objects.equals(this$course, other$course)) return false;
        final Object this$department = this.getDepartment();
        final Object other$department = other.getDepartment();
        if (!Objects.equals(this$department, other$department))
            return false;
        final Object this$books = this.getBooks();
        final Object other$books = other.getBooks();
        if (!Objects.equals(this$books, other$books)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Course;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $course = this.getCourse();
        result = result * PRIME + ($course == null ? 43 : $course.hashCode());
        final Object $department = this.getDepartment();
        result = result * PRIME + ($department == null ? 43 : $department.hashCode());
        final Object $books = this.getBooks();
        result = result * PRIME + ($books == null ? 43 : $books.hashCode());
        return result;
    }

    public String toString() {
        return "Course(id=" + this.getId() + ", course=" + this.getCourse() + ", department=" + this.getDepartment() + ", books=" + this.getBooks() + ")";
    }
}
