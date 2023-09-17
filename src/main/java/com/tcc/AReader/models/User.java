package com.tcc.areader.models;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.Builder;

@Data
@Entity
@Builder
public class User {
    @Id @GeneratedValue
    private Integer id;
    private String userEmail;
    private boolean isAdmin;
}
