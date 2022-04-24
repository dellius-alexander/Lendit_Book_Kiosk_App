package com.library.lendit_book_kiosk.Book.Kiosk;

import com.library.lendit_book_kiosk.Book.Copy.Book_Copy;
import com.library.lendit_book_kiosk.Book.Borrow.Borrow_Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
/**
 * Represents a single kiosk slot in the Book Kiosk Dispenser
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "kiosk")
public class Kiosk implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Kiosk.class);
    /////////////////////////////////////////////////////////////////
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "slot_id",
            unique = true
    )
    private Long id;
    @ManyToOne(
            targetEntity = Book_Copy.class
    )
    @JoinColumn(name = "copy_id")
    private Book_Copy book_copy;
    @OneToMany(
            mappedBy = "kiosk"
    )
    private Set<Borrow_Book> borrow_books;

}
