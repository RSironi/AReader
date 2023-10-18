package com.tcc.AReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.tcc.areader.request.AddBookRequest;
import com.tcc.areader.service.LibraryBookService;

@SpringBootTest
class AReaderApplicationTests {

	@Autowired
	private LibraryBookService libraryService;

	@Test
	void addBookToLibraryTest() throws ClientProtocolException, NotFoundException, IOException {
		var addbookrequest = new AddBookRequest();
		addbookrequest.setIsbn("9788525410979");
		addbookrequest.setUserEmail("test@test.com");

		var result = libraryService.addBookToLibrary(addbookrequest);
		assertEquals(result.getIsbn(), addbookrequest.getIsbn());
		assertEquals(1,result.getBook().getId());
	}

}