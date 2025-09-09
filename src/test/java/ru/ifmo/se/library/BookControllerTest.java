package ru.ifmo.se.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.ifmo.se.library.mapper.BookMapper;
import ru.ifmo.se.library.model.entity.Book;
import ru.ifmo.se.library.service.BookService;
import ru.ifmo.se.library.util.ResponseUtils;
import ru.ifmo.se.library.web.controller.BookController;
import ru.ifmo.se.library.web.model.request.BookRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(BookController.class)
@Import(ResponseUtils.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;
    @MockBean private BookMapper bookMapper;

    @Test
    void createBook_shouldReturnCreated() throws Exception {
        BookRequest request = new BookRequest();
        request.setTitle("Title");
        request.setAuthor("Author");
        request.setIsbn("978-0-06-112008-4");

        Book book = Book.builder().id(1L).title("Title").author("Author").isbn("978-0-06-112008-4").build();

        when(bookMapper.toEntity(any())).thenReturn(book);
        when(bookService.save(any())).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title"));
    }
}
