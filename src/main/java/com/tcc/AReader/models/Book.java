package com.tcc.areader.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Entity
@Table(name = "_Book")
public class Book {
  @Id
  @GeneratedValue
  private Long id;
  private String isbn;
  private String title;
  private String subtitle;
  private String author;
  private String cover;
  private String url;
}