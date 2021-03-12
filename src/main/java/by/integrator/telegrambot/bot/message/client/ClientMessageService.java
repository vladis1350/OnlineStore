package by.integrator.telegrambot.bot.message.client;

import by.integrator.telegrambot.bot.keyboard.client.ClientInlineKeyboardMarkupSource;
import by.integrator.telegrambot.model.Brand;
import by.integrator.telegrambot.model.enums.BrandList;
import by.integrator.telegrambot.service.BrandService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import by.integrator.telegrambot.bot.api.enums.UpdateType;
import by.integrator.telegrambot.bot.context.client.ClientBotContext;
import by.integrator.telegrambot.bot.keyboard.client.ClientReplyKeyboardMarkupSource;
import by.integrator.telegrambot.bot.message.MessageService;
import by.integrator.telegrambot.bot.api.MessageSender;
import by.integrator.telegrambot.exception.ClientNotFoundException;
import by.integrator.telegrambot.model.Client;
import by.integrator.telegrambot.util.BotUtils;

import java.util.List;

@Service
public class ClientMessageService extends MessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientMessageService.class);

    @Autowired
    private BotUtils botUtils;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private ClientMessageSource clientMessageSource;
    @Autowired
    private ClientReplyKeyboardMarkupSource clientReplyKeyboardMarkupSource;
    @Autowired
    private ClientInlineKeyboardMarkupSource clientInlineKeyboardMarkupSource;
    @Autowired
    private BrandService brandService;

    @SneakyThrows
    private Boolean checkCallbackQuery(ClientBotContext clientBotContext) {
        return botUtils.getUpdateType(clientBotContext.getUpdate()).equals(UpdateType.CALLBACK_QUERY) &&
                !clientBotContext.getUpdate().getMessage().getReplyMarkup().equals(new InlineKeyboardMarkup());

    }

    public void sendStartMessage(ClientBotContext clientBotContext) throws ClientNotFoundException {
        Client client = clientBotContext.getClient();
        if (client == null) throw new ClientNotFoundException();

        if (checkCallbackQuery(clientBotContext)) {

        } else {
            try {
                Message message = messageSender.sendMessage(client.getTelegramId(), clientMessageSource.getMessage("message.start", client.getUser().getFirstname()), null);

                updateLastBotMessage(client.getUser(), message);
            } catch (TelegramApiException ex) {
                LOGGER.error("Unable to send start message to user: {0}, reason: {1}", client.getTelegramId(), ex.getLocalizedMessage());
            }
        }
    }

    public void sendShareContact(ClientBotContext clientBotContext) throws ClientNotFoundException {
        Client client = clientBotContext.getClient();
        if (client == null) throw new ClientNotFoundException();

        try {
            Message message = messageSender.sendMessage(client.getTelegramId(),
                    clientMessageSource.getMessage("message.shareContact"), clientReplyKeyboardMarkupSource.getContact());

            updateLastBotMessage(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send start message to user: {0}, reason: {1}", client.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendClientMainMenu(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        try {
            Message message = messageSender.sendMessage(client.getTelegramId(),
                    clientMessageSource.getMessage("message.mainMenu"), clientReplyKeyboardMarkupSource.getClientMainMenu());

            updateLastBotMessage(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send start message to user: {0}, reason: {1}", client.getTelegramId(), ex.getLocalizedMessage());
        }
    }

    public void sendBrandList(ClientBotContext clientBotContext) {
        Client client = clientBotContext.getClient();

        List<Brand> brands = brandService.getAll();

        try {
            Message message = messageSender.sendMessage(client.getTelegramId(),
                    clientMessageSource.getMessage("message.brandSelection"), clientInlineKeyboardMarkupSource.generateBrandListPageableInlineMarkup(brands, client));

            updateLastBotMessage(client.getUser(), message);
        } catch (TelegramApiException ex) {
            LOGGER.error("Unable to send start message to user: {0}, reason: {1}", client.getTelegramId(), ex.getLocalizedMessage());
        }
    }
}
