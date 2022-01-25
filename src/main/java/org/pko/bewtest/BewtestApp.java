package org.pko.bewtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Rest app performing UI test of P&I Candidate application, with browser in docker container, controlled by Selenium Remote Web Driver.
 * Tests just candidate form.
 * APP_URL and HOST_URL predefined  for local, but used arguments from command line - as defined in Dockerfile
 */

@SpringBootApplication
public class BewtestApp {

	public static void main(String[] args) {
		SpringApplication.run(BewtestApp.class, args);
	}

}
