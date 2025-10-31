package com.biblioteca.library_api.service;

import com.biblioteca.library_api.dto.BookRequestDTO;
import com.biblioteca.library_api.dto.BookResponseDTO;
import com.biblioteca.library_api.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.library_api.repository.bookRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;





@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private bookRepository _bookRepository;

// Converciones de BookRequestDTO a Entity
@Override
public Book convertToEntity(BookRequestDTO bookRequestDTOS){
    Book book = new Book();
    book.setTitle(bookRequestDTOS.getTitle());
    book.setAuthor(bookRequestDTOS.getAuthor());
    book.setPublicationYear(bookRequestDTOS.getPublicationYear());
    book.setIsbn(bookRequestDTOS.getIsbn());
    book.setStatus(bookRequestDTOS.getStatus());
    return book;
}

// Convercion de Response a Entity
@Override
public Book convertResponseToEntity(BookResponseDTO bookResponseDTOS){
    Book book = new Book();
    book.setId(bookResponseDTOS.getId());
    book.setTitle(bookResponseDTOS.getTitle());
    book.setAuthor(bookResponseDTOS.getAuthor());
    book.setPublicationYear(bookResponseDTOS.getPublicationYear());
    book.setIsbn(bookResponseDTOS.getIsbn());
    book.setStatus(bookResponseDTOS.getStatus());
    return book;
}

//Converciones de Entity a BookResponseDTO
@Override
public BookResponseDTO convertToDTO(Book book){

    BookResponseDTO bookResponse = new BookResponseDTO();
    bookResponse.setAuthor(book.getAuthor());
    bookResponse.setId(book.getId());
    bookResponse.setIsbn(book.getIsbn());
    bookResponse.setPublicationYear(book.getPublicationYear());
    bookResponse.setStatus(book.getStatus());
    bookResponse.setTitle(book.getTitle());
    return bookResponse;
}




    @Override
    public List<BookResponseDTO> getAllBooks(){
        
        
        
        List<BookResponseDTO> ListBooks = _bookRepository.findAll()
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());

        return ListBooks;
    }

    @Override
    public BookResponseDTO getBookById(Long Id) {
        Optional<Book> book = _bookRepository.findById(Id);
        if(book.isPresent()){
            return convertToDTO(book.get());
        }
        else{
         throw new RuntimeException("libro no encontrado con ID: " + Id);
        }
        
    }

    @Override
    public BookResponseDTO createNewBook(BookRequestDTO book){

        if(book.getAuthor() == null || book.getAuthor().trim().isEmpty()){
            throw new RuntimeException("Necesita un Autor obligatorio");
        }

         if(book.getTitle() == null || book.getTitle().trim().isEmpty()){
            throw new RuntimeException("Necesita un titulo obligatorio");
        }
        

        if(book.getStatus() == null){
            book.setStatus("Disponible");
        }

            return convertToDTO(_bookRepository.save(convertToEntity(book)));
                

    }


    @Override
    public BookResponseDTO updateBook(Long Id, BookRequestDTO detailBook){

        BookResponseDTO existBook = getBookById(Id);

        if(detailBook.getTitle() != null){
            existBook.setTitle(detailBook.getTitle());
        }
        if(detailBook.getAuthor() != null){
            existBook.setAuthor(detailBook.getAuthor());
        }
        if(detailBook.getIsbn() != null){
            existBook.setIsbn(detailBook.getIsbn());
        }
        if(detailBook.getPublicationYear() != null){
            existBook.setPublicationYear(detailBook.getPublicationYear());
        }
        if(detailBook.getStatus() != null){
            existBook.setStatus(detailBook.getStatus());
        }

        return convertToDTO(_bookRepository.save(convertResponseToEntity(existBook)));
        
    }

    @Override
    public String deleteBook(Long Id){

        BookResponseDTO existBook = getBookById(Id);

        if(existBook != null){
            _bookRepository.delete(convertResponseToEntity(existBook));  
            return "Libro eliminado exitosamente";
        }

        return "Libro no encontrado";

    }

    //Personalizados

    @Override
    public List<BookResponseDTO> getBookByAuthor(String author) {
        return _bookRepository.findByAuthor(author)
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }
    
    @Override
    public List<BookResponseDTO> getBookByStatus(String status) {
        return _bookRepository.findByStatus(status)
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList())   ;
    }


}
