package by.integrator.telegrambot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.integrator.telegrambot.model.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
}
