package com.aegro;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	
	@Test
	public void applicationContextTest() {
		Application.main(new String[] {});
	}

}
