package ru.ifmo.se.library.mapper;

import org.springframework.stereotype.Component;
import ru.ifmo.se.library.model.entity.Client;
import ru.ifmo.se.library.web.model.request.ClientRequest;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest clientRequest) {
        return Client.builder()
                .fullName(clientRequest.getFullName())
                .birthDate(clientRequest.getBirthDate())
                .build();
    }

    public void updateEntityFromRequest(ClientRequest clientUpdateRequest, Client client) {
        if (clientUpdateRequest.getFullName() != null) client.setFullName(clientUpdateRequest.getFullName());
        if (clientUpdateRequest.getBirthDate() != null) client.setBirthDate(clientUpdateRequest.getBirthDate());
    }
}
