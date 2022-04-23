package com.library.lendit_book_kiosk.Book.Reserve;

import com.library.lendit_book_kiosk.Book.BookRepository;
import com.library.lendit_book_kiosk.Book.Copy.Book_Copy;
import com.library.lendit_book_kiosk.Student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "reserve_book")
public class Reserve_Book implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Reserve_Book.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "id",
            unique = true

    )
    private Long id;
    @ManyToOne(
            targetEntity = Book_Copy.class,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "copy_id")
    private Book_Copy book_copy;
    @ManyToOne(
            targetEntity = Student.class,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "student_id"
    )
    private Student student;

    public Reserve_Book(Book_Copy book_copy, Student student) {
        this.book_copy = book_copy;
        this.student = student;
    }
}
