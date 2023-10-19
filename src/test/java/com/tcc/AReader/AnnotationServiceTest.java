package com.tcc.AReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;

import com.tcc.areader.request.AddAnnotationRequest;
import com.tcc.areader.request.AddBookRequest;
import com.tcc.areader.service.AnnotationService;
import com.tcc.areader.service.LibraryBookService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class AnnotationServiceTest {
    @Autowired
    private AnnotationService annotationService;

    @Autowired
    private LibraryBookService libraryBookService;

    @Test
    @Order(1)
    void addAnnotationTest() throws IOException, NotFoundException {
        var addbookrequest = new AddBookRequest();
        addbookrequest.setIsbn("9788525410979");
        addbookrequest.setUserEmail("test@test.com");
        var libraryBook = libraryBookService.addBookToLibrary(addbookrequest);

        var mockMultipartFile = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE, Files.readAllBytes(Path.of("src/test/resources/test.jpg")));
        var addannotationrequest = new AddAnnotationRequest();
        addannotationrequest.setFile(mockMultipartFile);
        addannotationrequest.setText("Teste");
        addannotationrequest.setLibraryBookId(libraryBook.getId());

        var annotation = annotationService.addAnnotation(addannotationrequest);
        assertEquals(1L, annotation.getId());
        assertEquals(libraryBook.getId(), annotation.getLibraryBook().getId());
    }

    @Test
    @Order(2)
    void getAnnotationsByEmailTest() throws NotFoundException {
        var annotations = annotationService.getAnnotationsByEmail("test@test.com");
        assertEquals(1, annotations.size());
        assertEquals(1L, annotations.get(0).getId());
    }

    @Test
    @Order(3)
    void getAnnotationsByEmailAndIsbnTest() throws NotFoundException {
        var annotations = annotationService.getAnnotationsByEmailAndIsbn("test@test.com","9788525410979");
        assertEquals("9788525410979", annotations.get(0).getBookIsbn());
    }

    @Test
    @Order(4)
    void getAnnotationByIdTest() throws NotFoundException {
        var annotation = annotationService.getAnnotationById(1L);
        assertEquals(1L, annotation.getId());
    }
    @Test
    @Order(5)
    void deleteAnnotationTest() throws NotFoundException {
        assertDoesNotThrow(()-> annotationService.deleteAnnotationById(1L));
    }
}
