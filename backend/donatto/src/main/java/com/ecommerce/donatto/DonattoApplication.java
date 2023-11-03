package com.ecommerce.donatto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DonattoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonattoApplication.class, args);
	}

}
