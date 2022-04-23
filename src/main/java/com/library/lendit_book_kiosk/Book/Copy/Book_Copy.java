package com.library.lendit_book_kiosk.Book.Copy;


import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.Kiosk.Kiosk;
import com.library.lendit_book_kiosk.Book.Reserve.Reserve_Book;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

    public Book_Copy(Long id, Book book_copy, Set<Kiosk> kiosk, Set<Reserve_Book> reserve_books) {
        this.id = id;
        this.book_copy = book_copy;
        this.kiosk = kiosk;
        this.reserve_books = reserve_books;
    }
    public Book_Copy(Book book_copy, Set<Kiosk> kiosk, Set<Reserve_Book> reserve_books) {

        this.book_copy = book_copy;
        this.kiosk = kiosk;
        this.reserve_books = reserve_books;
    }

    public Book_Copy(Book book_copy, Set<Reserve_Book> reserve_books) {
        this.book_copy = book_copy;
        this.reserve_books = reserve_books;
    }
    public Book_Copy(Book book_copy) {
        this.book_copy = book_copy;
    }
    public Book_Copy() {
    }
}
