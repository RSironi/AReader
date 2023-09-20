package com.tcc.areader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.areader.models.Annotation;

public interface AnnotationRepository extends JpaRepository<Annotation, Long>{
    
}
