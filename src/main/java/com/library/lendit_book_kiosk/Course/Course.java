package com.library.lendit_book_kiosk.Course;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Department.Department;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
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

}
