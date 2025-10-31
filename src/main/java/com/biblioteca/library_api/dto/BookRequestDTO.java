package com.biblioteca.library_api.dto;
import jakarta.validation.constraints.*;


// Crear o Actualizar datos

public class BookRequestDTO {
    
    // ✅ COPIADO DE BOOK (sin anotaciones JPA)
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede exceder 100 caracteres")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 100, message = "El autor no puede exceder 100 caracteres")
    private String author;

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(regexp = "^[0-9-]+$", message = "El ISBN solo puede contener números y guiones")
    @Size(max = 20, message = "El ISBN no puede exceder 20 caracteres")
    private String isbn;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1000, message = "El año debe ser mayor a 1000")
    @Max(value = 2030, message = "El año no puede ser mayor a 2030")
    private Integer publicationYear;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "DISPONIBLE|PRESTADO|MANTENIMIENTO", 
             message = "El estado debe ser: DISPONIBLE, PRESTADO o MANTENIMIENTO")
    private String status;

    // ✅ Constructor vacío (igual que Book)
    public BookRequestDTO() {}

    // ✅ Constructor copiado de Book (sin el id)
    public BookRequestDTO(String title, String author, String isbn, 
                         Integer publicationYear, String status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    // ✅ Getters/Setters copiados de Book
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}