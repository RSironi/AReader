package com.tcc.areader.models;

import com.tcc.areader.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "_LibraryBook")
public class LibraryBook {
  @Id
  @GeneratedValue
  private Long id;
  private String userEmail;
  private String isbn;
  private Status status;
  @OneToOne
  private Book book;
}
