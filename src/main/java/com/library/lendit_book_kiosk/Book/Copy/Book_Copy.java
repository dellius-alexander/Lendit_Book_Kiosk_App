package com.library.lendit_book_kiosk.Book.Copy;


import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.Kiosk.Kiosk;
import com.library.lendit_book_kiosk.Book.Reserve.Reserve_Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Long getId() {
        return this.id;
    }

    public Book getBook_copy() {
        return this.book_copy;
    }

    public Set<Kiosk> getKiosk() {
        return this.kiosk;
    }

    public Set<Reserve_Book> getReserve_books() {
        return this.reserve_books;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBook_copy(Book book_copy) {
        this.book_copy = book_copy;
    }

    public void setKiosk(Set<Kiosk> kiosk) {
        this.kiosk = kiosk;
    }

    public void setReserve_books(Set<Reserve_Book> reserve_books) {
        this.reserve_books = reserve_books;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Book_Copy)) return false;
        final Book_Copy other = (Book_Copy) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$book_copy = this.getBook_copy();
        final Object other$book_copy = other.getBook_copy();
        if (!Objects.equals(this$book_copy, other$book_copy)) return false;
        final Object this$kiosk = this.getKiosk();
        final Object other$kiosk = other.getKiosk();
        if (!Objects.equals(this$kiosk, other$kiosk)) return false;
        final Object this$reserve_books = this.getReserve_books();
        final Object other$reserve_books = other.getReserve_books();
        if (!Objects.equals(this$reserve_books, other$reserve_books))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Book_Copy;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $book_copy = this.getBook_copy();
        result = result * PRIME + ($book_copy == null ? 43 : $book_copy.hashCode());
        final Object $kiosk = this.getKiosk();
        result = result * PRIME + ($kiosk == null ? 43 : $kiosk.hashCode());
        final Object $reserve_books = this.getReserve_books();
        result = result * PRIME + ($reserve_books == null ? 43 : $reserve_books.hashCode());
        return result;
    }

    public String toString() {
        return "Book_Copy(id=" + this.getId() + ", book_copy=" + this.getBook_copy() + ", kiosk=" + this.getKiosk() + ", reserve_books=" + this.getReserve_books() + ")";
    }
}
