package com.library.lendit_book_kiosk.Fines;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "FinesRepository")
public interface FinesRepository extends JpaRepository<Fines, Long> {
}
