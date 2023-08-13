package com.saimone.restfulapitestcase;

import com.saimone.restfulapitestcase.logger.UserLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;

@SpringBootApplication
public class RestfulApiTestCaseApplication {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> UserLogger.LOGGER.log(Level.INFO, "The program is shutting down")));

		SpringApplication.run(RestfulApiTestCaseApplication.class, args);
		UserLogger.LOGGER.log(Level.INFO, "The program is started");
	}
}
