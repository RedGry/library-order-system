package ru.ifmo.se.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.library.mapper.ReaderMapper;
import ru.ifmo.se.library.model.dto.ReaderDTO;
import ru.ifmo.se.library.model.entity.BorrowRecord;
import ru.ifmo.se.library.repository.api.BookRepository;
import ru.ifmo.se.library.repository.api.BorrowRecordRepository;
import ru.ifmo.se.library.repository.api.ClientRepository;
import ru.ifmo.se.library.web.model.response.PageResponse;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;
    private final ReaderMapper readerMapper;

    @Transactional
    public BorrowRecord borrow(Long clientId, Long bookId) {
        return borrowRecordRepository.save(
                BorrowRecord.builder()
                        .client(clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId)))
                        .book(bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId)))
                        .takenAt(LocalDateTime.now())
                        .build()
        );
    }

    public PageResponse<ReaderDTO> findAllWithPagination(Integer page, Integer size) {
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
        if (size > 100) size = 100;

        Pageable pageable = PageRequest.of(page, size);
        Page<BorrowRecord> borrowRecordsPage = borrowRecordRepository.findAll(pageable);

        List<ReaderDTO> readerDTOList = readerMapper.fromEntityList(borrowRecordsPage.getContent());

        return new PageResponse<>(
                readerDTOList,
                borrowRecordsPage.getNumber(),
                borrowRecordsPage.getSize(),
                borrowRecordsPage.getTotalElements(),
                borrowRecordsPage.getTotalPages()
        );
    }

    public PageResponse<ReaderDTO> findByIsbnWithPagination(String isbn, Integer page, Integer size) {
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
        if (size > 100) size = 100;

        Pageable pageable = PageRequest.of(page, size);
        Page<BorrowRecord> borrowRecordsPage = borrowRecordRepository.findByBookIsbn(isbn, pageable);

        List<ReaderDTO> readerDTOList = readerMapper.fromEntityList(borrowRecordsPage.getContent());

        return new PageResponse<>(
                readerDTOList,
                borrowRecordsPage.getNumber(),
                borrowRecordsPage.getSize(),
                borrowRecordsPage.getTotalElements(),
                borrowRecordsPage.getTotalPages()
        );
    }
}
