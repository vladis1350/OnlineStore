package by.integrator.telegrambot.bot.state;

import by.integrator.telegrambot.bot.api.BotState;
import by.integrator.telegrambot.bot.context.client.ClientBotContext;
import by.integrator.telegrambot.bot.keyboard.ReplyKeyboardMarkupSource;
import by.integrator.telegrambot.bot.message.client.ClientMessageService;
import by.integrator.telegrambot.exception.ClientBotStateException;
import by.integrator.telegrambot.model.Client;
import by.integrator.telegrambot.service.ClientService;
import lombok.Setter;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.objects.Contact;

public enum ClientBotState implements BotState<ClientBotState, ClientBotContext> {
    Start(false) {
        @SneakyThrows
        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendStartMessage(botContext);
        }

        @Override
        public ClientBotState nextState() {
            return ShareContact;
        }

        @Override
        public ClientBotState rootState() {
            return Start;
        }

    },

    ShareContact(true) {
        @SneakyThrows
        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendShareContact(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            String text = botContext.getUpdate().getMessage().getText();
            System.out.println(text);
        }

        @Override
        public void handleContact(ClientBotContext botContext) {
            String phoneNumber = botContext.getUpdate().getMessage().getContact().getPhoneNumber();
            System.out.println(phoneNumber);
            Client client = botContext.getClient();
            client.setPhoneNumber(phoneNumber);
            clientService.save(client);
            System.out.println("Stage: changed");
        }

        @Override
        public ClientBotState nextState() {
            return MainMenu;
        }

        @Override
        public ClientBotState rootState() {
            return Start;
        }

    },

    MainMenu(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendClientMainMenu(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) throws ClientBotStateException {
            String userAnswer = botContext.getUpdate().getMessage().getText();
            switch (userAnswer) {
                case ReplyKeyboardMarkupSource.PRODUCT_SELECTION:
                    nextState = ProductSelection;
                    break;
                case ReplyKeyboardMarkupSource.MAKE_ORDER:
                    nextState = MakeOrder;
                    break;
                case ReplyKeyboardMarkupSource.TRACKING:
                    nextState = Tracking;
                    break;
                case ReplyKeyboardMarkupSource.BECOME_PARTNER:
                    nextState = BecomePartner;
                    break;
                default:
                    nextState = MainMenu;
            }
        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    ProductSelection(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {
            clientMessageService.sendBrandList(botContext);
        }

        @Override
        public void handleText(ClientBotContext botContext) {
            String text = botContext.getUpdate().getMessage().getText();
            System.out.println(text);
        }

        @Override
        public void handleCallbackQuery(ClientBotContext botContext) {
            String date = botContext.getUpdate().getCallbackQuery().getData();
            System.out.println(date);
        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    MakeOrder(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    Tracking(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    BecomePartner(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterFirstname(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnteSurname(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    },

    EnterPatronymic(true) {
        private ClientBotState nextState = null;

        @Override
        public void enter(ClientBotContext botContext) {

        }

        @Override
        public void handleText(ClientBotContext botContext) {

        }

        @Override
        public ClientBotState nextState() {
            return nextState;
        }

        @Override
        public ClientBotState rootState() {
            return MainMenu;
        }
    };

    @Setter
    private static ClientMessageService clientMessageService;
    @Setter
    private static ClientService clientService;

    private final Boolean isInputNeeded;

    ClientBotState(Boolean isInputNeeded) {
        this.isInputNeeded = isInputNeeded;
    }

    public Boolean getIsInputNeeded() {
        return isInputNeeded;
    }

    public static ClientBotState getInitialState() {
        return Start;
    }

    @Override
    public void handleText(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleCallbackQuery(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleContact(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handlePhoto(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleVoice(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleVideo(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleVideoNote(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public void handleDocument(ClientBotContext clientBotContext) throws ClientBotStateException {
    }

    @Override
    public abstract void enter(ClientBotContext clientBotContext) throws ClientBotStateException;

    @Override
    public abstract ClientBotState nextState();

    @Override
    public abstract ClientBotState rootState();

}
