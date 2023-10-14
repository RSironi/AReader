package com.tcc.areader.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.areader.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

  Book findByIsbn(String isbn);
}