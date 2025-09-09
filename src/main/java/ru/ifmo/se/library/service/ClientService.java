package ru.ifmo.se.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ifmo.se.library.mapper.ClientMapper;
import ru.ifmo.se.library.model.entity.Client;
import ru.ifmo.se.library.repository.api.ClientRepository;
import ru.ifmo.se.library.web.model.request.ClientRequest;
import ru.ifmo.se.library.web.model.response.PageResponse;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public PageResponse<Client> findAllWithPagination(Integer page, Integer size) {
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
        if (size > 100) size = 100;

        Pageable pageable = PageRequest.of(page, size);
        Page<Client> bookPage = clientRepository.findAll(pageable);

        return new PageResponse<>(
                bookPage.getContent(),
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }

    @Transactional
    public Client save(Client client){
        return clientRepository.save(client);
    }

    @Transactional
    public Client update(Long id, ClientRequest clientUpdateRequest){
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
        clientMapper.updateEntityFromRequest(clientUpdateRequest, client);
        return clientRepository.save(client);
    }

    public Client findById(Long id){
        return clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
