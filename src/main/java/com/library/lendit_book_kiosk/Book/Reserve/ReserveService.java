package com.library.lendit_book_kiosk.Book.Reserve;

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookRepository;
import com.library.lendit_book_kiosk.Book.Copy.Book_Copy;
import com.library.lendit_book_kiosk.Book.Copy.CopyRepository;
import com.library.lendit_book_kiosk.Student.Student;
import com.library.lendit_book_kiosk.Student.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.Serializable;

@Service(value = "ReserveService")
public class ReserveService implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(ReserveService.class);
    @Autowired
    private final ReserveRepository reserveRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final StudentService studentService;

    public ReserveService(
            ReserveRepository reserveRepository,
            BookRepository bookRepository,
            StudentService studentService) {
        this.reserveRepository = reserveRepository;
        this.bookRepository = bookRepository;
        this.studentService = studentService;
    }

    public ResponseEntity reserveBookByIsbn(String isbn,Long student_id){
        try{
            Student student = studentService.findStudentById(student_id);
            Book book = bookRepository.findBookByIsbn(isbn);
            reserveRepository.save(new Reserve_Book(
                    new Book_Copy(book),student
            ));
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.ok().body(new IllegalStateException(
                    "<p>Something went wrong book or student id invalid.</p>"
            ));
        }
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }


}
