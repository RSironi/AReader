package com.tcc.areader.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.areader.models.Annotation;
import com.tcc.areader.requests.AddAnnotationRequest;
import com.tcc.areader.services.AnnotationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/annotations")
@CrossOrigin("http://localhost:8080")
@Validated
public class AnnotationController {

  private final AnnotationService annotationService;

  @GetMapping("/getAnnotationByEmail")
  public List<Annotation> getAnnotationsByEmail(String userEmail) {
    return annotationService.getAnnotationsByEmail(userEmail);
  }

  @GetMapping("/getAnnotationByEmailAndIsbn")
  public List<Annotation> getAnnotationsByEmailAndIsbn(String userEmail, String isbn) {
    return annotationService.getAnnotationsByEmailAndIsbn(userEmail, isbn);
  }

  @DeleteMapping("/deleteAnnotationById")
  public ResponseEntity<?> deleteAnnotationById(Long id) {
    annotationService.deleteAnnotationById(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/addAnnotation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> postAnnotationToAi(@Valid @RequestBody AddAnnotationRequest addannotationRequest)
      throws IOException {
    if (!addannotationRequest.isValid()) {
      return ResponseEntity.badRequest().body("validation error");
    }
    return ResponseEntity.status(annotationService.postToAi(addannotationRequest)).build();
  }
  
}