package ru.ifmo.se.library.web.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BorrowRequest {
    @NotNull(message = "clientId cannot be null")
    private Long clientId;

    @NotNull(message = "bookId cannot be null")
    private Long bookId;
}
