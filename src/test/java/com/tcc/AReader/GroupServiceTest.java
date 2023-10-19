package com.tcc.AReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.http.client.ClientProtocolException;
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
import org.springframework.transaction.annotation.Transactional;

import com.tcc.areader.exception.BadRequestException;
import com.tcc.areader.request.AddAnnotationRequest;
import com.tcc.areader.request.AddBookRequest;
import com.tcc.areader.service.AnnotationService;
import com.tcc.areader.service.GroupService;
import com.tcc.areader.service.LibraryBookService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private LibraryBookService libraryBookService;

    @Autowired
    private AnnotationService annotationService;

    @Test
    @Order(1)
    void createGroupTest() throws ClientProtocolException, NotFoundException, IOException {
        var addbookrequest = new AddBookRequest();
        addbookrequest.setIsbn("9788525410979");
        addbookrequest.setUserEmail("test@test.com");
        var libraryBook = libraryBookService.addBookToLibrary(addbookrequest);

        var mockMultipartFile = new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG_VALUE,
        Files.readAllBytes(Path.of("src/test/resources/test.jpg")));
        var addannotationrequest = new AddAnnotationRequest();
        addannotationrequest.setFile(mockMultipartFile);
        addannotationrequest.setText("Teste");
        addannotationrequest.setLibraryBookId(1L);
        annotationService.addAnnotation(addannotationrequest);

        var group = groupService.CreateGroup(libraryBook.getId(), "123");
        assertEquals(1L, group.getId());
        assertEquals("123", group.getPassword());
    }

    @Test
    @Order(2)
    void getGroupsByOwnerTest() {
        var groups = groupService.getGroupsByOwner("test@test.com");

        assertEquals(1, groups.size());
        assertEquals(1L, groups.get(0).getId());
    }

    @Test
    @Order(3)
    void getGroupByIdTest() {
        var group = groupService.getGroupById(1L);

        assertEquals(1L, group.get().getId());
    }

    @Test
    @Order(4)
    void addAnnotationToGroupTest() throws IOException {
        var group = groupService.addAnnotationToGroup(1L, 1L);

        assertEquals(1L, group.getAnnotations().get(0).getId());
        assertEquals(1, group.getAnnotations().size());
    }

    @Test
    @Order(5)
    @Transactional
    void removeAnnotationFromGroupTest() {
        var group = groupService.removeAnnotationFromGroup(1L, 1L);

        assertEquals(0, group.getAnnotations().size());
    }

    @Test
    @Order(6)
    void joinGroupTest() {
        var group = groupService.joinGroup(1L, "123", "joinMemberTest");

        assertEquals(1, group.getMembers().size());
        assertEquals("joinMemberTest", group.getMembers().get(0));
    }

    @Test
    @Order(7)
    void leaveGroupTest() {
        var group = groupService.leaveGroup(1L, "joinMemberTest");

        assertEquals(0, group.getMembers().size());
    }

    @Test
    @Order(8)
    void getGroupImMemberTest() {
        assertThrows(BadRequestException.class, () -> groupService.getGroupImMember("test"));

    }

    @Test
    @Order(9)
    void getGroupImMemberAndIsbnTest() {
        assertThrows(BadRequestException.class, () -> groupService.getGroupImMemberAndIsbn("test","9788525410979"));
    }
}