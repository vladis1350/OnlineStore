package by.integrator.telegrambot.bot.handler.client;

import by.integrator.telegrambot.bot.api.UpdateHandler;
import by.integrator.telegrambot.bot.api.enums.UpdateType;
import by.integrator.telegrambot.bot.context.client.ClientBotContext;
import by.integrator.telegrambot.bot.state.ClientBotState;
import by.integrator.telegrambot.exception.ClientBotStateException;
import by.integrator.telegrambot.exception.UserNotFoundException;
import by.integrator.telegrambot.model.Client;
import by.integrator.telegrambot.model.User;
import by.integrator.telegrambot.service.ClientService;
import by.integrator.telegrambot.service.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ClientUpdateHandler extends UpdateHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientUpdateHandler.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    private void updateState(User user, ClientBotState clientBotState) {
        if (user != null && user.getClient() != null && clientBotState != null) {
            user.getClient().setClientBotState(clientBotState);
            userService.save(user);
        }
    }

    @Override
    public void processText(Update update) throws UserNotFoundException, ClientBotStateException {
        runProcess(update, UpdateType.TEXT);
    }

    @Override
    public void processContact(Update update) throws UserNotFoundException, ClientBotStateException {
        runProcess(update, UpdateType.CONTACT);
    }

    @Override
    public void processPhoto(Update update) {
        runProcess(update, UpdateType.PHOTO);
    }

    @Override
    public void processCallbackQuery(Update update) throws UserNotFoundException, ClientBotStateException {
        final String chatId = update.getMessage().getChatId().toString();
        ClientBotContext botContext = null;
        ClientBotState botState = null;

        User user = userService.getByTelegramId(chatId).orElseThrow(UserNotFoundException::new);
        Client client = user.getClient();

        try {
            if (client == null) {
                client = clientService.createClient(user);
                System.out.println(client);

                botContext = ClientBotContext.of(client, update);
                botState = client.getClientBotState();

                botState.enter(botContext);

                while (!botState.getIsInputNeeded()) {
                    if (botState.nextState() != null) {
                        botState = botState.nextState();
                        botState.enter(botContext);
                    } else {
                        break;
                    }
                }
            } else {
                botContext = ClientBotContext.of(client, update);
                botState = client.getClientBotState();

                LOGGER.info("[{0} | {1}] CallbackQuery: {2}", chatId, botState, botContext.getUpdate().getCallbackQuery().getData());
                botState.handleCallbackQuery(botContext);

                do {
                    if (botState.nextState() != null) {
                        botState = botState.nextState();
                        botState.enter(botContext);
                    } else {
                        break;
                    }
                } while (!botState.getIsInputNeeded());
            }
        } catch (ClientBotStateException ex) {
            botState = ((ClientBotStateException) ex).getExceptionState().rootState();
            botState.enter(botContext);
        } finally {
            updateState(user, botState);
        }

    }

    @Override
    public void processVoice(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processVideo(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processVideoNote(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public void processDocument(Update update) {
        // TODO Auto-generated method stub
    }

    @SneakyThrows
    private void runProcess(Update update, UpdateType updateType) {
        final String chatId = update.getMessage().getChatId().toString();
        ClientBotContext botContext = null;
        ClientBotState botState = null;

        User user = userService.getByTelegramId(chatId).orElseThrow(UserNotFoundException::new);
        Client client = user.getClient();

        try {
            if (client == null) {
                client = clientService.createClient(user);
                System.out.println(client);

                botContext = ClientBotContext.of(client, update);
                botState = client.getClientBotState();

                botState.enter(botContext);

                while (!botState.getIsInputNeeded()) {
                    if (botState.nextState() != null) {
                        botState = botState.nextState();
                        botState.enter(botContext);
                    } else {
                        break;
                    }
                }
            } else {
                botContext = ClientBotContext.of(client, update);
                botState = client.getClientBotState();

                if (updateType.equals(UpdateType.TEXT)) {
                    LOGGER.info("[{0} | {1}] Text: {2}", chatId, botState, update.getMessage().getText());
                    botState.handleText(botContext);
                } else if (updateType.equals(UpdateType.CONTACT)) {
                    LOGGER.info("[{0} | {1}] Contact: {2}", chatId, botState, update.getMessage().getContact().getPhoneNumber());
                    botState.handleContact(botContext);
                } else if (updateType.equals(UpdateType.PHOTO)) {
                    LOGGER.info("[{0} | {1}] Photo", chatId, botState);
                    botState.handlePhoto(botContext);
                }

                do {
                    if (botState.nextState() != null) {
                        botState = botState.nextState();
                        botState.enter(botContext);
                    } else {
                        break;
                    }
                } while (!botState.getIsInputNeeded());
            }
        } catch (ClientBotStateException ex) {
            botState = ((ClientBotStateException) ex).getExceptionState().rootState();
            botState.enter(botContext);
        } finally {
            updateState(user, botState);
        }
    }

}
