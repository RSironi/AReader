package com.tcc.areader.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcc.areader.models.Book;
import com.tcc.areader.models.LibraryBook;
import com.tcc.areader.repositories.LibraryBookRepository;
import com.tcc.areader.requests.AddBookRequest;
import com.tcc.areader.utils.Status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LibraryService {
  private final LibraryBookRepository libraryRepository;
  private final BookService bookService;

  public ResponseEntity<String> addBookToLibrary(AddBookRequest addBookRequest) throws ClientProtocolException, NotFoundException, IOException {
    

    Book book = bookService.getBook(addBookRequest.getIsbn());
    if (book == null) {
      return ResponseEntity.badRequest().body("Book not found");
    }
    LibraryBook library = LibraryBook.builder()
        .book(book)
        .userEmail(addBookRequest.getUserEmail())
        .status(Status.WANT_TO_READ)
        .isbn(book.getIsbn())
        .build();
    libraryRepository.save(library);
    return ResponseEntity.ok().body(library.toString());
  }

  public LibraryBook updateStatus(Long id, Status status) {
    LibraryBook library = libraryRepository.findById(id).get();
    library.setStatus(status);
    return libraryRepository.save(library);
  }

  public void removeBookFromLibrary(Long id) {
    libraryRepository.deleteById(id);
  }

  public List<LibraryBook> getBooks(String userEmail) {
    return libraryRepository.findByUserEmail(userEmail);
  }
  public LibraryBook getBookFromLibraryBook(String isbn) {
    return libraryRepository.findByIsbn(isbn).get();
  }
}