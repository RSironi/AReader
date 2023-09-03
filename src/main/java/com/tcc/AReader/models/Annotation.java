package com.tcc.areader.models;

import org.springframework.data.annotation.Id;
import jakarta.persistence.*;

public class Annotation {
  @Id @GeneratedValue 
  private Integer id;
  @OneToMany
  private Book book;
  
  private User user;
  private String imgUrl;
  // private Group group;
}