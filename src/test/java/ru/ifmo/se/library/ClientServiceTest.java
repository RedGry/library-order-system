package ru.ifmo.se.library;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ifmo.se.library.mapper.ClientMapper;
import ru.ifmo.se.library.model.entity.Client;
import ru.ifmo.se.library.repository.api.ClientRepository;
import ru.ifmo.se.library.service.ClientService;
import ru.ifmo.se.library.web.model.request.ClientRequest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceTest {
    @Mock
    private ClientRepository repository;
    @Mock private ClientMapper mapper;
    @InjectMocks
    private ClientService service;

    ClientServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_shouldReturnClient() {
        Client client = Client.builder().id(1L).fullName("John Doe").build();
        when(repository.findById(1L)).thenReturn(Optional.of(client));

        Client result = service.findById(1L);

        assertThat(result.getFullName()).isEqualTo("John Doe");
    }

    @Test
    void update_shouldModifyAndSaveEntity() {
        Client client = Client.builder().id(1L).fullName("Old").build();
        ClientRequest req = new ClientRequest();
        req.setFullName("New");

        when(repository.findById(1L)).thenReturn(Optional.of(client));
        when(repository.save(client)).thenReturn(client);

        Client result = service.update(1L, req);

        verify(mapper).updateEntityFromRequest(req, client);
        verify(repository).save(client);
        assertThat(result).isNotNull();
    }
}
