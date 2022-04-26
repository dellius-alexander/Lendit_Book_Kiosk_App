package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////
import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookService;
import com.library.lendit_book_kiosk.User.Role.Role;
import com.library.lendit_book_kiosk.User.Role.RoleService;
import com.library.lendit_book_kiosk.User.Role.UserRole;
import com.library.lendit_book_kiosk.Security.Secret.Secret;
import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.Student.Student;
import com.library.lendit_book_kiosk.Utility.CSVParser;
import com.library.lendit_book_kiosk.Utility.FileParser;
import com.library.lendit_book_kiosk.User.Gender;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

/////////////////////////////////////////////////////////////////////
@SpringBootApplication
public class LendITBookKioskApplication implements CommandLineRunner
{
	// Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(LendITBookKioskApplication.class);


	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////	MAIN DRIVER 	////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	public static void main(String[] args) throws JSONException {
		SpringApplication.run(LendITBookKioskApplication.class, args);
		log.info("|<----------------| Your Spring Application has started......! |---------------->|");

	}
    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	/**
	 * Run the commandline runner
	 */
    @Override
	public void run(String... args) throws Exception
	{
		log.info("EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i)
		{
            log.info("args[{}]: {}", i, args[i]);
        }
	}
	////////////////////////////////////////////////////////////
	@Bean
	@Autowired
	CommandLineRunner commandLineRunner(
			UserService userService,
			RoleService roleService,
			BookService bookService){
		return args -> {

			User user = userService.getByEmail("adyos0@webs.com");

			// parse user/student csv file
			if (user == null){

				CSVParser csvUsers = new CSVParser(new FileParser(new File("target/classes/users.csv")));
				// save all new users
				userService.saveAll(
						csvUsers.getUsersFromCsvFile()
				);
				log.info("\nSAVING NEW USERS TO DB: {}", csvUsers.getUsersFromCsvFile());
			} else{
				log.info("\nDB ALREADY HAS USERS: {}\n", user.toString());
			}
			// parse books csv file
			List<Book> books = bookService.getBooksByTitle("potter");
			if(books.size() == 0){
				CSVParser csvBooks = new CSVParser(new FileParser(new File("target/classes/Lendit_Book_Kiosk_book.csv")));
				// save all books
				bookService.saveAll(
						csvBooks.getBooksFromCsvFile()
				);
				log.info("Books saved: {}", books);
			}else {
				log.info("\nDB ALREADY POPULATED WITH BOOKS.\n");
			}
		};
	}

	List<User> getUsers(UserService userService,
						RoleService roleService){

		Role student_role = roleService.findRoleByName("STUDENT").orElseThrow(
				() -> new IllegalStateException("Role does not exist......")
		);
		Role admin_role = roleService.findRoleByName("ADMIN").orElseThrow(
				() -> new IllegalStateException("Role does not exist......")
		);
		Role superuser_role = roleService.findRoleByName("SUPERUSER").orElseThrow(
				() -> new IllegalStateException("Role does not exist......")
		);
		Major CSCI = new Major("CSCI");
		Major BSCS = new Major("BSCS");
		Major MBA = new Major("MBA");

		Role USER = new Role(UserRole.USER, "User");
		Role ADMIN = new Role(UserRole.ADMIN, "Administrator");
		Role GUEST = new Role(UserRole.GUEST,"Guest visitor");
		Role STUDENT = new Role(UserRole.STUDENT, "Student");
		Role SUPERUSER = new Role(UserRole.SUPERUSER,"Super Administrator");

		Student janeDoe = new Student (
				true,
				Set.of(CSCI, MBA )
		);

		Student johnDoe = new Student (
				true,
				Set.of(BSCS, MBA)
		);

		Student bobDoe = new Student (
				true,
				Set.of(MBA, BSCS)
		);

		User jane = new User(
				"Jane Doe",
				"jane@gmail.com",
				new Secret("password"),
				Gender.FEMALE,
				LocalDate.of(1989, Month.JANUARY, 6),
				"Student:Senior",
				Set.of(student_role,admin_role),
				Set.of(janeDoe)
		);

		User john = new User(
				"John Doe",
				"john@gmail.com",
				new Secret("password"),
				Gender.MALE,
				LocalDate.of(1989, Month.JANUARY, 5),
				"Student:Senior",
				Set.of(student_role,admin_role),
				Set.of(johnDoe)
		);

		User bob = new User(
				"Bob Doe",
				"bob@gmail.com",
				new Secret("password"),
				Gender.MALE,
				LocalDate.of(1979, Month.JANUARY, 12),
				"Visitor:GSU",
				Set.of(GUEST),
				Set.of(bobDoe)
		);
		return List.of(bob, jane, john);

	}
}


