package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////
import java.io.IOException;
import com.library.lendit_book_kiosk.tools.LoadFromFile;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
/////////////////////////////////////////////////////////////////////
@RestController
@RequestMapping(path = "/", method = RequestMethod.GET)
public class IndexController {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private LoadFromFile loadFile;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
	public String index() throws IOException {
        loadFile = new LoadFromFile();
        String index = loadFile.loadFromFile("src/main/resources/static/html/index.html");
        log.info("INDEX => [ {} ]",index);
		return index;
	}
}