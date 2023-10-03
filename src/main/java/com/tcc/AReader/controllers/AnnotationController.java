package com.tcc.areader.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.areader.services.AnnotationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/annotations")
@CrossOrigin("http://localhost:8080")
public class AnnotationController {

  private final AnnotationService annotationService;

  @GetMapping("")
  public String hello() {
    return "Hello World Annotation";
  }

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
  public void getFileInfo(@RequestParam("file") MultipartFile file, @RequestParam("text") String text) throws IOException {
    System.out.println("recebi o post:" + file.getOriginalFilename() + " " + text);
    annotationService.postToAi(file, text);
    // Seu código aqui
  }
}