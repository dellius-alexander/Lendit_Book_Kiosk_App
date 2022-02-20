package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/////////////////////////////////////////////////////////////////////
/**
 * Data Access Layer of our Model.
 * Student Repository access the database to retrieve students.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {}
/////////////////////////////////////////////////////////////////////