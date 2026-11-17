package org.example.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LmsApplication {

	static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}

}
