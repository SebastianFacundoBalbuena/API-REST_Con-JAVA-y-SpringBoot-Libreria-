package com.biblioteca.library_api.dto;

public class BookResponseDTO {
    
    // ✅ Incluye el ID (para respuesta)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String status;

    // ✅ Constructores
    public BookResponseDTO() {}

    public BookResponseDTO(Long id, String title, String author, String isbn, 
                          Integer publicationYear, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.status = status;
    }

    // ✅ Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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