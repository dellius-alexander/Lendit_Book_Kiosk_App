package com.library.lendit_book_kiosk.Tools;
/////////////////////////////////////////////////////////////////////
// Import Dependencies
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileNotFoundException;

// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteToFile implements Serializable {

    // Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(WriteToFile.class);
    String filePath = null;
    String status = null;
    public WriteToFile(String fileName, String data) {

        File file = null;
        // search for file in each directory from PWD or "."
        try (Stream<Path> walkStream = Files.walk(Paths.get("./data/api").toAbsolutePath().normalize())) {
            
            walkStream.filter(p -> p.toFile().isFile()).forEach(f -> {
   
                if(fileName.equalsIgnoreCase(f.getFileName().toString()))
                {
                    filePath = f.toString();
                    log.info("Located file to write to: FILENAME => "+f.toString());
                } 
                
            });
            // search one level up if file not found
            if(filePath == null)
                try (Stream<Path> walkStreamAgain = Files.walk(Paths.get("..").toAbsolutePath().normalize())) {
                    walkStreamAgain.filter(p -> p.toFile().isFile()).forEach(f -> {
                        // if (f.toString().endsWith(filename)) {
                        //     filePath = f.toString();
                        // }
                        if(fileName.equalsIgnoreCase(f.getFileName().toString())){filePath = f.toString();}
                    });
            }
            // exit if we still can't locate the file and throw an exception
            if(filePath == null)
            {
                // System.err.println("\nUnable to locate file: "+filename+"\n");
                throw new FileNotFoundException("Unable to locate file: "+fileName+"\n"); 
                // System.exit(1);
            }
            
        }catch (Exception e){
            log.error("\nSomething went wrong...\n"+e.getMessage());
            // e.printStackTrace();
        }
        // write to file
        try {
            file = new File(filePath);
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(data);
            myWriter.close();
            log.info("Successfully wrote to the file.");
            status = "Successfully wrote to the file.";
        } catch (IOException e) {
            status = "An error occurred."+ e.getMessage();
            log.error("An error occurred."+ e.getMessage());
            // e.printStackTrace();
        }                
            
        
    }
    /**
     * Gets the success status
     * @return success response message
     */
    public String GetStatus(){return this.status;}
}

