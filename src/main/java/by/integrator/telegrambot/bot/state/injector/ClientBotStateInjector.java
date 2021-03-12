package by.integrator.telegrambot.bot.state.injector;

import javax.annotation.PostConstruct;

import by.integrator.telegrambot.bot.message.client.ClientMessageService;
import by.integrator.telegrambot.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.integrator.telegrambot.bot.api.BotStateInjector;
import by.integrator.telegrambot.bot.context.client.ClientBotContext;
import by.integrator.telegrambot.bot.state.ClientBotState;

@Component
public class ClientBotStateInjector implements BotStateInjector<ClientBotState, ClientBotContext> {

    @Autowired private ClientMessageService clientMessageService;
    @Autowired private ClientService clientService;

    @PostConstruct
    @Override
    public void inject() {
        ClientBotState.setClientMessageService(clientMessageService);
        ClientBotState.setClientService(clientService);
    }

}
