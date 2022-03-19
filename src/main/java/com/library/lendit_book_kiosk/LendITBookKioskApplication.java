package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////

import com.library.lendit_book_kiosk.Book.Book;
import com.library.lendit_book_kiosk.Book.BookRepository;
import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Role.UserRole;
import com.library.lendit_book_kiosk.Student.Major;
import com.library.lendit_book_kiosk.Student.Student;
//import com.library.lendit_book_kiosk.Student.StudentRepository;
import com.library.lendit_book_kiosk.User.GENDER;
import com.library.lendit_book_kiosk.User.User;
import com.library.lendit_book_kiosk.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


/////////////////////////////////////////////////////////////////////
@SpringBootApplication
public class LendITBookKioskApplication implements CommandLineRunner
{
	// Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(LendITBookKioskApplication.class);

	private static  UserRepository userRepository;
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
			UserRepository userRepository){
		return args -> {

//			List<User> users =  userRepository.findAll();
			Major CSCI = new Major("CSCI");
			Major BSCS = new Major("BSCS");
			Major MBA = new Major("MBA");

			Role ADMIN = new Role(UserRole.ADMIN, "Administrator");
			Role GUEST = new Role(UserRole.GUEST,"Guest visitor");
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
					"password",
					GENDER.FEMALE,
					LocalDate.of(1989, Month.JANUARY, 6),
					"Student:Senior",
					Set.of(ADMIN,SUPERUSER),
					Set.of(janeDoe)
			);

			User john = new User(
					"John Doe",
					"john@gmail.com",
					"password",
					GENDER.MALE,
					LocalDate.of(1989, Month.JANUARY, 5),
					"Faculty:Professor",
					Set.of(ADMIN,SUPERUSER),
					Set.of(johnDoe)
			);

			User bob = new User(
					"Bob Doe",
					"bob@gmail.com",
					"password",
					GENDER.MALE,
					LocalDate.of(1979, Month.JANUARY, 12),
					"Student:Junior",
					Set.of(GUEST),
					Set.of(bobDoe)
			);

			log.info("\nUSERS: \n{} \n{} \n{}\n",jane,john,bob);
//			userRepository.save(bob);
//			userRepository.saveAll(
//					List.of(jane, john, bob)
//			);

//			init_books(bookRepository);
		};
	}
/////////////////////////////////////////////////////////////////////
//
//	/**
//	 * Initialize demo user and student objects
//	 * @param userRepository DB repository
//	 * @param users a list of current DB users
//	 * @throws JSONException
//	 */
//	@Bean
//	@Autowired
//	private static List<User> init_users(UserRepository userRepository, List<User> users) throws JSONException{
//
//            Student janeDoe = new Student (
//                    true,
//                    Set.of(new Major("CSCI"), new Major("MBA") )
//            );
//
//            Student johnDoe = new Student (
//                    true,
//                    Set.of(new Major("BSCS"), new Major("MBA"))
//            );
//
//            Student bobDoe = new Student (
//                    true,
//                    Set.of(new Major("BSA"), new Major("MBA"))
//            );
//
//            User jane = new User(
//                    "Jane Doe",
//                    "jane@gmail.com",
//                    "password",
//                    GENDER.FEMALE,
//                    LocalDate.of(1989, Month.JANUARY, 6),
//                    "Student:Senior",
//                    Set.of(new Role(UserRole.ADMIN),new Role(UserRole.SUPERUSER)),
//                    Set.of(janeDoe)
//            );
//
//            User john = new User(
//                    "John Doe",
//                    "john@gmail.com",
//                    "password",
//                    GENDER.MALE,
//                    LocalDate.of(1989, Month.JANUARY, 5),
//                    "Faculty:Professor",
//                    Set.of(new Role(UserRole.ADMIN),new Role(UserRole.SUPERUSER)),
//                    Set.of(johnDoe)
//            );
//
//            User bob = new User(
//                    "Bob Doe",
//                    "bob@gmail.com",
//                    "password",
//                    GENDER.MALE,
//                    LocalDate.of(1979, Month.JANUARY, 12),
//                    "Student:Junior",
//                    Set.of(new Role(UserRole.GUEST)),
//                    Set.of(bobDoe)
//            );
//		log.info("USERS SIZE: {}",users.size());
//		return List.of(jane,john,bob);
//
//}

//	/**
//	 * Initialize demo book objects
//	 * @throws JSONException
//	 */
//	@Autowired
//	private static void init_books(BookRepository bookRepository) throws JSONException {
//	JSONObject json = new JSONObject(
//			"{\n" +
//					"    \"isbn\": \"9781856136631\",\n" +
//					"    \"title\": \"The Harry Potter trilogy\",\n" +
//					"    \"series\": \"Harry Potter #1-3\",\n" +
//					"    \"authors\": \"J.K. Rowling\",\n" +
//					"    \"description\": \"This box set collects hard cover editions Harry Potter and the Philosopher's Stone, Harry Potter and the Chamber of Secrets, and Harry Potter and the Prisoner of Azkaban in a slip case.\",\n" +
//					"    \"language\": \"English\",\n" +
//					"    \"rating\": 4.66,\n" +
//					"    \"genres\": \"['Fantasy', 'Fiction', 'Young Adult', 'Childrens', 'Magic', 'Adventure', 'Classics', 'Middle Grade', 'Novels', 'France']\",\n" +
//					"    \"num_of_pages\": 900,\n" +
//					"    \"publisher\": \"Ted Smart/Bloomsbury\",\n" +
//					"    \"publication_date\": [\n" +
//					"      1905,\n" +
//					"      6,\n" +
//					"      21\n" +
//					"    ],\n" +
//					"    \"cover_img\": \"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1330972392l/2337379.jpg\"\n" +
//					"  }"
//	);
//	Book b1 = new Book(
//			json.getString("isbn"),
//			json.getString("title"),
//			json.getString("series"),
//			json.getString("authors"),
//			json.getString("description"),
//			json.getString("language"),
//			json.getDouble("rating"),
//			json.getString("genres"),
//			json.getLong("num_of_pages"),
//			json.getString("publisher"),
//			LocalDate.of(
//					json.getJSONArray("publication_date").getInt(0),
//					json.getJSONArray("publication_date").getInt(1),
//					json.getJSONArray("publication_date").getInt(2)),
//			json.getString("cover_img")
//	);
//	JSONObject json0 = new JSONObject(
//			"{\n" +
//					"    \"isbn\": \"9780141314815\",\n" +
//					"    \"title\": \"The Magical Worlds Of Harry Potter: A Treasury Of Myths, Legends And Fascinating Facts\",\n" +
//					"    \"series\": null,\n" +
//					"    \"authors\": \"David Colbert (Goodreads Author)\",\n" +
//					"    \"description\": \"J.K. Rowling fills her books with references to history, myths, legends and literature. The Magical Worlds of Harry Potter reveals the stories behind the stories.All the questions you ever wanted to ask about the fantastical world in which Harry lives are\",\n" +
//					"    \"language\": \"English\",\n" +
//					"    \"rating\": 3.99,\n" +
//					"    \"genres\": \"['Fantasy', 'Young Adult', 'Fiction', 'Reference', 'Mythology', 'Magic', 'Childrens', 'Books About Books', 'Fandom', 'Witches']\",\n" +
//					"    \"num_of_pages\": 224,\n" +
//					"    \"publisher\": \"Penguin Books Ltd\",\n" +
//					"    \"publication_date\": [\n" +
//					"      1905,\n" +
//					"      6,\n" +
//					"      23\n" +
//					"    ],\n" +
//					"    \"cover_img\": \"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1181390041l/1145438._SX318_.jpg\"\n" +
//					"  }"
//	);
//	Book b2 = new Book(
//			json0.getString("isbn"),
//			json0.getString("title"),
//			json0.getString("series"),
//			json0.getString("authors"),
//			json0.getString("description"),
//			json0.getString("language"),
//			json0.getDouble("rating"),
//			json0.getString("genres"),
//			json0.getLong("num_of_pages"),
//			json0.getString("publisher"),
//			LocalDate.of(
//					json0.getJSONArray("publication_date").getInt(0),
//					json0.getJSONArray("publication_date").getInt(1),
//					json0.getJSONArray("publication_date").getInt(2)),
//			json0.getString("cover_img")
//	);
//	JSONObject json1 = new JSONObject(
//			"{\n" +
//					"    \"isbn\": \"9780439064866\",\n" +
//					"    \"title\": \"Harry Potter and the Chamber of Secrets\",\n" +
//					"    \"series\": \"Harry Potter #2\",\n" +
//					"    \"authors\": \"J.K. Rowling, Mary GrandPré (Illustrator)\",\n" +
//					"    \"description\": \"Ever since Harry Potter had come home for the summer, the Dursleys had been so mean and hideous that all Harry wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he’s packing his bags, Harry receives a warning from a st\",\n" +
//					"    \"language\": \"English\",\n" +
//					"    \"rating\": 4.43,\n" +
//					"    \"genres\": \"['Fantasy', 'Young Adult', 'Fiction', 'Magic', 'Childrens', 'Middle Grade', 'Adventure', 'Audiobook', 'Classics', 'Science Fiction Fantasy']\",\n" +
//					"    \"num_of_pages\": 341,\n" +
//					"    \"publisher\": \"Arthur A. Levine Books / Scholastic Inc.\",\n" +
//					"    \"publication_date\": [\n" +
//					"      1999,\n" +
//					"      6,\n" +
//					"      2\n" +
//					"    ],\n" +
//					"    \"cover_img\": \"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1474169725l/15881._SY475_.jpg\"\n" +
//					"  }"
//	);
//	Book b3 = new Book(
//			json1.getString("isbn"),
//			json1.getString("title"),
//			json1.getString("series"),
//			json1.getString("authors"),
//			json1.getString("description"),
//			json1.getString("language"),
//			json1.getDouble("rating"),
//			json1.getString("genres"),
//			json1.getLong("num_of_pages"),
//			json1.getString("publisher"),
//			LocalDate.of(
//					json1.getJSONArray("publication_date").getInt(0),
//					json1.getJSONArray("publication_date").getInt(1),
//					json1.getJSONArray("publication_date").getInt(2)),
//			json1.getString("cover_img")
//	);
//	log.info("\nBook1: {} \nBook2: {}\nBook3: {}\n", b1, b2, b3);
//
//	bookRepository.saveAll(
//			List.of(b1,b2,b3)
//	);
//
//}	/////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////
}


