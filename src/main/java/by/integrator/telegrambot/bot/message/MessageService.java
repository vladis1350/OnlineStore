package by.integrator.telegrambot.bot.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import by.integrator.telegrambot.model.User;
import by.integrator.telegrambot.service.UserService;

@Service
public class MessageService {
    
    @Autowired private UserService userService;

    protected void updateLastBotMessage(User user, Message message) {
        user.setLastBotMessageId(message.getMessageId());
        userService.save(user);
    }

}
