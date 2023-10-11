package com.tcc.areader.requests;

import org.springframework.web.multipart.MultipartFile;

import com.tcc.areader.exceptions.BadRequestException;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddAnnotationRequest {
    @Size(min = 1, max = 240)
    private String text;
    private MultipartFile file;
    @NotNull
    private long libraryBookId;

    @Hidden
    public boolean isValidFile() {

        if (file != null && !file.isEmpty() && file.getContentType().equals("image/jpeg"))
            return true;
        throw new BadRequestException("arquivo file precisa ser image/jpeg");
    }
}
