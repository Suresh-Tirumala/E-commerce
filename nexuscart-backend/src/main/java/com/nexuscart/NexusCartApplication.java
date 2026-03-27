package com.nexuscart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * NexusCart E-Commerce Platform - Main Application Entry Point
 * 
 * A production-level e-commerce system built with Spring Boot 3,
 * featuring modular architecture, security, and scalability.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableCaching
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
public class NexusCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexusCartApplication.class, args);
    }
}
