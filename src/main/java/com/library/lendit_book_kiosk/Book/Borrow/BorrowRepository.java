package com.library.lendit_book_kiosk.Book.Borrow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Data Access Layer
 */
@Repository(value = "com.library.lendit_book_kiosk.Book.Borrow.BorrowRepository")
public interface BorrowRepository extends JpaRepository<Borrow_Book, Long> {

    @Query(value = "SELECT b FROM Borrow_Book b WHERE b.id = ?1")
    List<Borrow_Book> findBorrow_BookById(Long id);

}
