package ru.ifmo.se.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.library.mapper.BookMapper;
import ru.ifmo.se.library.model.entity.Book;
import ru.ifmo.se.library.repository.api.BookRepository;
import ru.ifmo.se.library.web.model.request.BookUpdateRequest;
import ru.ifmo.se.library.web.model.response.PageResponse;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public PageResponse<Book> findAllWithPagination(Integer page, Integer size) {
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
        if (size > 100) size = 100;

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return new PageResponse<>(
                bookPage.getContent(),
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(Long id, BookUpdateRequest bookUpdateRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + id));
        bookMapper.updateEntityFromRequest(bookUpdateRequest, book);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + id));
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException("Book not found with ISBN: " + isbn));
    }
}
