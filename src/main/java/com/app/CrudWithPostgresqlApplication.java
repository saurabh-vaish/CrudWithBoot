package com.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableCaching
@SpringBootApplication
//@EnableEncryptableProperties
public class CrudWithPostgresqlApplication implements CommandLineRunner {

	@Value("${secret.password}")
	private String pass;

	public static void main(String[] args) {
		SpringApplication.run(CrudWithPostgresqlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(pass);
	}
}
