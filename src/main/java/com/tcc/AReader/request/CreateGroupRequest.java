package com.tcc.areader.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateGroupRequest {

    private Long libraryBookId;

    @NotEmpty(message = "Senha não pode ser vazia")
    private String password;
}
