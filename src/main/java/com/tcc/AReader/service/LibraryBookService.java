package com.tcc.areader.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.tcc.areader.exception.BadRequestException;
import com.tcc.areader.model.Annotation;
import com.tcc.areader.model.Book;
import com.tcc.areader.model.LibraryBook;
import com.tcc.areader.repository.LibraryBookRepository;
import com.tcc.areader.request.AddBookRequest;
import com.tcc.areader.util.Status;

@Service
public class LibraryBookService {

  @Autowired
  private LibraryBookRepository libraryRepository;
  @Autowired
  private BookService bookService;

  public LibraryBook addBookToLibrary(AddBookRequest addBookRequest)
      throws ClientProtocolException, NotFoundException, IOException {

    Book book = bookService.getBook(addBookRequest.getIsbn());

    if (libraryBookExists(addBookRequest.getIsbn(), addBookRequest.getUserEmail()).isPresent()) {
      throw new BadRequestException("Livro já adicionado na biblioteca");
    }
    return libraryRepository.save(LibraryBook.build(null, addBookRequest.getUserEmail(), book.getIsbn(),
        Status.WANT_TO_READ, null, book, null, new ArrayList<Annotation>()));
  }

  public Optional<LibraryBook> libraryBookExists(String isbn, String userEmail) {
    return libraryRepository.findByIsbnAndUserEmail(isbn, userEmail);
  }

  public LibraryBook updateStatus(Long id, Status status) {
    LibraryBook libraryBook = libraryRepository.findById(id).get();
    libraryBook.setStatus(status);
    return libraryRepository.save(libraryBook);
  }

  public void removeBookFromLibrary(Long id) {
    libraryRepository.deleteById(id);
  }

  public List<LibraryBook> getAllLibraryBooksFromUser(String userEmail) {
    List<LibraryBook> libraryBooks = libraryRepository.findByUserEmail(userEmail);

    if (libraryBooks.isEmpty()) {
      throw new BadRequestException("Nenhum livro adicionado na biblioteca");
    }
    return libraryBooks;
  }

  public List<Annotation> getAnnotationsOfLibraryBook(Long libraryBookId) {
    LibraryBook libraryBook = libraryRepository.findById(libraryBookId).orElse(null);
    if (libraryBook != null) return libraryBook.getAnnotations();
    else throw new BadRequestException("Não há anotações neste livro");
  }

  public Optional<LibraryBook> getLibraryBookById(long libraryBookId) {
        Optional<LibraryBook> libraryBook = libraryRepository.findById(libraryBookId);
        if (libraryBook.isPresent()) return libraryBook;
        else throw new BadRequestException("Livro não encontrado na biblioteca, id: " + libraryBookId);
      }
}