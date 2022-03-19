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
import java.util.Set;

import org.apache.commons.lang.NullArgumentException;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
/**
 * Api Layer <br/>
 * <code style="color:orange;font-style:bold;">/student</code>
 */
@RestController
@RequestMapping(value = "/student")
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
    public ResponseEntity<List<Student>> getStudents()  {
        List<Student> list_students = studentService.getStudents();
        log.info("RequestedMethod GET: Student => {}", list_students);
        return ResponseEntity.ok().body(list_students);
    }
    /**
     * Find student by <code>student_Id</code>
     * @param studentId the student Id
     * @return List of <code>Students</code>
     */
    @RequestMapping(
            value = {"/findById"},
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Student>> getByQueryId(
            @RequestParam Long studentId) {
        List<Student> student = studentService.findStudentById(studentId);
        log.info("RequestedMethod GET: StudentId => {}", student);
        return ResponseEntity.ok().body(student);
    }
    /**
     * Find student by <code>student_Id</code>
     * @param studentId the student Id
     * @return List of <code>Students</code>
     */
    @RequestMapping(
            value = {"/findById/Id={student_Id}"},
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Student>> getStudentById(
            @PathVariable("student_Id") Long studentId) {
        List<Student> students = studentService.findStudentById(studentId);
        log.info("RequestedMethod GET: Student_Id => {}", students.toString());
        return ResponseEntity.ok().body(students);
    }
    /**
     * Registers a new <code>Student</code>
     * @param student a <code>Student</code> object
     * @return response status/message & status [code]
     */
    @RequestMapping(
            value = {"/register/{student}"},
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<List<Serializable>>  registerNewStudent(
            @PathVariable("student") @RequestBody Student student
    ){
        if(student == null){
            throw new NullArgumentException("Empty payload received.");
        }
        if (!(student instanceof Student)){
            throw new NullArgumentException("Not an instance of Student.class");
        }
        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/student/register")
                        .toUriString()
        );
        log.info("RequestedMethod POST: Student => {}", student);
        return ResponseEntity.created(uri).body(studentService.addNewStudent(student));
    }
    /**
     * Delete a <code>Student</code> by <code>student_Id</code>
     * @param studentId the student id
     * @return response message
     */
    @DeleteMapping(
            value = {"/delete/{studentId}"},
            produces = {"application/json"})
    public ResponseEntity<List<String>>  deleteStudent(
            @PathVariable("studentId") Long studentId){
        log.info("Delete RequestedMethod POST: StudentId => {}", studentId);
        return  ResponseEntity.ok().body(studentService.deleteStudent(studentId));
    }

}


/////////////////////////////////////////////////////////////////////