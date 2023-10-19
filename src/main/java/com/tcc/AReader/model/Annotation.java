package com.tcc.areader.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
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

  @ManyToMany(mappedBy = "annotations",fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Group> groups = new ArrayList<Group>();

  @PreRemove
  private void removeAnnotationFromGroups(){
    for(Group group : groups){
      group.getAnnotations().remove(this);
    }
  }
}