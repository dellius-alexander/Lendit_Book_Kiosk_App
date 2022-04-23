package com.library.lendit_book_kiosk.Book.Copy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "CopyRepository")
public interface CopyRepository extends JpaRepository<Book_Copy, Long> {

}
