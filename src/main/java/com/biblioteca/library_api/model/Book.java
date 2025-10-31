package com.biblioteca.library_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;



@Entity  // Le decimos que es una tabla de SQL
@Table(name = "books")     // le asignamos nombre
public class Book {



//Constructor

public Book(){}


public Book(Long id, String title, String author, String isbn, Integer publicationYear, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    public Book( String title, String author, String isbn, Integer publicationYear, String status) {
    
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.status = status;
    }

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)  //autoincremental
private Long id;

@NotBlank(message = "El titulo es obligatorio")
@Size(max = 100, message = "no puede exceder a mas de 100 caracteres")
@Column(name = "title", nullable = false, length = 100)
private String title;

@NotBlank(message = "El autor es obligatorio")
@Size(max = 100, message = "No debe exceder a mas de 100 caracteres")
@Column(name = "author", nullable = false, length = 100)
private String author;


@NotBlank(message = "El isbn es obligatorio")
@Size(max = 20, message = "El isbn no debe exceder a mas de 20 caracteres")
@Pattern(regexp = "^[0-9-]+$", message = "El ISBN solo puede contener números y guiones")
@Column(name = "isbn", nullable = false, length = 20)
private String isbn;


@NotNull(message = "El año de publicacion es obligatorio")
@Min(value = 1000, message = "el año no debe ser menor a 1000")
@Max(value = 2030, message = "el año no debe ser mayor a 2030")
@Column(name = "publication_year")
private Integer publicationYear;


@NotBlank(message = "El estado es obligatorio")
@Pattern(regexp = "DISPONIBLE|PRESTADO|MANTENIMIENTO", 
             message = "El estado debe ser: DISPONIBLE, PRESTADO o MANTENIMIENTO")
@Column(name = "status", length = 20)
private String status;


//Getters y Setters

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getAuthor() {
    return author;
}

public void setAuthor(String author) {
    this.author = author;
}

public String getIsbn() {
    return isbn;
}

public void setIsbn(String isbn) {
    this.isbn = isbn;
}

public Integer getPublicationYear() {
    return publicationYear;
}

public void setPublicationYear(Integer publicationYear) {
    this.publicationYear = publicationYear;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}




}
