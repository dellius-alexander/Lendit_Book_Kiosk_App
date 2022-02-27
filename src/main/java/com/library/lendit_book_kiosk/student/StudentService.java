package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import java.io.Serializable;
import java.util.ArrayList;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/////////////////////////////////////////////////////////////////////
/**
 * Service Layer Class that supports our API layer
 */
import org.springframework.transaction.annotation.Transactional;
@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    /**
     * Serivce access layer connects to Data access layer to retrieve students
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
        List<Student> students_list = studentRepository.findAll();
        List<Student> students_found = new ArrayList<Student>();
        boolean found = false;
        for (Student student : students_list) 
        {
            if  (
                Pattern.compile(Pattern.quote(name), 
                Pattern.CASE_INSENSITIVE).matcher(student.getName()).find()
                )
            {
                log.info(student.toString());
                students_found.add(student);
                found = true;
            }
        }
        if (found == false) {
            throw new IllegalStateException("Student: " + name + " does not exist.");
        }
        log.info("RequestedMethod GET: Students => [ {} ]",students_found);
        return students_found;
    }
    /**
     * Add a student to Student Table
     * @param student
     */
    public List<Serializable> addNewStudent(Student student) {
        String message;
        Optional<Student> studentOptional = studentRepository
            .findStudentByEmail(student.getEmail());
        
        if (studentOptional.isPresent()){
            throw new IllegalStateException("Email address " + student.getEmail() + " exists.");
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
    public void updateStudent(  
        Long studentId,
        String name,
        String email
        ){
        // find all students with that studentId
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new IllegalStateException(
                "Student with id " + studentId + " does not exist."
            ));
        // check and set name, if the parameter exists
        if (name != null && 
            name.length() > 0 && 
            !Objects.equals(student.getName(), name)){
                student.setName(name);
        }
        // check and set email, if the parameter exists
        if (email != null &&
            email.length() > 0 &&
            !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("Email: " + email + " exists.");
            }
            student.setEmail(email);
        }
    }
    
}
