package ru.ifmo.se.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderDTO {
    private String fullName;
    private String birthDate;
    private String bookTitle;
    private String bookAuthor;
    private String isbn;
    private String takenAt;
}
