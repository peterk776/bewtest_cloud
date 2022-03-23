package com.piag.uitests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Rest app performing UI test of P&I Candidate application, with browser in docker container, controlled by Selenium Remote Web Driver.
 * Tests just candidate form.
 * APP_URL and HOST_URL predefined  for local, but used arguments from command line - as defined in Dockerfile
 *
 * todo:
 * - add security
 * - do configuration handling thread safe if necessary, currently is not
 * - implement tests
 */

@SpringBootApplication
@EnableScheduling
@ComponentScan
public class BewtestApp {

	public static void main(String[] args) {
		SpringApplication.run(BewtestApp.class, args);
	}

}
