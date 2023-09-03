package com.tcc.areader.models;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue
    private Integer id;
    private String userEmail;
    private boolean isAdmin;
}
