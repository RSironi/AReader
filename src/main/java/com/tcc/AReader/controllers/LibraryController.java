package com.tcc.areader.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.models.LibraryBook;
import com.tcc.areader.requests.AddBookRequest;
import com.tcc.areader.services.LibraryService;
import com.tcc.areader.utils.Status;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@CrossOrigin("http://localhost:8080")
public class LibraryController {
  private final LibraryService libraryService;
  
  @PostMapping(value = "/addBook")
  @ResponseBody
  public ResponseEntity<?> addBook(AddBookRequest addBookRequest) throws ClientProtocolException, NotFoundException, IOException {
    return libraryService.addBookToLibrary(addBookRequest);
  }

  @PatchMapping("/updateStatus")
  public LibraryBook updateStatus(Long id, Status status) {
    return libraryService.updateStatus(id, status);
  }

  @DeleteMapping("/removeBook")
  public void removeBook(Long id) {
    libraryService.removeBookFromLibrary(id);
  }

  @GetMapping("/getBooks")
  public List<LibraryBook> getBooks(String userEmail) {
    return libraryService.getBooks(userEmail);
  }
}