package by.integrator.telegrambot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.integrator.telegrambot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByTelegramId(String telegramId);
}
