package com.tcc.areader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.areader.model.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, Long>{
            List<Annotation> findByUserEmail(String email);
            List<Annotation> findByUserEmailAndBookIsbn(String email, String isbn);
    
}