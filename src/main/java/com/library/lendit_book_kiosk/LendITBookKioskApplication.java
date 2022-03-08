package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////
import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserRepository;
import com.library.lendit_book_kiosk.Student.Student;
import com.library.lendit_book_kiosk.Student.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

/////////////////////////////////////////////////////////////////////
@SpringBootApplication
public class LendITBookKioskApplication implements CommandLineRunner
{
	// Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.
		getLogger(LendITBookKioskApplication.class);
		
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////	MAIN DRIVER 	////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		SpringApplication.run(LendITBookKioskApplication.class, args);
		log.info("<----------------| Your Spring Application has started......! |---------------->");

	}
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	/**
	 * Run the commandline runner
	 */
    @Override
	public void run(String... args) throws Exception {
		log.info("EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i) {
            log.info("args[{}]: {}", i, args[i]);
        }
	}
	////////////////////////////////////////////////////////////
	@Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository,
            UserRepository userRepository
    ){
        return args -> {


            Student janeDoe = new Student (
                    true,
                    Set.of(new Major("CSCI"), new Major("MBA") )
            );

            Student johnDoe = new Student (
                    true,
                    Set.of(new Major("BSCS"), new Major("MBA"))
            );

            Student bobDoe = new Student (
                    true,
                    Set.of(new Major("BSA"), new Major("MBA"))
            );

            User jane = new User(
                    "Jane Doe",
                    "jane@gmail.com",
                    "password",
                    "FEMALE",
                    LocalDate.of(1989, Month.JANUARY, 6),
                    "Student:Senior",
                    Set.of(new Role("ADMIN"),new Role("SUPERUSER")),
                    Set.of(janeDoe)
            );

            User john = new User(
                    "John Doe",
                    "john@gmail.com",
                    "password",
                    "Male",
                    LocalDate.of(1989, Month.JANUARY, 5),
                    "Faculty:Professor",
                    Set.of(new Role("ADMIN"),new Role("SUPERUSER")),
                    Set.of(johnDoe)
            );

            User bob = new User(
                    "John Doe",
                    "john@gmail.com",
                    "password",
                    "Male",
                    LocalDate.of(1979, Month.JANUARY, 12),
                    "Student:Junior",
                    Set.of(new Role("FACULTY")),
                    Set.of(bobDoe)
            );
            //             save users to the database
            userRepository.saveAll(
                    List.of(jane,john,bob)
            );


        };
    }
}
/////////////////////////////////////////////////////////////////////


