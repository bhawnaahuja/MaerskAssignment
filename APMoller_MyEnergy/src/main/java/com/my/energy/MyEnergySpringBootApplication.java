package com.my.energy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.my.energy")
public class MyEnergySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyEnergySpringBootApplication.class, args);
	}
}
