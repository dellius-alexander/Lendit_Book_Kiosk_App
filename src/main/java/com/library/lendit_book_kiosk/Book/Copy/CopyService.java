package com.library.lendit_book_kiosk.Book.Copy;

import com.library.lendit_book_kiosk.Book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CopyService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(CopyService.class);
    @Autowired
    private final CopyRepository copyRepository;
    @Autowired
    private final BookRepository bookRepository;

    public CopyService(CopyRepository copyRepository, BookRepository bookRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }


}
