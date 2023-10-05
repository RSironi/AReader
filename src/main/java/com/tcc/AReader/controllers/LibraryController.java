package com.tcc.areader.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  
  @PostMapping("/addBook")
  public LibraryBook addBook(AddBookRequest addBookRequest) {
    return libraryService.addBook(addBookRequest);
  }

  @PutMapping("/updateStatus")
  public LibraryBook updateStatus(Long id, Status status) {
    return libraryService.updateStatus(id, status);
  }

  @DeleteMapping("/removeBook")
  public void removeBook(Long id) {
    libraryService.removeBook(id);
  }

  @GetMapping("/getBooks")
  public List<LibraryBook> getBooks(String userEmail) {
    return libraryService.getBooks(userEmail);
  }
}
