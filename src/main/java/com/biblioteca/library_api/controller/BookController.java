package com.biblioteca.library_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.biblioteca.library_api.dto.BookRequestDTO;
import com.biblioteca.library_api.dto.BookResponseDTO;
import com.biblioteca.library_api.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("api/books")
public class BookController {


    @Autowired
    private BookService _BookService;


    @GetMapping()
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(){

        try {
            
            List<BookResponseDTO> books = _BookService.getAllBooks();

            return  ResponseEntity
            .status(HttpStatus.OK)
            .body(books);

        } catch (Exception e) {

            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(null);
        }

    }

    @GetMapping("/{Id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long Id){
       
        try {
            BookResponseDTO existBook = _BookService.getBookById(Id);
            if(existBook != null){
                return ResponseEntity
                .status(HttpStatus.OK)
                .body(existBook);
            }
        } catch (RuntimeException e) {
           
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(null);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PostMapping()    
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO book){

        try {
            

                BookResponseDTO response = _BookService.createNewBook(book);

                return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
            

        } catch (RuntimeException e) {
           
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(null);
        }

    }

    
    @PutMapping("/{Id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long Id,@Valid @RequestBody BookRequestDTO book){

        try {
          
           BookResponseDTO bookUpdate =  _BookService.updateBook(Id, book);
           return ResponseEntity
           .status(HttpStatus.OK)
           .body(bookUpdate);

        } catch (Exception e) {
            
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(null);
        }

    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long Id){

        try {
            
            _BookService.deleteBook(Id);
            return ResponseEntity
            .status(HttpStatus.NO_CONTENT).build();


        } catch (Exception e) {
            
            return ResponseEntity
            .status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookResponseDTO>> getBookByAuthor(@PathVariable String author){

        try {
            
            List<BookResponseDTO> book = _BookService.getBookByAuthor(author);

            return ResponseEntity
            .status(HttpStatus.OK)
            .body(book);

        } catch (Exception e) {
            
            return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
        }
    }
    
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookResponseDTO>> getBookByStatus(@PathVariable String status){

        try {
            
            List<BookResponseDTO> book = _BookService.getBookByStatus(status);

            return ResponseEntity
            .status(HttpStatus.OK)
            .body(book);

        } catch (Exception e) {
            
            return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
        }
    }

}
