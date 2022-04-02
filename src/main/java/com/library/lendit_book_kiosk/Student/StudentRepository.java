package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.library.lendit_book_kiosk.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
/////////////////////////////////////////////////////////////////////
/**
 * Data Access Layer of our Model.
 * Student Repository access the database to retrieve students.
 * @extends JpaRepository<Student, Long>
 */
@Repository(value = "com.library.lendit_book_kiosk.Student.StudentRepository")
public interface StudentRepository extends JpaRepository<Student, Long>    {

    UserRepository userRepository = null;
//    /**
//     * Custom query to locate student by email
//     * @param email
//     * @return
//     */
//    @Query("SELECT s FROM Student s WHERE s.user.email = ?1")
//    Optional<Student> findStudentByEmail(String email);

//    /**
//     * Custom query to locate student by email and password
//     * @param email
//     * @return
//     */
//    @Query("SELECT s FROM Student s WHERE s.user.email = ?1 and s.user.password = ?2")
//    Optional<Student> findStudentByEmailAAndPassword(String email, String password);

    /**
     * Find student by id
     * @param id studentId
     * @return  Optional<Student>
     */
    @Query("SELECT s FROM Student s WHERE s.Id = ?1")
    Optional<Student> findStudentById(Long id);

    /**
     * Find all students enrolled|not-enrolled
     * @param enrolled true|false
     * @return List<Student>
     */
    @Query("SELECT s FROM Student s WHERE s.enrolled = ?1")
    List<Student> findStudentsByEnrolled(boolean enrolled);

}
    
/////////////////////////////////////////////////////////////////////