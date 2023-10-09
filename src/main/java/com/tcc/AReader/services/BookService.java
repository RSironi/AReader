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
        } else
            return bookRepository.save(livro.get());
    }

    public Optional<Book> getBookForApi(String isbn) throws ClientProtocolException, IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
        HttpResponse response = httpClient.execute(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
        if (jsonNode.path("totalItems").asInt() >0) {

            JsonNode volumeInfo = jsonNode.path("items").path(0).path("volumeInfo");
            String title = volumeInfo.path("title").asText(null);
            String subtitle = volumeInfo.path("subtitle").asText(null);
            String author = volumeInfo.path("authors").path(0).asText(null);
            String cover = volumeInfo.path("imageLinks").path("thumbnail").asText(null);

            return Optional.of(Book.builder()
                    .isbn(isbn)
                    .title(title)
                    .subtitle(subtitle)
                    .author(author)
                    .cover(cover)
                    .build());
        }
        return Optional.empty();
    }
}