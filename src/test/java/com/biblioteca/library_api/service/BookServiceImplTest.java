package com.biblioteca.library_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.biblioteca.library_api.dto.BookRequestDTO;
import com.biblioteca.library_api.dto.BookResponseDTO;
import com.biblioteca.library_api.model.Book;
import com.biblioteca.library_api.repository.bookRepository;




@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {


    @Mock
    private bookRepository _bookRepository;

    @InjectMocks
    private BookServiceImpl _BookServiceImpl;


    @Test
    void getBookById_whenBookExist_thenReturnBook(){

        //Give -configuracion(Preparamos escenario - datos de prueba)
        Long bookId = 1L;
        Book book = new Book(bookId, "Cien años de soledad", "Gabriel Garcia",
         "123123123", 2000,"DISPONIBLE");


        //Simulamos que la DB encuentra el libro
        when(_bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        //Ejecutamos el services REAL con el DB falso
        BookResponseDTO result = _BookServiceImpl.getBookById(bookId);

        //THEN - Verificamos resultados
        assertNotNull(result);
        assertEquals("Cien años de soledad", result.getTitle());
        assertEquals(bookId, result.getId());

        //Verificamos que se ha llamado al Repository
        verify(_bookRepository, times(1)).findById(bookId);

    }


    @Test
    void getBookById_whenBookNotExists_thenThrowException(){

        Long notExistsBookId = 99L;

        when(_bookRepository.findById(notExistsBookId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
        (() -> _BookServiceImpl.getBookById(notExistsBookId)));


        assertEquals("libro no encontrado con ID: 99", exception.getMessage());

        verify(_bookRepository, times(1)).findById(notExistsBookId);


    }

    @Test
    void createNewBook_WhenValidData_thenReturnSaveBook(){

        //Give
        BookRequestDTO Request = new BookRequestDTO("Las maravillas del mar","Jhon","123123",
        1995,"DISPONIBLE");

        //libro que simula ser guardado en DB
        Book saveBook = new Book();
        saveBook.setId(1L);
        saveBook.setTitle("Las maravillas del mar");
        saveBook.setAuthor("Jhon");
        saveBook.setIsbn("123123");
        saveBook.setPublicationYear(1995);
        saveBook.setStatus("DISPONIBLE");

        when(_bookRepository.save(any(Book.class))).thenReturn(saveBook);

        BookResponseDTO response = _BookServiceImpl.createNewBook(Request);

        assertNotNull(response);
        assertEquals(1L,response.getId());
        assertEquals(1995,response.getPublicationYear());
        assertEquals("Jhon",response.getAuthor());

        verify(_bookRepository, times(1)).save(any(Book.class));

    }


    @Test
    void updateBook_whenBookExist_thenReturnBookUpdate(){

        Long bookId = 1L;

           // Libro existente en la BD
        Book existingBook = new Book(bookId, "Título Original", "Autor Original", 
           "123-4567890", 2000, "DISPONIBLE");

        //Datos actualizados enviados por REQUEST
        BookRequestDTO updateRequest = new BookRequestDTO();
        updateRequest.setAuthor("German");
        updateRequest.setPublicationYear(2015);
        updateRequest.setTitle("Años luz de casa");

        //libro actualizado - simulado
        Book updateBook = new Book(bookId, "Años luz de casa","German",
         "123-4567890",2015,"DISPONIBLE");

         //simulamos llamdas 
         when(_bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
         when(_bookRepository.save(any(Book.class))).thenReturn(updateBook);

         BookResponseDTO result = _BookServiceImpl.updateBook(bookId, updateRequest);

         assertNotNull(result);
         assertEquals(bookId, result.getId());
         assertEquals("German", result.getAuthor());
         assertEquals("Años luz de casa", result.getTitle());
         assertEquals(2015, result.getPublicationYear());
         assertEquals("123-4567890", result.getIsbn());
         assertEquals("DISPONIBLE", result.getStatus());

         verify(_bookRepository, timeout(1)).save(any(Book.class));
                                
    }


    @Test
    void deleteBook_whenBookExisting_thenDeleteSuccesfully(){

        Long bookId = 1L;

        Book existingBook = new Book(bookId, "Título Original", "Autor Original", 
           "123-4567890", 2000, "DISPONIBLE");


        when(_bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        String result = _BookServiceImpl.deleteBook(bookId);

        assertEquals("Libro eliminado exitosamente", result);
        
        verify(_bookRepository, times(1)).findById(bookId);
        verify(_bookRepository, times(1)).delete(any(Book.class));
    }


}
