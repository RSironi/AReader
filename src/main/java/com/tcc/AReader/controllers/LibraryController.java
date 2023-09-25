package com.tcc.areader.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.models.LibraryBook;
import com.tcc.areader.requests.AddBookRequest;
import com.tcc.areader.requests.RemoveBookRequest;
import com.tcc.areader.requests.UpdateStatusRequest;
import com.tcc.areader.services.LibraryService;

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

  @PostMapping("/updateStatus")
  public LibraryBook updateStatus(UpdateStatusRequest updateStatusRequest) {
    return libraryService.updateStatus(updateStatusRequest);
  }

  @PostMapping("/removeBook")
  public void removeBook(RemoveBookRequest removeBookRequest) {
    libraryService.removeBook(removeBookRequest);
  }

  @GetMapping("/getBooks")
  public List<LibraryBook> getBooks(String userEmail) {
    return libraryService.getBooks(userEmail);
  }
}
