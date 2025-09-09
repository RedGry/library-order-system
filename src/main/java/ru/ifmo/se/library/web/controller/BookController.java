package ru.ifmo.se.library.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.library.mapper.BookMapper;
import ru.ifmo.se.library.model.entity.Book;
import ru.ifmo.se.library.service.BookService;
import ru.ifmo.se.library.web.model.request.BookRequest;
import ru.ifmo.se.library.web.model.request.BookUpdateRequest;
import ru.ifmo.se.library.web.model.response.PageResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<PageResponse<Book>> getBooks(
            @Valid @RequestParam(required = false, defaultValue = "0") Integer page,
            @Valid @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        PageResponse<Book> response = bookService.findAllWithPagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@Valid @PathVariable String isbn){
        return ResponseEntity.ok().body(bookService.findByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(bookMapper.toEntity(bookRequest)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(@Valid @PathVariable Long id, @Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        return ResponseEntity.ok().body(bookService.update(id, bookUpdateRequest));
    }
}
