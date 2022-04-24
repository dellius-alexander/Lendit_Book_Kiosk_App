package com.library.lendit_book_kiosk.Utility;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.Serializable;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateFile implements Serializable {
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
                File newFile = new File("./data/api/createFile/"+file);
                if (newFile.createNewFile()) {
                    status = String.format("File created => \n{}", newFile.getName());
                } else {
                    status = String.format("File already exists => {}.",newFile.getName());
                }
            } catch (IOException e) {
                status = String.format("An error occurred. \n{}", e.getMessage());
                log.error("{}", status);               
            } finally {
                log.info(status);
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