package com.library.requests;
/////////////////////////////////////////////////////////////////////
// Import Dependencies

import com.fasterxml.jackson.core.JsonParseException;
import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;
import com.library.lendit_book_kiosk.LendITBookKioskApplication;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Import com.library.lendit_book_kiosk.* 
// import com.library.lendit_book_kiosk.Role.Role;
// import com.library.lendit_book_kiosk.User.User;
// import com.library.lendit_book_kiosk.Role.Role;
import com.library.lendit_book_kiosk.Tools.LoadFromFile;
/////////////////////////////////////////////////////////////////////
@RunWith(OrderedTestRunner.class)
@SpringBootTest(classes = LendITBookKioskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class GreetingsControllerTest {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(GreetingsControllerTest.class);

    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Rule
    public TestWatcher watchman = TestWatchman.watchman;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(GreetingsControllerTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(GreetingsControllerTest.class);
    }

    /**
     * It tests response to be "Hello Spring!"
     * @throws Exception
     */
    @Test
    @Order(1)
    public void greetSpring() throws Exception {
        String expected = mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        LoadFromFile  actual = new LoadFromFile("src/main/resources/templates/index.html");
        log.info("\nExpected => [{}]\n Actual => [{}]\n", expected,actual);
        Assert.assertEquals(expected, actual.toString());
    }
    /**
     * Test retrieving a list of students from the database.
     * @throws Exception
     */
    @Test
    @Order(2)
    public void getListOfStudents() throws JsonParseException, Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

//            // Define your users
//            Student janeDoe = new Student (
//                "Jane Doe",
//                "jane.doe@gmail.com",
//                LocalDate.of(1989, Month.JANUARY, 6),
//                "CIS");
//            Student johnDoe = new Student (
//                "John Doe",
//                "john.doe@gmail.com",
//                LocalDate.of(1972, Month.FEBRUARY, 15),
//                "BIT");
//
//            Student bobDoe = new Student (
//                "Bob Doe",
//                "bob.doe@gmail.com",
//                LocalDate.of(1979, Month.JANUARY, 12),
//                "MIT");
            // User jane = new User (
            //     "Jane Doe",
            //     "jane.doe@gmail.com",
            //     "123456",
            //     List.of(new Role("ADMIN"),new Role("SUPERUSER")),
            //     janeDoe
            // );
            // User john = new User (
            //     "John Doe",
            //     "john.doe@gmail.com",
            //     "123456",
            //     List.of(new Role("ADMIN"),new Role("SUPERUSER")),
            //     johnDoe
            // );
            // User bob = new User (
            //     "Bob Doe",
            //     "bob.doe@gmail.com",
            //     "123456",
            //     List.of(new Role("FACULTY")),
            //     bobDoe
            // );
 
        
//        // List<Student> list_o_students = List.of(janeDoe);
//        String students = List.of(janeDoe,johnDoe,bobDoe).toString();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode expected = mapper.readTree(response);
//        JsonNode actual = mapper.readTree(students);
//        log.info("Expected => [{}]\n Actual => [{}]", expected,actual);
//        Assert.assertEquals(expected,actual);
    }

}
