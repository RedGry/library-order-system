package ru.ifmo.se.library.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.library.mapper.ClientMapper;
import ru.ifmo.se.library.model.entity.Client;
import ru.ifmo.se.library.service.ClientService;
import ru.ifmo.se.library.web.model.request.ClientRequest;
import ru.ifmo.se.library.web.model.response.PageResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public ResponseEntity<PageResponse<Client>> getClients(
            @Valid @RequestParam(required = false, defaultValue = "0") Integer page,
            @Valid @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        PageResponse<Client> response = clientService.findAllWithPagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientRequest clientRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(clientMapper.toEntity(clientRequest)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @PathVariable Long id, @Valid @RequestBody ClientRequest clientUpdateRequest) {
        return ResponseEntity.ok().body(clientService.update(id, clientUpdateRequest));
    }
}
