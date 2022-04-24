package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////
import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Role.RoleRepository;
import com.library.lendit_book_kiosk.Role.RoleService;
import com.library.lendit_book_kiosk.Role.UserRole;
import com.library.lendit_book_kiosk.Security.Custom.Secret;
import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.Student.Student;
import com.library.lendit_book_kiosk.Tools.CSVParser;
import com.library.lendit_book_kiosk.Tools.FileParser;
import com.library.lendit_book_kiosk.User.GENDER;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserRepository;
import com.library.lendit_book_kiosk.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
			RoleService roleService){
		return args -> {

			User user =  userService.getByEmail("adyos0@webs.com");

			if (user == null){

//				List<User> newUser = getUsers(userService,roleService);

				CSVParser csv = new CSVParser(new FileParser(new File("target/classes/users.csv")));
				// save all new users
				userService.saveAll(
						csv.getStudentsInfo()
				);
				log.info("\nSAVING NEW USERS TO DB: {}", csv.getStudentsInfo());
			} else{
				log.info("\nDB ALREADY HAS USERS: {}\n", user.toString());
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
				GENDER.FEMALE,
				LocalDate.of(1989, Month.JANUARY, 6),
				"Student:Senior",
				Set.of(student_role,admin_role),
				Set.of(janeDoe)
		);

		User john = new User(
				"John Doe",
				"john@gmail.com",
				new Secret("password"),
				GENDER.MALE,
				LocalDate.of(1989, Month.JANUARY, 5),
				"Student:Senior",
				Set.of(student_role,admin_role),
				Set.of(johnDoe)
		);

		User bob = new User(
				"Bob Doe",
				"bob@gmail.com",
				new Secret("password"),
				GENDER.MALE,
				LocalDate.of(1979, Month.JANUARY, 12),
				"Visitor:GSU",
				Set.of(GUEST),
				Set.of(bobDoe)
		);
		return List.of(bob, jane, john);

	}
}


