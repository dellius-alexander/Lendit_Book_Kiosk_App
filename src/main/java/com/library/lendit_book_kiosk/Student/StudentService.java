package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.*;
import java.util.regex.Pattern;

import java.io.Serializable;

// LOGGING CLASSES
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/////////////////////////////////////////////////////////////////////
/**
 * Service Layer Class that supports our API layer
 */
@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    /**
     * Import our Student Repository Data Access Layer, so we can Query the database
     */
    private final StudentRepository studentRepository;

    /**
     * Service access layer connects to Data access layer to retrieve students.
     * This represents the connection between the two layers.
     * @param studentRepository
     */
    @Autowired // needed to automatically connect to StudentRepository
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    public List<Student> getStudents(String name) {
        List<Student> students = studentRepository.findStudentByName(name);
        if (students.isEmpty()) {
            throw new IllegalStateException("Student: " + name + " does not exist.");
        }
        log.info("RequestedMethod GET: Students => [ {} ]",students.toString());
        return students;
    }
    /**
     * Add a student to Student Table
     * @param student
     */
    public List<Serializable> addNewStudent(Student student) {
        String message;
        Optional<Student> studentOptional = studentRepository
                .findStudentById(student.getId());
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
    /**
     * Update student name and email by studentId
     * @param studentId
     * @param name
     * @param email
     */
    @Transactional // puts the entity in a managed state
    public List<String> updateStudent(  
        Long studentId,
        String name,
        String email
        ){
        // find all students with that studentId
        Student studentById = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalStateException(
                "\nStudent with id " + studentId + " does not exist.\n"
            ));
        Student studentByEmail = studentRepository.findStudentByEmail(email)
           .orElseThrow(() -> new IllegalStateException(
                "Student with email: " + email + " does not exist.\n"
                ));
        // if the same students are returned then we proceed to update student
        if (studentByEmail.canEqual(studentById))
        {
            // check and set name, if the parameter exists
            if (name != null &&
                    name.length() > 0 &&
                    !Objects.equals(studentById.getName(), name)) {
                studentById.setName(name);
            }
            // check and set email, if the parameter exists
            if (email != null &&
                    email.length() > 0 &&
                    !Objects.equals(studentById.getEmail(), email)) {
                Optional<Student> studentOptional = studentRepository
                        .findStudentById(studentId);
                if (studentOptional.isPresent()) {
                    throw new IllegalStateException("Email: " + email + " exists.");
                }
                studentById.setEmail(email);
            }
        }
        return List.of(String.format("UPDATE Successful"));
    }

	public List<Student> findStudentById(Long studentId) {
        Optional<Student> student = studentRepository.findStudentById(studentId);
		return List.of(
                student
                        .orElseThrow(() ->
                                new IllegalStateException("Student with studentId: "+ studentId + ", not found.")));
	}
    
}
