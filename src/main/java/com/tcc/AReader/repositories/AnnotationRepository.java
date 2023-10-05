package com.tcc.areader.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.areader.models.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, Long>{
            List<Annotation> findByUserEmail(String email);
            List<Annotation> findByUserEmailAndBookIsbn(String email, String isbn);
    
}
