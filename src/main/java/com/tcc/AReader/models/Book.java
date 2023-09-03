package com.tcc.areader.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  public String isbn;
}