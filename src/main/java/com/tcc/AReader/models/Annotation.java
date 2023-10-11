package com.tcc.areader.models;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Entity
@Table(name = "_Annotation")
public class Annotation {
  @Id
  @GeneratedValue
  private Long id;
  private String userEmail;
  private String bookIsbn;
  private String imgUrl;
  private String annotationUrl;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  @JsonIgnore
  private Instant createdAt;

  @ManyToOne
  @JoinColumn(name = "library_book_id")
  @JsonIgnore
  private LibraryBook libraryBook;
}