package com.library.lendit_book_kiosk.tools;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFile {
    private String[] args= {""};
    private String status="";
    private static final Logger log = LoggerFactory.getLogger(CreateFile.class);
    /** 
     * Create file(s)
     * @param args an array of files
     */
    public CreateFile(String[] args) {
        this.args = args;
        this.NewFile();
    }
    /**
     * Creates new file
     * @return success reponse message
     */
    private String NewFile(){

        for (String file : args) {
            try {
                File myObj = new File("./data/api/createFile/"+file);
                if (myObj.createNewFile()) {
                    status = "File created: " + myObj.getName();
                    log.info("File created: " + myObj.getName());
                } else {
                    status = "File already exists.";
                    log.error("File already exists.");
                }
            } catch (IOException e) {
                status = "An error occurred. \n" + e.getMessage();
                log.error("An error occurred. {}", e.getMessage());               
            }
        }
        return status;
    }
    /**
     * Gets the success status
     * @return success response message
     */
    public String GetStatus(){return this.status;}
}