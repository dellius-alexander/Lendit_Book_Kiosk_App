package com.library.lendit_book_kiosk.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzTransactionManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service layer control logic
 */
@Service(value = "BookService")
public class BookService {
    private final static Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

     /**
     * Find books By Title
     * @param title book title
     * @return list of books matching the search term(s)
     */
    public List<Book> getBooksByTitle(String title){

        List<Book> books = this.bookRepository.findBookByTitle(title);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    /**
     * Find book By Author
     * @param author
     * @return list of books matching the search term(s)
     */
    public List<Book> getBooksByAuthor(String author){
        List<Book> books = this.bookRepository.findBookByAuthors(author);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    /**
     * Find book By Genres
     * @param genres
     * @return list of books matching the search term(s)
     */
    public List<Book> getBooksByGenres(String genres){
        List<Book> books = this.bookRepository.findBookByGenres(genres);
        log.info("\nBooks: {}\n",books);
        return books;
    }

    public List<Book> getBooksByDescription(String description){
        List<Book> books = this.bookRepository.findBookByDescription(description);
        log.info("\nBooks: {}\n",books);
        return books;
    }
}
