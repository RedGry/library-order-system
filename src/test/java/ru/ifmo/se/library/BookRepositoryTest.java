package ru.ifmo.se.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.ifmo.se.library.model.entity.Book;
import ru.ifmo.se.library.repository.api.BookRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldFindByIsbn() {
        Book book = Book.builder().title("Test").author("Author").isbn("0-596-52068-9").build();
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByIsbn("0-596-52068-9");

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test");
    }
}
