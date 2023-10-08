package com.tcc.areader.services;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.areader.models.Book;
import com.tcc.areader.repositories.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;

    public Book getBook(String isbn) throws NotFoundException, ClientProtocolException, IOException {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        } else {
            return addBook(isbn);
        }
    }

    public Book addBook(String isbn) throws ClientProtocolException, IOException, NotFoundException {
        Optional<Book> livro = getBookForApi(isbn);
        if (livro.isEmpty()) {
            return null;
        }else
        return bookRepository.save(livro.get());
    }
    
    public Optional<Book> getBookForApi(String isbn) throws ClientProtocolException, IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://openlibrary.org/isbn/" + isbn + ".json");
        HttpResponse response = httpClient.execute(request);
        
        if (response.getStatusLine().getStatusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
            return Optional.of(Book.builder()
                    .isbn(isbn)
                    .title(jsonNode.get("title").asText())
                    .subtitle(jsonNode.get("subtitle").asText())
                    //.author(jsonNode.get("author").asText())
                    .cover("http://covers.openlibrary.org/b/isbn/" + isbn + "-M.jpg")
                    .build());
        }
        return Optional.empty();
    }
}