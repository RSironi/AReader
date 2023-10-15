package com.tcc.areader.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "_Group")
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    private String password;
    private String owner;

    @OneToOne
    @JoinColumn(name = "library_book_id")
    private LibraryBook libraryBook;

    @ElementCollection
    private List<String> members;

    @ManyToMany
    @JoinTable(
        name = "group_annotation",
        joinColumns = @JoinColumn(name = "group_id"),
        inverseJoinColumns = @JoinColumn(name = "annotation_id"))
        private List<Annotation> annotations;
}