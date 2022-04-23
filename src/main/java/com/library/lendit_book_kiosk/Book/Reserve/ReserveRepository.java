package com.library.lendit_book_kiosk.Book.Reserve;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ReserveRepository")
public interface ReserveRepository extends JpaRepository<Reserve_Book, Long> {
}
