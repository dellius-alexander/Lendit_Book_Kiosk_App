package com.library.lendit_book_kiosk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LenditBookKioskApplicationTests {

	@Autowired
	private GreetingsController greetingsController;
	
	@Test
	public void contextLoads() {
		assertThat(greetingsController).isNotNull();
	}

}
