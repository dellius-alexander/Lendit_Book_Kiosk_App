package com.library.lendit_book_kiosk.Department;

import com.library.lendit_book_kiosk.Book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
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
    private String name;
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
    @JoinColumn(
            name = "isbn",
            referencedColumnName = "isbn"
    )
    private Set<Book> books;
}
