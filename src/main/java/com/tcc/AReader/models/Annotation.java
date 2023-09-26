package com.tcc.areader.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Annotation")
public class Annotation {
  @Id
  @GeneratedValue
  private Long id;
  private String imgUrl;
  private String text;
  private String userEmail;
  private String bookIsbn;

  @Nullable
  private Integer page;
  private String annotationUrl;
}