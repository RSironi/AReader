package com.tcc.areader.models;

import jakarta.persistence.Id ;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookStatus {
    @Id @GeneratedValue
    private Integer Id;
    private Book book;
}