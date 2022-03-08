package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.List;
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
    
    /**
     * Custom query to locate student by email
     * @param email
     * @return
     */
    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    /**
     * Custom query to locate student by email and password
     * @param email
     * @return
     */
    @Query("SELECT s FROM Student s WHERE s.email = ?1 and s.password = ?2")
    Optional<Student> findStudentByEmailAAndPassword(String email, String password);

    /**
     * custom query to locate student by id
     * @param id
     * @return
     */
    @Query("SELECT s FROM Student s WHERE s.id = ?1")
    Optional<Student> findStudentById(Long id);

    /**
     * custom query to locate student by name
     * @param name
     * @return
     */
    @Query("SELECT s FROM Student s WHERE s.name like ?1")
    List<Student> findStudentByName(String name);

}
    
/////////////////////////////////////////////////////////////////////