package ru.ifmo.se.library;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.ifmo.se.library.mapper.BookMapper;
import ru.ifmo.se.library.model.entity.Book;
import ru.ifmo.se.library.web.model.request.BookRequest;
import ru.ifmo.se.library.web.model.request.BookUpdateRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
class BookMapperTest {
    private final BookMapper mapper = new BookMapper();

    @Test
    void toEntity_shouldMapCorrectly() {
        BookRequest request = new BookRequest();
        request.setTitle("Title");
        request.setAuthor("Author");
        request.setIsbn("978-0-06-112008-4");

        Book book = mapper.toEntity(request);

        assertThat(book.getTitle()).isEqualTo("Title");
        assertThat(book.getAuthor()).isEqualTo("Author");
        assertThat(book.getIsbn()).isEqualTo("978-0-06-112008-4");
    }

    @Test
    void updateEntityFromRequest_shouldUpdateNonNullFields() {
        Book book = Book.builder().title("Old").author("Old").isbn("978-0-06-112008-4").build();
        BookUpdateRequest update = new BookUpdateRequest();
        update.setTitle("New");
        update.setIsbn("0-596-52068-9");

        mapper.updateEntityFromRequest(update, book);

        assertThat(book.getTitle()).isEqualTo("New");
        assertThat(book.getAuthor()).isEqualTo("Old"); // не менялось
        assertThat(book.getIsbn()).isEqualTo("0-596-52068-9");
    }
}
