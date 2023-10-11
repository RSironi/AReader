package com.tcc.areader.requests;

import org.hibernate.validator.constraints.ISBN;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class AddBookRequest {
  @Email(message = "Email inválido")
  @NotEmpty(message = "Email não pode ser vazio")
  private String userEmail;
  @ISBN(message = "ISBN inválido", type = ISBN.Type.ANY)
  private String isbn;
}