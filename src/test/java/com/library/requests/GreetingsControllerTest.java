package com.library.requests;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;
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
import com.library.lendit_book_kiosk.LenditBookKioskApplication;
import com.library.lendit_book_kiosk.student.Student;
import com.library.lendit_book_kiosk.tools.LoadFromFile;
/////////////////////////////////////////////////////////////////////
@RunWith(OrderedTestRunner.class)
@SpringBootTest(classes = LenditBookKioskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
        LoadFromFile  actual = new LoadFromFile("src/frontend/public/index.html");
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

        Student janeDoe = new Student (
                        1L,
                        "Jane Doe",
                        "jane.doe@gmail.com",
                        LocalDate.of(1989, Month.JANUARY, 6),
                        "CIS");

        Student johnDoe = new Student (
                        2L,
                        "John Doe",
                        "john.doe@gmail.com",
                        LocalDate.of(1972, Month.FEBRUARY, 15),
                        "BIT");

        Student bobDoe = new Student (
                        3L,
                        "Bob Doe",
                        "bob.doe@gmail.com",
                        LocalDate.of(1979, Month.JANUARY, 12),
                        "MIT");
        // List<Student> list_o_students = List.of(janeDoe);
        String students = List.of(janeDoe,johnDoe,bobDoe).toString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode expected = mapper.readTree(response);
        JsonNode actual = mapper.readTree(students);
        log.info("Expected => [{}]\n Actual => [{}]", expected,actual);
        Assert.assertEquals(expected,actual);
    }

}
