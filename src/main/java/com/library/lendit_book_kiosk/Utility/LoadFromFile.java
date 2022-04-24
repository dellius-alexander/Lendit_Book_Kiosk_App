package com.library.lendit_book_kiosk.Utility;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
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
    private String status = null;
    private String myFile = null;
    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(LoadFromFile.class);
    /**
     * Loads a file from path.
     * @param path the files absolute or relative path.
     * @return a byte array containing the bytes read from the file.
     */
    public LoadFromFile(String path) throws IOException 
    {
        this.status = String.format("File Retrieved from: [ %s ]", path);
        byte[] file = Files.readAllBytes(Paths.get(path));
        this.myFile = new String(file, StandardCharsets.UTF_8);
        log.info(status);
    }
    /**
     * Get a byte array containing the bytes read from file.
     * @return a byte array containing the bytes read from the file.  
     */
    public String getFile(){
        return this.myFile;
    }
    /**
     * Get a byte array containing the bytes read from file.
     * @param path the path to the file
     * @return a byte array containing the bytes read from the file.  
     */
    public String getFile(String path){
        try {
            new LoadFromFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.myFile;
    }

    @Override
    public String toString(){
        return this.myFile;
    }
}