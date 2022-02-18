package com.library.lendit_book_kiosk;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.lendit_book_kiosk.student.Student;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
@RestController
class HelloController {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(HelloController.class);
	@GetMapping("/")
	public String index() {
        String index = ("<h1 style='margin:auto; width:80%; padding:15px;' >Welcome to the LendIT Book Kiosk Application! </h1></br><p style='font-size:1.5rem; margin:auto; width:80%; padding:15px;' >You have successfully setup your development environment...</p>");
        log.info(index);
		return index;
	}
}
/////////////////////////////////////////////////////////////////////
/**
 * A sample request controller to get student info
 */
@RestController
class GreetingsController {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(GreetingsController.class);
    /**
     * @param name the name of the student
     * @return the student info
     */
    @JsonSerialize(using=JsonSerializer.class)
    @RequestMapping(value = "/api/v1/student/{name}", method = RequestMethod.GET)

    @ResponseStatus(HttpStatus.OK)
    public List<Student> getStudent(@PathVariable String name) {
        Student temp = new Student (
                            1L,
                            name,
                            "janedoe@gmail.com",
                            LocalDate.of(1989, Month.JANUARY, 6),
                            35,
                            "CIS");
        log.info(temp.toString());
        return List.of(temp);
    }
}
/////////////////////////////////////////////////////////////////////
