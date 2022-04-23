package com.library.lendit_book_kiosk.Book.Copy;


import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.Kiosk.Kiosk;
import com.library.lendit_book_kiosk.Book.Reserve.Reserve_Book;
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
@Table(name = "book_copy")
public class Book_Copy implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Book_Copy.class);
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "LendIT_Book_Kiosk_DB_Sequence_Generator"
    )
    @Column(
            name = "copy_id",
            unique = true

    )
    private Long id;
    @ManyToOne(
            targetEntity = Book.class
    )
    @JoinColumn(
            name="isbn",
            nullable=false,
            updatable = false,
            insertable = false)
    private Book book_copy;
    @OneToMany(
            mappedBy = "book_copy"
    )
    private Set<Kiosk> kiosk;
    @OneToMany(
            mappedBy = "book_copy"
    )
    private Set<Reserve_Book> reserve_books;

}
