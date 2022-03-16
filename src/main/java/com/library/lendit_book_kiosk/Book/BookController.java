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

//@Api(value = "Books Rest Controller", description = "REST API for Books")
@RestController(value = "/api/v1/book")
@RequestMapping(value = "/api/v1/book")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = {"/getByTitle/{title}"})
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable("title") String title){
        return ResponseEntity.ok().body(
                this.bookService.getBooksByTitle(title)
        );
    }
}
