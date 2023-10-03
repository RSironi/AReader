package com.tcc.areader.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.areader.models.Annotation;
import com.tcc.areader.repositories.AnnotationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnnotationService {

    private final AnnotationRepository annotationRepository;

public Annotation save2(HttpResponse result) throws ParseException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(result.getEntity()));

        if (result.getStatusLine().getStatusCode() == 200) {
                Annotation annotation = Annotation.builder()
                                .imgUrl(jsonNode.get("urlAncora").asText())
                                .annotationUrl(jsonNode.get("urlAnotacao").asText())
                                .userEmail("teste@gmail.com")
                                .bookIsbn("123456789")
                                .build();
                annotationRepository.save(annotation);
                return annotation;
        }

        return null;
}

    public void postToAi(MultipartFile file, String text) 
        throws IOException{ 
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("file", file.getBytes(), ContentType.IMAGE_JPEG, file.getOriginalFilename())
                .addTextBody("text", text)
                .build();
        
        HttpPost post = new HttpPost("http://127.0.0.1:80");
        post.setEntity(entity);
        HttpResponse response = httpClient.execute(post);

        System.out.println("\n => AI_API status response: " + response.getStatusLine().getStatusCode() + " "
                + response.getStatusLine().getReasonPhrase());
        save2(response);
    }


}