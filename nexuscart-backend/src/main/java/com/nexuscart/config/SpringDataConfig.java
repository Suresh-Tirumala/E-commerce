package com.nexuscart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Spring Data Web Configuration
 * 
 * Enables advanced Spring Data web support including pagination and sorting.
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class SpringDataConfig {
    // Additional Spring Data configurations can be added here
}
