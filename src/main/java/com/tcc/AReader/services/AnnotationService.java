package com.tcc.areader.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.areader.exceptions.AiException;
import com.tcc.areader.exceptions.BadRequestException;
import com.tcc.areader.models.Annotation;
import com.tcc.areader.models.LibraryBook;
import com.tcc.areader.repositories.AnnotationRepository;
import com.tcc.areader.requests.AddAnnotationRequest;

@Service
public class AnnotationService {

        @Autowired
        private AnnotationRepository annotationRepository;
        @Autowired
        private LibraryService libraryService;

        public Annotation addAnnotation(AddAnnotationRequest addannotationrequest) throws IOException {
                Optional<LibraryBook> libraryBook = libraryService.getLibraryBookById(addannotationrequest.getLibraryBookId());
                
                if (!libraryBook.isPresent())
                        throw new BadRequestException("Impossível adicionar anotação sem o livro na biblioteca");

                HttpResponse response = postToAi(addannotationrequest.getFile(), addannotationrequest.getText());

                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
                        throw new AiException("Não aprovado na API", response);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(EntityUtils.toString(response.getEntity()));

                String imgUrl = jsonNode.get("urlAnchor").asText();
                String annotationUrl = jsonNode.get("urlAnnotation").asText();

                String userEmail = libraryBook.get().getUserEmail();
                String bookIsbn = libraryBook.get().getIsbn();

                Annotation anotacao = Annotation.build(null, userEmail, bookIsbn, imgUrl, annotationUrl, null,
                                libraryBook.get());

                return annotationRepository
                                .save(anotacao);
        }

        public HttpResponse postToAi(MultipartFile file, String text) throws IOException {
                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpEntity entity = MultipartEntityBuilder
                                .create()
                                .addBinaryBody("file", file.getBytes(), ContentType.create(file.getContentType()),
                                                file.getOriginalFilename())
                                .addTextBody("text", text)
                                .build();

                HttpPost post = new HttpPost("https://areader-ai-api-zkmzgms3ea-rj.a.run.app");
                post.setEntity(entity);
                HttpResponse response = httpClient.execute(post);
                return response;
        }

        public List<Annotation> getAnnotationsByEmail(String email) {
                List<Annotation> annotations = annotationRepository.findByUserEmail(email);
                if (annotations.isEmpty()) {
                        throw new BadRequestException("Nenhuma anotação encontrada");
                }
                return annotations;
        }

        public List<Annotation> getAnnotationsByEmailAndIsbn(String email, String isbn) {
                List<Annotation> annotations = annotationRepository.findByUserEmailAndBookIsbn(email, isbn);
                if (annotations.isEmpty()) {
                        throw new BadRequestException("Nenhuma anotação encontrada");
                }
                return annotations;
        }

        public void deleteAnnotationById(Long id) {
                annotationRepository.deleteById(id);
        }
}