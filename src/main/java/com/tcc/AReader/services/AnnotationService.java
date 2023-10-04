package com.tcc.areader.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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

        public int postToAi(MultipartFile file, String text) throws IOException {
                HttpResponse response = executePost(file, text);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));
                        executeSave(jsonNode);
                }
                return response.getStatusLine().getStatusCode();
        }

        public Annotation executeSave(JsonNode jsonresult) {
                Annotation annotation = Annotation.builder()
                                .imgUrl(jsonresult.get("urlAnchor").asText())
                                .annotationUrl(jsonresult.get("urlAnnotation").asText())
                                .userEmail("teste@gmail.com")
                                .bookIsbn("123456789")
                                .build();
                System.out.println(annotation);
                annotationRepository.save(annotation);
                return annotation;
        }

        public HttpResponse executePost(MultipartFile file, String text) throws IOException {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpEntity entity = MultipartEntityBuilder
                                                .create()
                                                .addBinaryBody("file", file.getBytes(), ContentType.create(file.getContentType()),
                                                                                file.getOriginalFilename())
                                                .addTextBody("text", text)
                                                .build();

                HttpPost post = new HttpPost("http://127.0.0.1:80");
                post.setEntity(entity);
                HttpResponse response = httpClient.execute(post);
                return response;
        }

}