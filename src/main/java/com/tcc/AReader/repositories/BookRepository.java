package com.tcc.areader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.areader.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  Book findByIsbn(String isbn);
}