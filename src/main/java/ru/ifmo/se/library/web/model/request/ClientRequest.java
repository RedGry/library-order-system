package ru.ifmo.se.library.web.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class ClientRequest {
    @NotEmpty(message = "fullName cannot be empty")
    private String fullName;

    private LocalDate birthDate;
}
