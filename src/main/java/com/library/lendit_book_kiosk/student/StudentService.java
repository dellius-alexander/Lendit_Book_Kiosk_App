package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/////////////////////////////////////////////////////////////////////
/**
 * Service Layer Class that supports our API layer
 */
@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    /**
     * @param name the name of the student
     * @return the student info
     */
    public List<Student> getStudents(String name) {
        String email = name.replaceAll("\\s+","");
        Student student = new Student (
                            1L,
                            name,
                            email+"@gmail.com",
                            LocalDate.of(1989, Month.JANUARY, 6),
                            35,
                            "CIS");
        log.info(student.toString());
        return List.of(student);
    }   
}
