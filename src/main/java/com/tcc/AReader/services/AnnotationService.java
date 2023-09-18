package com.tcc.areader.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.tcc.areader.models.Annotation;
import com.tcc.areader.requests.AnnotationRequest;
import com.tcc.areader.utils.FileValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnotationService {
    public Annotation save(AnnotationRequest request, BindingResult result) throws IOException {
        // TODO: Enviar para a IA
        // ...

        // Dependendo resposta da IA
        // Salva imagem na cloudnary e dados no banco
        System.out.println(" => Saving annotation: " + request.getText());
        uploadFile(request, result);

        return Annotation.builder()
                .imgUrl(request.getImgUrl())
                .text(request.getText())
                .build();
    }

    public void postAnnotationToAI(String token) {

    }

    @SuppressWarnings("rawtypes")
    public AnnotationRequest uploadFile(AnnotationRequest request, BindingResult result)
            throws IOException {
        FileValidator validator = new FileValidator();
        validator.validate(request, result);

        Map uploadResult = Singleton.getCloudinary().uploader().upload(request.getFile().getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        System.out.println(" => Upload file into " + uploadResult.get("url").toString());
        request.setImgUrl(uploadResult.get("url").toString());
        return request;
    }

}
