package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

// import com.fasterxml.jackson.databind.JsonSerializer;
// import com.fasterxml.jackson.databind.annotation.JsonSerialize;


// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
@RestController
@RequestMapping(path = "/api/v1/student/", method = RequestMethod.GET)
public class StudentController {

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

}
/////////////////////////////////////////////////////////////////////