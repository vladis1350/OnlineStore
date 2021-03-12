package by.integrator.telegrambot.bot;

import java.util.Optional;

import by.integrator.telegrambot.EncryptService;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import by.integrator.telegrambot.bot.handler.client.ClientUpdateHandler;
import by.integrator.telegrambot.model.User;
import by.integrator.telegrambot.model.enums.Role;
import by.integrator.telegrambot.service.UserService;
import lombok.Getter;

@Component
@Configuration
@Setter
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LogManager.getLogger(TelegramBot.class);

    @Autowired private EncryptService encryptService;

    @Value("${telegram.botToken}")
    private String botToken;

    @Value("${telegram.botUsername}")
    private String botUsername;

    @Autowired private UserService userService;
    @Autowired private ClientUpdateHandler clientUpdateHandler;

    @Override
    public void onUpdateReceived(Update update) {
        final Optional<User> user = userService.getByTelegramId(this.getTelegramId(update));

        if (user.isPresent()) {
            processUpdate(user.get(), update);
        }
        else {
            register(update);
        }
    }

    private String getTelegramId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        }
        else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId().toString();
        }
        return null;
    }

    private void processUpdate(User user, Update update) {
        try {
            switch(user.getRole()) {
                case CLIENT:
                    clientUpdateHandler.handle(update);
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void register(Update update) {
        LOGGER.info("New user registered: " + this.getTelegramId(update));
        User user = userService.createUser(update, Role.parseRole(update.getMessage().getText()));
        processUpdate(user, update);
    }

    @Override
    public String getBotUsername() {
        return encryptService.decryptPass(botUsername);
    }

    @Override
    public String getBotToken() {
        return encryptService.decryptPass(botToken);
    }
}
