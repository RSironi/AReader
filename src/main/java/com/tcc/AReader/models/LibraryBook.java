package com.tcc.areader.models;

import com.tcc.areader.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  private String userEmail; //se o ID da biblioteca for o email, é possível adicionar uma 
                            //lista de livros que se referem a biblioteca do camarada e após somente 
                            //buscar o livro pela biblioteca dele
  private String isbn;
  private Status status;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;
}