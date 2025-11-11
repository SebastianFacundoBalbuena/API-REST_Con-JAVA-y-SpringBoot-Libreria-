package com.biblioteca.library_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.Arrays;
import java.util.List;

import com.biblioteca.library_api.config.TestSecurityConfig;
import com.biblioteca.library_api.dto.BookRequestDTO;
import com.biblioteca.library_api.dto.BookResponseDTO;
import com.biblioteca.library_api.model.Book;
import com.biblioteca.library_api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;






@WebMvcTest(BookController.class)
@Import(TestSecurityConfig.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;  //simula peticiones http

     @MockitoBean
     private BookService _BookService; 

    @Autowired
    private ObjectMapper objectMapper;




     @Test
     void getAllBook_whenBookExisting_thenReturnList() throws Exception{

        //Give - configuracion
        BookResponseDTO book1 = new BookResponseDTO(1L, "Cien años","Jhon", "121221",
        2020,"DISPONIBLE");
        BookResponseDTO book2 = new BookResponseDTO(2L, "Cien años 2","Jhon", "121221",
        2021,"DISPONIBLE");

        List<BookResponseDTO> listBook = Arrays.asList(book1, book2);


        //cuando, entonces 
        when(_BookService.getAllBooks()).thenReturn(listBook);

        //veriticamos
        mockMvc.perform(get("/api/books")
            .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.length()").value(2))
         .andExpect(jsonPath("$[0].title").value("Cien años"))
         .andExpect(jsonPath("$[1].title").value("Cien años 2"));

         
     }

     @Test
     void getBookById_whenBookExisting_theReturnBook() throws Exception{


        //Give - configuracion
        Long bookId = 1L;
        BookResponseDTO book1 = new BookResponseDTO(bookId, "Cien años","Jhon", "121221",
        2020,"DISPONIBLE");

        when(_BookService.getBookById(book1.getId())).thenReturn(book1);

        mockMvc.perform(get("/api/books/{Id}", bookId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title").value("Cien años"));
     }

     @Test
     void getBookById_whenBookNotExisting_thenReturnThrows() throws Exception{

        //Give
                Long bookId = 1L;

        when(_BookService.getBookById(bookId)).thenThrow( new RuntimeException("libro no encontrado con el ID: " + bookId));

        mockMvc.perform(get("/api/books/{Id}", bookId)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
        
     }


     @Test
     void createNewBook_whenSaveBook_thenReturnSaveBook() throws Exception{

        //Give
        BookRequestDTO newBook = new BookRequestDTO("Cien años", "Jhon", "121212",
        2020, "DISPONIBLE");

        Book bookEntity = new Book(1L,newBook.getTitle(), newBook.getAuthor(), newBook.getIsbn(),
         newBook.getPublicationYear(), newBook.getStatus());

         BookResponseDTO bookDTO = new BookResponseDTO(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getAuthor(),
          bookEntity.getIsbn(), bookEntity.getPublicationYear(), bookEntity.getStatus());

          when(_BookService.createNewBook(any(BookRequestDTO.class))).thenReturn(bookDTO);

          mockMvc.perform(post("/api/books")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(newBook)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(bookDTO.getId()))
          .andExpect(jsonPath("$.title").value(bookDTO.getTitle()));
          
          

     }

     @Test
     void updateBook_WhenBookExists_ThenReturnUpdatedBook() throws Exception {
    // GIVEN
    Long bookId = 1L;
    BookRequestDTO request = new BookRequestDTO("1984 - Edición Especial", "George Orwell", 
                                               "978-0451524935", 1949, "PRESTADO");
    
    BookResponseDTO response = new BookResponseDTO(bookId, "1984 - Edición Especial", "George Orwell", 
                                                  "978-0451524935", 1949, "PRESTADO");
    
    when(_BookService.updateBook(eq(bookId), any(BookRequestDTO.class))).thenReturn(response);

    // WHEN & THEN
    mockMvc.perform(put("/api/books/{id}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(bookId))
            .andExpect(jsonPath("$.title").value("1984 - Edición Especial"))
            .andExpect(jsonPath("$.status").value("PRESTADO"));
}


@Test
void deleteBook_whenBookExisting_thenReturnNotContent() throws Exception{
    Long bookId = 1L;

    when(_BookService.deleteBook(bookId)).thenReturn("libro eliminado exitosamente");

    mockMvc.perform(delete("/api/books/{id}", bookId))
    .andExpect(status().isNoContent());

}


@Test
void getBooksByAuthor_WhenBooksExist_ThenReturnList() throws Exception {
    // GIVEN
    String author = "Gabriel García Márquez";
    BookResponseDTO book1 = new BookResponseDTO(1L, "Cien años de soledad", author, 
                                               "978-8437604947", 1967, "DISPONIBLE");
    BookResponseDTO book2 = new BookResponseDTO(2L, "El amor en los tiempos del cólera", author, 
                                               "978-0307474728", 1985, "DISPONIBLE");
    
    List<BookResponseDTO> books = Arrays.asList(book1, book2);
    
    when(_BookService.getBookByAuthor(author)).thenReturn(books);

    // WHEN & THEN
    mockMvc.perform(get("/api/books/author/{author}", author))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].author").value(author))
            .andExpect(jsonPath("$[1].author").value(author));
}


@Test
void getBooksByStatus_WhenBooksExist_ThenReturnList() throws Exception {
    // GIVEN
    String status = "DISPONIBLE";
    BookResponseDTO book1 = new BookResponseDTO(1L, "Cien años de soledad", "Gabriel García Márquez", 
                                               "978-8437604947", 1967, status);
    BookResponseDTO book2 = new BookResponseDTO(2L, "1984", "George Orwell", 
                                               "978-0451524935", 1949, status);
    
    List<BookResponseDTO> books = Arrays.asList(book1, book2);
    
    when(_BookService.getBookByStatus(status)).thenReturn(books);

    // WHEN & THEN
    mockMvc.perform(get("/api/books/status/{status}", status))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].status").value(status))
            .andExpect(jsonPath("$[1].status").value(status))
            .andExpect(jsonPath("$[0].title").value("Cien años de soledad"))
            .andExpect(jsonPath("$[1].title").value("1984"));
}

}
