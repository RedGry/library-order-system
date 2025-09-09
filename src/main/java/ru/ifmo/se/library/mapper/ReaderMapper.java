package ru.ifmo.se.library.mapper;

import org.springframework.stereotype.Component;
import ru.ifmo.se.library.model.dto.ReaderDTO;
import ru.ifmo.se.library.model.entity.BorrowRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    public ReaderDTO fromEntity(BorrowRecord borrowRecord) {
        if (borrowRecord == null) {
            return null;
        }

        return ReaderDTO.builder()
                .fullName(borrowRecord.getClient().getFullName())
                .birthDate(formatDate(borrowRecord.getClient().getBirthDate()))
                .bookTitle(borrowRecord.getBook().getTitle())
                .bookAuthor(borrowRecord.getBook().getAuthor())
                .isbn(borrowRecord.getBook().getIsbn())
                .takenAt(formatDateTime(borrowRecord.getTakenAt()))
                .build();
    }

    public List<ReaderDTO> fromEntityList(List<BorrowRecord> borrowRecordList) {
        if (borrowRecordList == null || borrowRecordList.isEmpty()) {
            return Collections.emptyList();
        }

        return borrowRecordList.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
