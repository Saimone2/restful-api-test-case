package com.saimone.restfulapitestcase;

import com.saimone.restfulapitestcase.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulApiTestCaseApplication {
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> logger.info("The program is shutting down")));

		SpringApplication.run(RestfulApiTestCaseApplication.class, args);
	}
}
