package ru.ifmo.se.library.mapper;

import org.springframework.stereotype.Component;
import ru.ifmo.se.library.model.entity.Book;
import ru.ifmo.se.library.web.model.request.BookRequest;
import ru.ifmo.se.library.web.model.request.BookUpdateRequest;

@Component
public class BookMapper {

    public Book toEntity(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .isbn(bookRequest.getIsbn())
                .build();
    }

    public void updateEntityFromRequest(BookUpdateRequest bookUpdateRequest, Book book) {
        if (bookUpdateRequest.getTitle() != null) book.setTitle(bookUpdateRequest.getTitle());
        if (bookUpdateRequest.getAuthor() != null) book.setAuthor(bookUpdateRequest.getAuthor());
        if (bookUpdateRequest.getIsbn() != null) book.setIsbn(bookUpdateRequest.getIsbn());
    }
}
