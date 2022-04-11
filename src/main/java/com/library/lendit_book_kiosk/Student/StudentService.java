package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.*;

import java.io.Serializable;

// LOGGING CLASSES
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/////////////////////////////////////////////////////////////////////
/**
 * Service Layer Class that supports our API layer
 */
@Transactional
@Service(value = "StudentService")
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    /**
     * Import our Student Repository Data Access Layer, so we can Query the database
     */
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    /**
     * Service access layer connects to Data access layer to retrieve students.
     * This represents the connection between the two layers.
     * @param studentRepository access to student repository
     * @param userRepository access to user repository
     */
    @Autowired // needed to automatically connect to StudentRepository and UserRepository
    public StudentService(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all students
     * @return a list of students
     */
    public List<Student> getStudents() {
        List<Student> std_list = studentRepository.findAll();
        log.info("RequestedMethod GET: Student => [ {} ]",std_list);

        return std_list;
    }


    /**
     * Search and find students by name
     * @param name the name of the student
     * @return a list of students matching the regex <code>name</code>
     */
    public List<Student> getStudentByName(String name) {
        Optional<User> user = userRepository.findUserByName(name);
        if (!user.isPresent()){
            log.info("User [ {} ] not found.", name);
            throw new IllegalStateException("Request for User: [ "+  name  + " ], returned not found.");
        }
        Optional<Student> students = studentRepository.findStudentById(user.get().getId());
        if (students.isEmpty()) {
            throw new IllegalStateException("Student: " + name + " does not exist.");
        }
        log.info("RequestedMethod GET: Students => [ {} ]",students.toString());

        return List.of(students.orElseThrow(() ->
                new IllegalStateException("Student with name: "+ name + ", not found.")));
    }


    /**
     * Add a student to Student Table
     * @param student
     */
    public List<Serializable> addNewStudent(Student student) {
        String message;
        Optional<Student> studentOptional = studentRepository
                .findStudentById(student.getStudentId());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("Student: " + student.toString() + " exists.");
        }
        studentRepository.save(student);
        log.info(student.toString());
        message = String.format("\nSuccessfully added new Student: [%s]",student);
        log.info(message);
        return List.of(message);
    }
    /**
     * Delete a student from Student Table
     * @param studentId the studentId
     */
    public List<String> deleteStudent(Long studentId){
        boolean exists = studentRepository.existsById(studentId);
        if (!exists){
            throw new IllegalStateException(
                    "Student with Id " + studentId + " does not exist."
            );
        }
        studentRepository.deleteById(studentId);
        String message = "Student: " + studentId + " has been deleted.";
        return List.of(message);
    }
//    /**
//     * Update student name and email by studentId
//     * @param studentId
//     * @param name
//     * @param email
//     */
//    @Transactional // puts the entity in a managed state
//    public List<String> updateStudent(
//        Long studentId,
//        String name,
//        String email
//        ){
//        // find all students with that studentId
//        Student studentById = studentRepository.findById(studentId)
//            .orElseThrow(() -> new IllegalStateException(
//                "\nStudent with id " + studentId + " does not exist.\n"
//            ));
//        Student studentByEmail = studentRepository.findStudentByEmail(email)
//           .orElseThrow(() -> new IllegalStateException(
//                "Student with email: " + email + " does not exist.\n"
//                ));
//        // if the same students are returned then we proceed to update student
//        if (studentByEmail.canEqual(studentById))
//        {
//            // check and set name, if the parameter exists
//            if (name != null &&
//                    name.length() > 0 &&
//                    !Objects.equals(studentById.getUser().getName(), name)) {
//                studentById.getUser().setName(name);
//            }
//            // check and set email, if the parameter exists
//            if (email != null &&
//                    email.length() > 0 &&
//                    !Objects.equals(studentById.getUser().getEmail(), email)) {
//                Optional<Student> studentOptional = studentRepository
//                        .findStudentById(studentId);
//                if (studentOptional.isPresent()) {
//                    throw new IllegalStateException("Email: " + email + " exists.");
//                }
//                studentById.getUser().setEmail(email);
//            }
//        }
//        return List.of(String.format("UPDATE Successful"));
//    }

    /**
     * Find student by id.
     * @param studentId the student id
     * @return List<Student>
     */
    public List<Student> findStudentById(Long studentId) throws IllegalArgumentException {
        Optional<Student> student = studentRepository.findById(studentId);
        return List.of(
                student.orElseThrow(() ->
                        new IllegalStateException("Student with studentId: "+ studentId + ", not found.")));
    }

}
