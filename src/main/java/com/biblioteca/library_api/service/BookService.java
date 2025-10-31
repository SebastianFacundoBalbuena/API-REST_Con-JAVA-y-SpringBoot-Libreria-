package com.biblioteca.library_api.service;
import java.util.List;

import com.biblioteca.library_api.dto.BookRequestDTO;
import com.biblioteca.library_api.dto.BookResponseDTO;
import com.biblioteca.library_api.model.Book;




public interface BookService {

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookById(Long Id);

    BookResponseDTO createNewBook(BookRequestDTO book);

    BookResponseDTO updateBook(Long Id, BookRequestDTO book);

    String deleteBook(Long Id);

    //Busqueda especificas
    List<BookResponseDTO> getBookByAuthor(String author);
    List<BookResponseDTO> getBookByStatus(String status);


    // ✅ MÉTODOS DE CONVERSIÓN (opcionales, pueden estar en el impl)
    Book convertToEntity(BookRequestDTO bookRequestDTO);
    Book convertResponseToEntity(BookResponseDTO bookRequestDTO);
    BookResponseDTO convertToDTO(Book book);



}
