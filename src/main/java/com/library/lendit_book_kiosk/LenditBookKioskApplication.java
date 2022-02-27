package com.library.lendit_book_kiosk;
/////////////////////////////////////////////////////////////////////
// import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import com.fasterxml.jackson.core.JsonGenerator;
// import com.fasterxml.jackson.databind.SerializerProvider;
// import com.fasterxml.jackson.databind.JsonSerializable;
// import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.Serializable;
// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/////////////////////////////////////////////////////////////////////
@SpringBootApplication
public class LenditBookKioskApplication implements CommandLineRunner, Serializable
{
	// Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.
		getLogger(LenditBookKioskApplication.class);
		
	String parentField;
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	////////////////////	MAIN DRIVER 	////////////////////
	////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		SpringApplication.run(LenditBookKioskApplication.class, args);
		log.info("------------Your Spring Application has started......!");
	
	}
	////////////////////////////////////////////////////////////
	@Override
	/**
	 * Run the commandline runner
	 */
	public void run(String... args) throws Exception {
		log.info("EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i) {
            log.info("args[{}]: {}", i, args[i]);
        }
	}
	////////////////////////////////////////////////////////////
}
/////////////////////////////////////////////////////////////////////