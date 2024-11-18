package com.deceville.event_sphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class EventSphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSphereApplication.class, args);
	}

}
