package com.tcc.areader.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tcc.areader.util.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Entity
@Table(name = "_LibraryBook")
public class LibraryBook {
  @Id
  @GeneratedValue
  private Long id;
  private String userEmail;

  private String isbn;
  private Status status;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  @JsonIgnore
  private Instant createdAt;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  @OneToMany(mappedBy = "libraryBook")
  @Cascade(CascadeType.REMOVE)
  @JsonIgnore
  private List<Annotation> annotations;
}