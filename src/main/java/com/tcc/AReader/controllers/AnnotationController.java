package com.tcc.areader.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.models.Annotation;
import com.tcc.areader.requests.*;
import com.tcc.areader.services.AnnotationService;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/annotations")
public class AnnotationController {
  protected AnnotationService service;

  // hello world
  @GetMapping("/hello")
  public String hello() {
    return "Hello World Annotation";
  }

  @PostMapping(value = "/post", consumes = { "multipart/form-data" })
  public ResponseEntity<Annotation> createAnnotation(@ModelAttribute AnnotationRequest request, BindingResult result) 
  throws IOException {
    return ResponseEntity.ok((service.save(request, result)));
  }
}