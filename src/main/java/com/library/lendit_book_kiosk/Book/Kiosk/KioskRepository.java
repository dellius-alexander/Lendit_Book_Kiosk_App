package com.library.lendit_book_kiosk.Book.Kiosk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "KioskRepository")
public interface KioskRepository extends JpaRepository<Kiosk, Long> {
}
