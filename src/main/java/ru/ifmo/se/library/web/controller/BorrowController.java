package ru.ifmo.se.library.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.library.service.BorrowService;
import ru.ifmo.se.library.web.model.request.BorrowRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<?> borrowBook(@Valid @RequestBody BorrowRequest borrowRequest) {
        return ResponseEntity.ok(borrowService.borrow(borrowRequest.getClientId(), borrowRequest.getBookId()));
    }
}
