package com.library.lendit_book_kiosk.Book.Borrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Transactional
/**
 * Service Layer
 */
@Service(value = "BorrowService")
public class BorrowService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(BorrowService.class);
    private final  BorrowRepository borrowRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepo) {
        this.borrowRepository = borrowRepo;
    }

    public List<Borrow_Book> findBorrow_BookById(Long id){
        return this.borrowRepository.findBorrow_BookById(id);
    }

}
