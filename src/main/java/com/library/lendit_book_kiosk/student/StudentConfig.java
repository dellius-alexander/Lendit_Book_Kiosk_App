package com.library.lendit_book_kiosk.student;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/////////////////////////////////////////////////////////////////////
@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student janeDoe = new Student (
                        "Jane Doe",
                        "jane.doe@gmail.com",
                        LocalDate.of(1989, Month.JANUARY, 6),
                        "CIS");
        
            Student johnDoe = new Student (
                            "John Doe",
                            "john.doe@gmail.com",
                            LocalDate.of(1972, Month.FEBRUARY, 15),
                            "BIT");
            
            Student bobDoe = new Student (
                            "Bob Doe",
                            "bob.doe@gmail.com",
                            LocalDate.of(1979, Month.JANUARY, 12),
                            "MIT");
            // save students to the database
            repository.saveAll(
                List.of(janeDoe, johnDoe, bobDoe)
            );
        };

        
        
    }
    
}
/////////////////////////////////////////////////////////////////////