package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableCaching
@SpringBootApplication
public class CrudWithPostgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudWithPostgresqlApplication.class, args);
	}

}
