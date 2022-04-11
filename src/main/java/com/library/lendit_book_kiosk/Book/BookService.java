package com.library.lendit_book_kiosk.Book;

import org.springframework.beans.factory.annotation.Autowired;
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
     * Get books by title
     * @param title book title
     * @return a list of books
     */
    public List<Book> getBooksByTitle(String title){

        List<Book> books = this.bookRepository.findBookByTitle(title);
        log.info("\nBooks: {}\n",books.stream().collect(Collectors.toList()));
        return books;
    }
}
