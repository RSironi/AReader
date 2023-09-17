package com.tcc.areader.models;

import jakarta.persistence.Id ;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.Data;

@Table
@Entity
@Data
public class Annotation {

  @Id
  @GeneratedValue
  private Integer idAnnotation;

  private String imgUrlAnnotation;
  
  private String textAnnotation;
} 