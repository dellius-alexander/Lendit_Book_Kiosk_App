package com.library.lendit_book_kiosk.Student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
     * Get a list of All <code>Students</code>
     * @return List of <code>Students</code>
     */
    @RequestMapping(
            value = {"/findAll","/findall"},
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Student>> getStudents() throws IllegalArgumentException {
        List<Student> list_students = studentService.getStudents();
        log.info("RequestedMethod GET: Student => {}", list_students);
        return ResponseEntity.ok().body(list_students);
    }
    
//    /**
//     * Get a student info
//     * @param name the name of the student
//     * @return the student info
//     */
//    @RequestMapping(value = {"/findByName"})
//    public ResponseEntity<List<Student>> getStudentByQueryName(
//        @RequestParam String name) {
//        List<Student> list_students = studentService.getStudent(name.trim());
//        log.info("RequestedMethod GET: Student => {}", list_students);
//        return ResponseEntity.ok().body(list_students);
//    }
//    /**
//     * Get a student info
//     * @param name the name of the student
//     * @return the student info
//     */
//    @GetMapping(path = "/findByName/{name}")
//    public ResponseEntity<List<Student>> getStudentByName(
//        @PathVariable("name") String name) {
//        List<Student> list_students = studentService.getStudent(name.trim());
//        log.info("RequestedMethod GET: Student => {}", list_students);
//        return ResponseEntity.ok().body(list_students);
//    }
    /**
     * Find student by id
     * @param studentId the student id
     * @return List of <code>Students</code>
     */    
    @RequestMapping(
            value = {"/findById/{student_Id}"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getByQueryId(
        @RequestParam Long studentId) {
        List<Student> student = studentService.findStudentById(studentId);
        log.info("RequestedMethod GET: StudentId => {}", student);
        return ResponseEntity.ok().body(student);
    }
    /**
     * Find student by id
     * @param studentId the student id
     * @return List of <code>Students</code>
     */
    @RequestMapping(
            value = {"/findById/id={student_Id}"},
            method = RequestMethod.GET )
    public ResponseEntity<List<Student>> getStudentById(
        @PathVariable("student_Id") Long studentId) {
        List<Student> students = studentService.findStudentById(studentId);
        log.info("RequestedMethod GET: Student_Id => {}", students.toString());
        return ResponseEntity.ok().body(students);
    }
    /**
     * Registers a new student
     * @param student a student object
     */
    @RequestMapping(
            value = {"/register/{student}","/register/"},
            method = RequestMethod.POST,
            consumes = {"application/json"})
    public ResponseEntity<List<Serializable>>  registerNewStudent(
        @RequestBody Student student){
            if(student == null || student.getClass().isInstance(new Student())){
                throw new NullArgumentException("Empty payload received.");
            }
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/student/register").toUriString());
            log.info("RequestedMethod POST: Student => {}", student);
        return ResponseEntity.created(uri).body(studentService.addNewStudent(student));

    }
    /**
     * Delete a student
     * @param studentId the student id
     */
    @DeleteMapping(
            value = {"/delete/{studentId}"},
            path = "/delete/{studentId}",
            consumes = "text/plain")
    public ResponseEntity<List<String>>  deleteStudent(
        @PathVariable("studentId") Long studentId){
            log.info("Delete RequestedMethod POST: StudentId => {}", studentId);
        return  ResponseEntity.ok().body(studentService.deleteStudent(studentId));
    }
//    /**
//     * Update student name and email by studentId
//     * @param studentId
//     * @param name
//     * @param email
//     */
//    @PutMapping(path = "update/{studentId}")
//    public ResponseEntity<List<String>> updateStudent(
//        @PathVariable("studentId") Long studentId,
//        @RequestParam(required = false) String name,
//        @RequestParam(required = false) String email){
//            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/student/update/{studentId}").toUriString());
//            log.info("RequestedMethod POST: StudentId => {}, Name => {}, Email => {}", studentId, name, email);
//            return ResponseEntity.created(uri).body(studentService.updateStudent(studentId, name, email));
//        }

}


/////////////////////////////////////////////////////////////////////