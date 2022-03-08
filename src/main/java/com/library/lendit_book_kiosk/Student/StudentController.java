package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
/**
 * Api Layer <br/>
 * <code style="color:red;font-style:bold;">/api/v1/student</code>
 */
@RestController
@RequestMapping(value = "/api/v1/student")
public class StudentController implements Serializable {

    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    
    // instance of StudentService
    private final StudentService studentService;

    /**
     * Bind StudentServices to StudentController as part of our business logic
     * @param studentService an instance of StudentService business logic
     */
    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    /**
     * Get a student info
     * @return the student info
     */    
    @GetMapping(path = "/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getStudents() {
        List<Student> list_students = studentService.getStudents();
        log.info("RequestedMethod GET: Student => {}", list_students);
        return list_students;
    }
    
    /**
     * Get a student info
     * @param name the name of the student
     * @return the student info
     */    
    @RequestMapping(value = {"/findByName"})
    public ResponseEntity<List<Student>> getStudentByQueryName(
        @RequestParam String name) {
        List<Student> list_students = studentService.getStudents(name.trim());
        log.info("RequestedMethod GET: Student => {}", list_students);
        return ResponseEntity.ok().body(list_students);
    }
    /**
     * Get a student info
     * @param name the name of the student
     * @return the student info
     */    
    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<List<Student>> getStudentByName(
        @PathVariable("name") String name) {
        List<Student> list_students = studentService.getStudents(name.trim());
        log.info("RequestedMethod GET: Student => {}", list_students);
        return ResponseEntity.ok().body(list_students);
    }
    /**
     * Get a student info
     * @param studentId the name of the student
     * @return the student info
     */    
    @RequestMapping(value = {"/findById"})
    public ResponseEntity<List<Student>> getByQueryId(
        @RequestParam  Long studentId) {
        List<Student> student = studentService.findStudentById(studentId);
        log.info("RequestedMethod GET: StudentId => {}", student);
        return ResponseEntity.ok().body(student);
    }
    /**
     * Get a student info
     * @param studentId the name of the student
     * @return the student info
     */    
    @GetMapping(path = "/findById/{studentId}")
    public ResponseEntity<List<Student>> getById(
        @PathVariable("studentId") Long studentId) {
        List<Student> student = studentService.findStudentById(studentId);
        log.info("RequestedMethod GET: StudentId => {}", student);
        return ResponseEntity.ok().body(student);
    }
    /**
     * Registers a new student
     * @param student
     */
    @PostMapping(path = "register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Serializable>>  registerNewStudent(
        @RequestBody Student student){
            if(student == null){
                throw new NullArgumentException("Empty payload received.");
            }
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/student/register").toUriString());
            log.info("RequestedMethod POST: Student => {}", student);
        return ResponseEntity.created(uri).body(studentService.addNewStudent(student));

    }
    /**
     * Delete a student
     * @param studentId
     */
    @DeleteMapping(path = "delete/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>>  deleteStudent(
        @PathVariable("studentId") Long studentId){
            log.info("Delete RequestedMethod POST: StudentId => {}", studentId);
        return  ResponseEntity.ok().body(studentService.deleteStudent(studentId));
    }
    /**
     * Update student name and email by studentId
     * @param studentId
     * @param name
     * @param email
     */
    @PutMapping(path = "update/{studentId}")
    public ResponseEntity<List<String>> updateStudent(
        @PathVariable("studentId") Long studentId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email){
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/student/update/{studentId}").toUriString());
            log.info("RequestedMethod POST: StudentId => {}, Name => {}, Email => {}", studentId, name, email);
            return ResponseEntity.created(uri).body(studentService.updateStudent(studentId, name, email));
        }

}

/////////////////////////////////////////////////////////////////////