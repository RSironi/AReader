package com.tcc.areader.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.models.Annotation;
import com.tcc.areader.requests.AnnotationRequest;
import com.tcc.areader.services.AnnotationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/annotations")
@CrossOrigin("http://localhost:8080")
public class AnnotationController {
  private final AnnotationService service;

  @GetMapping("/hello")
  public String hello() {
    return "Hello World Annotation";
  }

  @PostMapping(value = "", consumes = { "multipart/form-data" })
  public ResponseEntity<Annotation> createAnnotation(@ModelAttribute AnnotationRequest request, BindingResult result)
      throws IOException {
    return ResponseEntity.ok((service.save(request, result)));
  }

  @PostMapping("/test")
  public ResponseEntity<String> testPostRequest() {
    return ResponseEntity.ok("POST request successful");
  }
}