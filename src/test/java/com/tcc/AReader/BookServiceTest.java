package com.tcc.AReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import com.tcc.areader.service.BookService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    
    @Test
    void getBookTest() throws ClientProtocolException, IOException, NotFoundException{
        var book = bookService.getBook("9788525410979");
        assertEquals("9788525410979", book.getIsbn());
        assertEquals("Discurso do método: ", book.getTitle());
        assertEquals("René Descartes", book.getAuthor());
        assertEquals(1L, book.getId());
    }
    
}
