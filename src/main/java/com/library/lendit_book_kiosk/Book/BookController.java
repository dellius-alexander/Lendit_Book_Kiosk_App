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
/**
 * Api Layer <br/>
 * <code style="color:orange;font-style:bold;">/api/v1/book</code>
 */
@RestController
@RequestMapping(value = "/api/v1/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
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
            value = {"/getByTitle/{title}","/getByTitle/title={title}"},
            method = RequestMethod.GET,
            consumes = {"text/*"},
            produces = "application/json")
    public ResponseEntity<List<Book>> getBooksByTitle(
            @PathVariable("title") String title
    ){
        return ResponseEntity.ok().body(
                this.bookService.getBooksByTitle(title)
        );
    }
}
