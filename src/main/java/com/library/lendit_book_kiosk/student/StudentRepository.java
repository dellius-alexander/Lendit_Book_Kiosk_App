package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/////////////////////////////////////////////////////////////////////
/**
 * Data Access Layer of our Model.
 * Student Repository access the database to retrieve students.
 * @extends JpaRepository<Student, Long>
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>  {
    
    // @Query provides a way to customize the query to findStudentByEmail
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    }
    
/////////////////////////////////////////////////////////////////////