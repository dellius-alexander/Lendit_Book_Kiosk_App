package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.util.List;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/////////////////////////////////////////////////////////////////////
/**
 * Service Layer Class that supports our API layer
 */
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
     * @param name the name of the student
     * @return the student info
     */
    public List<Student> getStudents() {
        List<Student> std_list = studentRepository.findAll();
        log.info("[ {} ]",std_list);
        return std_list;
    }   
}
