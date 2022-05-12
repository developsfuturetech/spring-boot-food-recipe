package com.capgemini.recipes;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @SpringBootApplication encapsulates @ComponentScan
 * @ComponentScan must be declared at the higher level package
 * If @SpringBootApplication or @ComponentScan is not at the higher level application, then
 * every package who should be scanned must be declared explicitly.
 */
@SpringBootApplication
public class StandaloneApplication {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	public static void main(String[] args) {
        SpringApplication.run(StandaloneApplication.class, args);
    }

}
