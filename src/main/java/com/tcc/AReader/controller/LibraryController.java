package com.tcc.areader.controller;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.model.Annotation;
import com.tcc.areader.model.LibraryBook;
import com.tcc.areader.request.AddBookRequest;
import com.tcc.areader.services.LibraryBookService;
import com.tcc.areader.utils.Status;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/library")
public class LibraryController {

  @Autowired
  private LibraryBookService libraryService;

  @PostMapping(value = "/add")
  public ResponseEntity<LibraryBook> addBook(@RequestBody @Valid AddBookRequest addBookRequest)
      throws ClientProtocolException, NotFoundException, IOException {
    return new ResponseEntity<>(libraryService.addBookToLibrary(addBookRequest), HttpStatus.CREATED);
  }

  @PatchMapping("/updateStatus")
  public LibraryBook updateStatus(@RequestParam Long id, @RequestParam Status status) {
    return libraryService.updateStatus(id, status);
  }

  @DeleteMapping("/remove")
  public void removeBook(@RequestParam Long id) {
    libraryService.removeBookFromLibrary(id);
  }

  @GetMapping("/{userEmail}")
  public List<LibraryBook> getLibraryBooksFromUser(@PathVariable String userEmail) {
    return libraryService.getAllLibraryBooksFromUser(userEmail);
  }

  @GetMapping("/annotation/{id}")
  public List<Annotation> getAnnotationsFromLibraryBook(@PathVariable Long id){
    return libraryService.getAnnotationsOfLibraryBook(id);
  }
}