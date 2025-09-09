package ru.ifmo.se.library.web.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotEmpty;

@Data
public class BookUpdateRequest {
    private String title;
    private String author;

    @NotEmpty
    @ISBN(message = "Invalid ISBN format", type = ISBN.Type.ANY)
    @Schema(
            description = "International Standard Book Number. Can be ISBN-10 or ISBN-13 format",
            example = "978-0-06-112008-4",
            examples = {"978-0-06-112008-4", "0-596-52068-9"}
    )
    private String isbn;
}
