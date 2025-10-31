package com.biblioteca.library_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.library_api.model.Book;



@Repository
public interface bookRepository extends JpaRepository<Book, Long>{

 List<Book> findByAuthor(String Author);

 List<Book> findByStatus(String status);

 List<Book> findByPublicationYear(Integer publicationYear);

    boolean existsByIsbn(String isbn);



}
