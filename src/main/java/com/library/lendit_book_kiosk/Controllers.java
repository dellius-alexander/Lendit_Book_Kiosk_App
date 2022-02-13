package com.library.lendit_book_kiosk;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
class GreetingsController {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(GreetingsController.class);
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> greeting(@PathVariable String name) {
        log.info("Hello " + name + "!");
        return List.of("Hello",name);
    }
}
// /**
//  * A simple controller to create file
//  */
// @RestController
// class CreateFileController{
//     // Define a logger instance and log what you want.
// 	private static final Logger log = LoggerFactory.getLogger(CreateFileController.class);
//     /**
//      * Creates a new file for TCP request
//      * @param name filename
//      * @return Success Message
//      */
//     @RequestMapping(value = "/createFile/{name}", method = RequestMethod.POST)
//     @ResponseStatus(HttpStatus.OK)
//     public List<String> createFile(@PathVariable String name){
//         String[] files = {name};
//         CreateFile cf = new CreateFile(files);
//         log.info(cf.GetStatus()); 
//         return  List.of(cf.GetStatus());
//     }

// }