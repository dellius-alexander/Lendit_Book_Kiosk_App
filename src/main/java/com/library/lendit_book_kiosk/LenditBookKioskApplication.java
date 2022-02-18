package com.library.lendit_book_kiosk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// LOGGING CLASSES
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class LenditBookKioskApplication implements CommandLineRunner
{
	// Define a logger instance and log what you want.
	private static final Logger log = LoggerFactory.getLogger(LenditBookKioskApplication.class);
	String parentField;
	public static void main(String[] args) {
		SpringApplication.run(LenditBookKioskApplication.class, args);
		log.info("------------Your Spring Application has started......!");
	}
	@Override
	public void run(String... args) throws Exception {
		log.info("EXECUTING : command line runner");
 
        for (int i = 0; i < args.length; ++i) {
            log.info("args[{}]: {}", i, args[i]);
        }
		
	}
	// @Override
	// public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
	// 		startSerialize(gen);
    //         doSerialize(gen, serializers);
    //         endSerialize(gen);
		
	// }
	
	// @Override
	// public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1, TypeSerializer arg2) throws IOException {
	// 	// TODO Auto-generated method stub
		
	// }
	// protected void startSerialize(JsonGenerator gen) throws IOException {
	// 	gen.writeStartObject();
	// }

	// protected void doSerialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
	// 	gen.writeStringField("parentField", parentField);
	// }

	// protected void endSerialize(JsonGenerator gen) throws IOException {
	// 	gen.writeEndObject(); // Closing Object..
	// }

}
