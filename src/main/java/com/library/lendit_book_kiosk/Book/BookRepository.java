package com.library.lendit_book_kiosk.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Data Access Layer
 */
@Repository(value = "com.library.lendit_book_kiosk.Book.BookRepository")
public interface BookRepository extends JpaRepository<Book, String> {

    // find books by title match
    @Query(value = "SELECT b from Book b WHERE b.title LIKE %?1%")
    List<Book> findBookByTitle(String title);

    // find books by author name match
    @Query(value = "SELECT b from Book b WHERE b.authors LIKE %?1%")
    List<Book> findBookByAuthors(String author);

    // find books by publisher
    @Query(value = "select b from Book b where b.publisher like %?1%")
    List<Book> findBookByPublisher(String publisher);

    // find books by isbn
    @Query(value = "select b from Book b where b.isbn like %?1%")
    Book findBookByIsbn(String isbn);

    // find book by genres
    @Query(value = "SELECT b FROM Book b WHERE b.genres LIKE %?1%")
    List<Book> findBookByGenres(String genres);

    // find book by genres
    @Query(value = "SELECT b FROM Book b WHERE b.description LIKE %?1%")
    List<Book> findBookByDescription(String description);
}
