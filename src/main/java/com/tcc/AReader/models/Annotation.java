package com.tcc.areader.models;

import jakarta.persistence.Id;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
  private Integer id;
  private String imgUrl;
  private String text;
}