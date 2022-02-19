package com.library.lendit_book_kiosk.tools;
/////////////////////////////////////////////////////////////////////
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
/**
 * This class loads a local file from a exact file path.
 */
public class LoadFromFile implements Serializable {
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(LoadFromFile.class);
    /**
     * Loads a file from the files Path.
     * @param filePath the files absolute or relative path.
     */
    public String loadFromFile(String filePath) throws IOException {
        log.info("File Retrieved: [ {} ]",filePath.toString());
        return 
            new String(
                Files.readAllBytes(Paths.get(filePath))
        );
    }
}
