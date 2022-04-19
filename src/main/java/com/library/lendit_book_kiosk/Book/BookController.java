package com.library.lendit_book_kiosk.Book;

import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserController;
//import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Api Layer <br/>
 * <code style="color:orange;font-style:bold;">/book</code>
 */
@RestController(value = "BookController")
@RequestMapping(value = "/book")
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
            value = {"/getByTitle/{title}"},
            path = {"/getByTitle/{title}"},
            method = {RequestMethod.GET},
            consumes = "*/*",
            produces = "application/json")
    public ResponseEntity<List<Book>> getBooksByTitle(
            @PathVariable("title")
            @RequestParam(value = "title")
                    String title,
            HttpServletRequest req,
            HttpServletResponse res
    )  {
        List<Book> books = null;
        try{
            books = this.bookService.getBooksByTitle(title);
            req.setAttribute("book_list",books);
            res.sendRedirect(req.getRequestURI());
            res.setContentType("application/json");
            log.info("Title: {}",title);

        } catch (IOException e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(books);
    }
}
