package com.biblioteca.library_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class LibraryApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(LibraryApiApplication.class, args);
		
		System.out.println(" ✅ API de Biblioteca ejecutándose en: http://localhost:8080");

	}

}
