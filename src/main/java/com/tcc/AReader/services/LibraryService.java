package com.tcc.areader.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tcc.areader.models.Book;
import com.tcc.areader.models.LibraryBook;
import com.tcc.areader.repositories.BookRepository;
import com.tcc.areader.repositories.LibraryBookRepository;
import com.tcc.areader.requests.AddBookRequest;
import com.tcc.areader.requests.RemoveBookRequest;
import com.tcc.areader.requests.UpdateStatusRequest;
import com.tcc.areader.utils.Status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LibraryService {
  private final BookRepository bookRepository;
  private final LibraryBookRepository libraryRepository;

  public LibraryBook addBook(AddBookRequest addBookRequest) {
    Optional<Book> book = bookRepository.findByIsbn(addBookRequest.getIsbn());
    if (book.isEmpty()) {
      book = Optional.of(Book.builder().isbn(addBookRequest.getIsbn()).build());
      bookRepository.save(book.get());
    }
    LibraryBook library = LibraryBook.builder()
        .book(book.get())
        .userEmail(addBookRequest.getUserEmail())
        .status(Status.WANT_TO_READ)
        .build();
    libraryRepository.save(library);
    return library;
  }

  public LibraryBook updateStatus(UpdateStatusRequest request) {
    LibraryBook library = libraryRepository.findByIsbn(request.isbn).get();
    library.setStatus(request.status);
    libraryRepository.save(library);
    return library;
  }

  public void removeBook(RemoveBookRequest request) {
    libraryRepository.deleteById(request.id);
  }

  public List<LibraryBook> getBooks(String userEmail) {
    return libraryRepository.findByUserEmail(userEmail);
  }
}
