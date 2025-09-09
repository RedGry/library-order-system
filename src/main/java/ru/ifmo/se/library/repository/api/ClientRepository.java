package ru.ifmo.se.library.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.library.model.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
