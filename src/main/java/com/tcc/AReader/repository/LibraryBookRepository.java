package com.tcc.areader.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.areader.model.LibraryBook;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, Long> {
  List<LibraryBook> findByUserEmail(String userEmail);

  Optional<LibraryBook> findByIsbnAndUserEmail(String isbn, String userEmail);
  Optional<LibraryBook> findByIsbn(String isbn);


}