package com.library.lendit_book_kiosk.Book;

import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserController;
//import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Api Layer <br/>
 * <code style="color:orange;font-style:bold;">/book</code>
 */
@RestController(value = "BookController")
@RequestMapping(value = "/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get books by title using <code>{title}</code> pattern match
     * @param title book title
     * @return a list of books matching
     */
    @RequestMapping(
            value = {"/getByTitle/{title}"},
            path = {"/getByTitle/{title}"},
            method = RequestMethod.GET,
            consumes = "*/*",
            produces = "application/json")
    public ResponseEntity<Set<Book>> getBooksByTitle(
            @PathVariable("title")
            @RequestParam(value = "title")
                    String title
    ){
        log.info("Title: {}",title);
        return ResponseEntity.ok().body(
                this.bookService.getBooksByTitle(title)
        );
    }
}
