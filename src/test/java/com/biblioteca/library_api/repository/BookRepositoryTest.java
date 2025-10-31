package com.biblioteca.library_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import com.biblioteca.library_api.model.Book;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;





@DataJpaTest
public class BookRepositoryTest {


    @Autowired
    private bookRepository _bookRepository;


    @Test
    void contextLoad(){

        assertNotNull(_bookRepository, "el bookRepository se esta inyectando correctamente");

        assertTrue(_bookRepository instanceof bookRepository, "debe ser el repo real y no el mock");
    }


    @Test
    void getBookById_whenBookExists_thenReturnBook(){
        //GIVE - CONFIGURACION
        Book book = new Book("Cien años","Gustavo","1212",2000,"DISPONIBLE");
        Book saveBook = _bookRepository.save(book);
        Long bookId = saveBook.getId();

        Optional<Book> foundBook = _bookRepository.findById(bookId);

        assertTrue(foundBook.isPresent(),"el libro deberia existir en la DB");
        assertEquals(2000, foundBook.get().getPublicationYear());
        assertEquals("Gustavo", foundBook.get().getAuthor());
        assertEquals("Cien años", foundBook.get().getTitle());

        System.out.println("libro encontrado exitosamente con ID: " + foundBook.get().getId());
    }

    @Test
    void getAllBook_whenAllBookExists_thenReturnListBook(){

        //GIVE 
        Book book = new Book("Cien años","Gustavo","1212",2000,"DISPONIBLE");
        Book book2 = new Book("Cien años 2","Gustavo","1212",2001,"DISPONIBLE");

        List<Book> listBooks =  Arrays.asList(book, book2);

        _bookRepository.saveAll(listBooks);

        assertNotNull(listBooks);
        assertEquals("Cien años", book.getTitle());
        assertEquals("Cien años 2",book2.getTitle());
        assertEquals(2, listBooks.size());
        assertFalse(listBooks.isEmpty());
    }

    @Test
    void createNewBook_whenSaveBookSucesfull_thenReturnSaveBook(){
        //GIVE
        Book book = new Book("Cien años","Gustavo","1212",2000,"DISPONIBLE");
        Book saveBook = _bookRepository.save(book);
        Optional<Book> bookSave = _bookRepository.findById(saveBook.getId());
        
        
        assertNotNull(saveBook.getId(), "Deberia generar un id automaticamente");
        assertEquals("Cien años", saveBook.getTitle());
        assertTrue(bookSave.isPresent(),"deberia existir el libro");

     System.out.println("✅ Libro guardado correctamente con ID: " + bookSave.get().getId());


    }


    @Test
void updateBook_WhenBookExists_ThenReturnUpdatedBook() {
    // GIVEN - Configuración: Guardamos un libro primero
    Book originalBook = new Book("Título Original", "Autor Original", 
                                "123-4567890", 2000, "DISPONIBLE");
    Book savedBook = _bookRepository.save(originalBook);
    Long bookId = savedBook.getId();

    // WHEN - Ejecución: Actualizamos el libro
    savedBook.setTitle("Título Actualizado");
    savedBook.setAuthor("Autor Actualizado"); 
    savedBook.setStatus("PRESTADO");
     _bookRepository.save(savedBook);

    // THEN - Verificación
    Optional<Book> foundBook = _bookRepository.findById(bookId);
    
    assertTrue(foundBook.isPresent(), "El libro debería seguir existiendo");
    
    // Verificamos que los datos se actualizaron
    assertEquals("Título Actualizado", foundBook.get().getTitle());
    assertEquals("Autor Actualizado", foundBook.get().getAuthor());
    assertEquals("PRESTADO", foundBook.get().getStatus());
    
    // Verificamos que los campos NO actualizados se mantienen igual
    assertEquals("123-4567890", foundBook.get().getIsbn());
    assertEquals(2000, foundBook.get().getPublicationYear());
    
    System.out.println("✅ Libro actualizado correctamente. ID: " + bookId);
}


@Test
void deleteBook_WhenBookExists_ThenRemoveFromDatabase() {
    // GIVEN - Configuración: Guardamos un libro primero
    Book book = new Book("Libro a Eliminar", "Autor Ejemplo", 
                        "999-8887777", 2020, "DISPONIBLE");
    Book savedBook = _bookRepository.save(book);
    Long bookId = savedBook.getId();

    // Verificamos que existe antes de eliminar
    Optional<Book> bookBeforeDelete = _bookRepository.findById(bookId);
    assertTrue(bookBeforeDelete.isPresent(), "El libro debería existir antes de eliminar");

    // WHEN - Ejecución: Eliminamos el libro
    _bookRepository.delete(savedBook);

    // THEN - Verificación: El libro ya no debería existir
    Optional<Book> bookAfterDelete = _bookRepository.findById(bookId);
    assertFalse(bookAfterDelete.isPresent(), "El libro debería ser eliminado de la BD");
    
    System.out.println("✅ Libro eliminado correctamente. ID: " + bookId);
}


@Test
void findByAuthor_ThenReturnBooks() {
    // GIVEN
    Book book1 = new Book("Cien años", "Gabriel García Márquez", "111", 1967, "DISPONIBLE");
    Book book2 = new Book("El amor", "Gabriel García Márquez", "222", 1985, "PRESTADO");
    _bookRepository.saveAll(Arrays.asList(book1, book2));

    // WHEN
    List<Book> result = _bookRepository.findByAuthor("Gabriel García Márquez");

    // THEN
    assertEquals(2, result.size());
    assertEquals("Gabriel García Márquez", result.get(0).getAuthor());
}

@Test
void findByStatus_ThenReturnBooks() {
    // GIVEN
    Book book1 = new Book("Libro 1", "Autor 1", "111", 2020, "DISPONIBLE");
    Book book2 = new Book("Libro 2", "Autor 2", "222", 2021, "DISPONIBLE");
    Book book3 = new Book("Libro 3", "Autor 3", "333", 2022, "PRESTADO");
    _bookRepository.saveAll(Arrays.asList(book1, book2, book3));

    // WHEN
    List<Book> result = _bookRepository.findByStatus("DISPONIBLE");

    // THEN
    assertEquals(2, result.size());
    assertEquals("DISPONIBLE", result.get(0).getStatus());
}

}
