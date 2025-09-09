package ru.ifmo.se.library.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.library.model.dto.ReaderDTO;
import ru.ifmo.se.library.service.BorrowService;
import ru.ifmo.se.library.web.model.response.PageResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/readers")
@RequiredArgsConstructor
public class ReaderController {
    private final BorrowService borrowService;

    @GetMapping
    public ResponseEntity<PageResponse<ReaderDTO>> getBooks(
            @Valid @RequestParam(required = false, defaultValue = "0") Integer page,
            @Valid @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String isbn
    ) {
        PageResponse<ReaderDTO> response;

        if (isbn != null && !isbn.isEmpty()) {
            response = borrowService.findByIsbnWithPagination(isbn, page, size);
        } else {
            response = borrowService.findAllWithPagination(page, size);
        }

        return ResponseEntity.ok().body(response);
    }
}
