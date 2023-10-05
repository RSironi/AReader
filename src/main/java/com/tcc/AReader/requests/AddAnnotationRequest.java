package com.tcc.areader.requests;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddAnnotationRequest {
    @Size(min = 1, max = 240)
    private String text;
    @Email()
    private String email;
    private String isbn;
    private MultipartFile file;

    @Hidden
    public boolean isValid() {
        return text != null && !text.isEmpty() && email != null && !email.isEmpty() && isbn != null && !isbn.isEmpty()
                && file != null && !file.isEmpty() && file.getContentType().equals("image/jpeg");
    }
}
