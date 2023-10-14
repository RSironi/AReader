package com.tcc.areader.services;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.areader.exception.BadRequestException;
import com.tcc.areader.model.Book;
import com.tcc.areader.repository.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private  BookRepository bookRepository;

    public Book getBook(String isbn) throws ClientProtocolException, IOException, NotFoundException {
        Book book = bookRepository.findByIsbn(isbn);
        if(book == null) {
            book = getBookFromApi(isbn);
            return bookRepository.save(book);
        }
        return book;
    }

    public Book getBookFromApi(String isbn) throws ClientProtocolException, IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
        HttpResponse response = httpClient.execute(request);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getEntity().getContent());
        if (jsonNode.path("totalItems").asInt() ==1) {

            JsonNode volumeInfo = jsonNode.path("items").path(0).path("volumeInfo");
            String title = volumeInfo.path("title").asText(null);
            String subtitle = volumeInfo.path("subtitle").asText(null);
            String author = volumeInfo.path("authors").path(0).asText(null);
            String cover = volumeInfo.path("imageLinks").path("thumbnail").asText(null);

            return Book.build(null, isbn, title, subtitle, author, cover);
        }
        throw new BadRequestException("livro não encontrado no Google Books");
    }
}