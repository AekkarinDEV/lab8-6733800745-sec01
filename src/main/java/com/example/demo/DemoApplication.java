package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Lab 8 Spring Boot Application.
 * Bootstraps the application context, embedded web container, and JPA configurations.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Main method executed by the JVM to launch the Spring Boot application.
     *
     * @param args Command line arguments passed during execution.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
