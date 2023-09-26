package com.tcc.areader.services;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.tcc.areader.models.Annotation;
import com.tcc.areader.repositories.AnnotationRepository;
import com.tcc.areader.requests.AnnotationRequest;
import com.tcc.areader.utils.FileValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnnotationService {

    private final AnnotationRepository annotationRepository;

    public int save(AnnotationRequest request, BindingResult result)
            throws IOException {
        int returnStatusCode = postToAiStatusCode(request);

        if (returnStatusCode == 200) {
            uploadFile(request, result);

            annotationRepository.save(
                    Annotation.builder()
                            .imgUrl(request.getImgUrl())
                            .text(request.getText())
                            .userEmail(request.getUserEmail())
                            .bookIsbn(request.getBookIsbn())
                            .page(request.getPage())
                            .annotationUrl("")
                            .build());

            System.out.println("\n => Saved in DB \n => Find All: " + annotationRepository.findAll()); // Retirar depois
        }
        return returnStatusCode;
    }

    public int postToAiStatusCode(AnnotationRequest request)
            throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("file", request.getFile().getBytes(), ContentType.IMAGE_JPEG,
                        request.getFile().getOriginalFilename())
                .build();

        HttpPost post = new HttpPost("https://areader-ai-api-zkmzgms3ea-rj.a.run.app");
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);

        System.out.println("\n => AI_API status response: " + response.getStatusLine().getStatusCode() + " "
                + response.getStatusLine().getReasonPhrase());

        System.out.println("\n => AI_API body response: " + EntityUtils.toString(response.getEntity()));

        return response.getStatusLine().getStatusCode();
    }

    @SuppressWarnings("rawtypes")
    public AnnotationRequest uploadFile(AnnotationRequest request, BindingResult result)
            throws IOException {
        FileValidator validator = new FileValidator();
        validator.validate(request, result);

        Map uploadResult = Singleton.getCloudinary().uploader().upload(request.getFile().getBytes(),
                ObjectUtils.asMap("resource_type", "auto"));

        System.out.println("\n => Upload file into " + uploadResult.get("url").toString());
        request.setImgUrl(uploadResult.get("url").toString());
        return request;
    }

}