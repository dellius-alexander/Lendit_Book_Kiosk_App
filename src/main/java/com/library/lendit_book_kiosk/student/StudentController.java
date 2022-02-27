package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.List;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
/**
 * Api Layer <br/>
 * <code style="color:red;font-style:bold;">/api/v1/student</code>
 */
@RestController
@RequestMapping(value = "api/v1/student")
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
     * @param name the name of the student
     * @return the student info
     */    
    @GetMapping
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
    @GetMapping(path = "find/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Student> getStudents(
        @PathVariable("name") String name) {
        List<Student> list_students = studentService.getStudents(name);
        log.info("RequestedMethod GET: Student => {}", list_students);
        return list_students;
    }
    /**
     * Registers a new student
     * @param student
     */
    @PostMapping(path = "new")
    @ResponseStatus(HttpStatus.OK)
    public List<Serializable> registerNewStudent(
        @RequestBody Student student){
        log.info("RequestedMethod POST: Student => {}", student);
        return studentService.addNewStudent(student);
    }
    /**
     * Delete a student
     * @param studentId
     */
    @DeleteMapping(path = "delete/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> deleteStudent(
        @PathVariable("studentId") Long studentId){
        return studentService.deleteStudent(studentId);
    }
    /**
     * Update student name and email by studentId
     * @param studentId
     * @param name
     * @param email
     */
    @PutMapping(path = "update/{studentId}")
    public void updateStudent(
        @PathVariable("studentId") Long studentId,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email){
            studentService.updateStudent(studentId, name, email);
        }

}

/////////////////////////////////////////////////////////////////////