package com.tcc.areader.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tcc.areader.models.Book;
import com.tcc.areader.models.LibraryBook;
import com.tcc.areader.repositories.BookRepository;
import com.tcc.areader.repositories.LibraryBookRepository;
import com.tcc.areader.requests.AddBookRequest;
import com.tcc.areader.utils.Status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LibraryService {
  private final BookRepository bookRepository;
  private final LibraryBookRepository libraryRepository;

  public LibraryBook addBook(AddBookRequest addBookRequest) {
    Optional<Book> bookOptional = bookRepository.findByIsbn(addBookRequest.getIsbn());
    Book book;
    System.out.println("livro opcional");
    System.out.println(bookOptional);
    if (bookOptional.isPresent()) {
      book = bookOptional.get();
      System.out.println("livro esta presente book = bookptional" + book);
    } else {
      book = Book.builder().isbn(addBookRequest.getIsbn()).build();
      System.out.println("livro não existe ainda book = criando livro " + book);
      bookRepository.save(book);
    }
    LibraryBook library = LibraryBook.builder()
        .book(book)
        .userEmail(addBookRequest.getUserEmail())
        .status(Status.WANT_TO_READ)
        .isbn(book.getIsbn())
        .build();
    libraryRepository.save(library);
    return library;
  }

  public LibraryBook updateStatus(Long id, Status status) {
    LibraryBook library = libraryRepository.findById(id).get();
    library.setStatus(status);
    return libraryRepository.save(library);
  }

  public void removeBook(Long id) {
    libraryRepository.deleteById(id);
  }

  public List<LibraryBook> getBooks(String userEmail) {
    return libraryRepository.findByUserEmail(userEmail);
  }
}