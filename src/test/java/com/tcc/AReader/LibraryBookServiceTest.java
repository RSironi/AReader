package com.tcc.AReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import com.tcc.areader.exception.BadRequestException;
import com.tcc.areader.request.AddBookRequest;
import com.tcc.areader.service.LibraryBookService;
import com.tcc.areader.util.Status;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class LibraryBookServiceTest {
    @Autowired
    private LibraryBookService libraryService;

    @Test
    @Order(1)
    void addBookToLibraryTest() {
        var addbookrequest = new AddBookRequest();
        addbookrequest.setIsbn("9788525410979");
        addbookrequest.setUserEmail("test@test.com");
        try {
            var result = libraryService.addBookToLibrary(addbookrequest);
            assertEquals(result.getIsbn(), addbookrequest.getIsbn());
            assertEquals(1, result.getBook().getId());

            assertThrows(BadRequestException.class, ()-> addBookToLibraryTest());
        } catch (NotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    @Order(2)
    void libraryBookExistsTest() {
        var result = libraryService.libraryBookExists("9788525410979", "test@test.com");
        assertTrue(result.isPresent());
        assertEquals("9788525410979", result.get().getIsbn());
        assertEquals("test@test.com", result.get().getUserEmail());
        assertThrows(BadRequestException.class, ()-> addBookToLibraryTest());
    }

    @Test
    @Order(3)
    void updateStatusTest() {
        var result = libraryService.updateStatus(1L, Status.READING);
        assertEquals(Status.READING, result.getStatus());

        assertThrows(BadRequestException.class, ()-> libraryService.updateStatus(0L, Status.READING));
    }

    @Test
    @Order(4)
    void getAllLibraryBooksFromUserTest() {
        var result = libraryService.getAllLibraryBooksFromUser("test@test.com");
        assertEquals(1, result.size());
        assertEquals("test@test.com", result.get(0).getUserEmail());
    }

    @Test
    @Order(5)
    void getLibraryBookByIdTest() {
        var result = libraryService.getLibraryBookById(1L);
        assertEquals(1L, result.get().getId());

        assertThrows(BadRequestException.class, ()-> libraryService.getLibraryBookById(0L));

    }

    @Test
    @Order(6)
    void getAnnotationsOfLibraryBookTest() {
        assertThrows(BadRequestException.class, ()-> libraryService.getAnnotationsOfLibraryBook(0L));
    }

    @Test
    @Order(7)
    void removeBookFromLibraryTest() {
        assertDoesNotThrow(()-> libraryService.removeBookFromLibrary(1L));
    }
}