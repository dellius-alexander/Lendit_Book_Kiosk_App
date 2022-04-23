package com.library.lendit_book_kiosk.Book.Borrow;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController(value = "BorrowController")
@RequestMapping(value = "/borrow")
public class BorrowController implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(BorrowController.class);
    @Autowired
    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping(
            value = "findById/{id}"
    )
    public ResponseEntity<List<Borrow_Book>> findBorrow_BookById(
            @PathVariable(value = "id") Long id
    ){
        if(id == null){
            throw new IllegalStateException("Student Id is null");
        }
        log.info("Borrow Id: {}",id);
        return ResponseEntity.ok().body( borrowService.findBorrow_BookById(id));
    }
}
