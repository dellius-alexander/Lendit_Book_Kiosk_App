package com.library.lendit_book_kiosk.Book.Donated;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "DonationRepository")
public interface DonationRepository extends JpaRepository<Donated_Book, Long> {
}
