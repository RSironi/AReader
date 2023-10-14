package com.tcc.areader.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.model.Annotation;
import com.tcc.areader.request.AddAnnotationRequest;
import com.tcc.areader.service.AnnotationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/annotation")
public class AnnotationController {
  @Autowired
  private AnnotationService annotationService;

  @GetMapping("{userEmail}")
  public List<Annotation> getAnnotationsByEmail(@PathVariable String userEmail) {
    return annotationService.getAnnotationsByEmail(userEmail);
  }

  @GetMapping("/{userEmail}/{isbn}")
  public List<Annotation> getAnnotationsByEmailAndIsbn(@PathVariable String userEmail, @PathVariable String isbn) {
    return annotationService.getAnnotationsByEmailAndIsbn(userEmail, isbn);
  }

  @DeleteMapping("/remove")
  public void deleteAnnotationById(@RequestParam Long id) {
    annotationService.deleteAnnotationById(id);
  }

  @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Annotation> postAnnotationToAi(@Valid @RequestBody AddAnnotationRequest addannotationRequest)
      throws IOException {
    addannotationRequest.isValidFile();
    return new ResponseEntity<>(annotationService.addAnnotation(addannotationRequest), HttpStatus.CREATED);
  }
}