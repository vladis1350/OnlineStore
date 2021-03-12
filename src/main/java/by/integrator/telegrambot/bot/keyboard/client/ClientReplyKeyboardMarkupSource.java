package by.integrator.telegrambot.bot.keyboard.client;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import by.integrator.telegrambot.bot.keyboard.ReplyKeyboardMarkupSource;

public class ClientReplyKeyboardMarkupSource extends ReplyKeyboardMarkupSource {

    public ReplyKeyboard getClientMainMenu() {
        final ReplyKeyboardMarkup replyKeyboardMarkup = createInstance(true, true, false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow KeyboardRowOne = new KeyboardRow();
        KeyboardRow KeyboardRowTwo = new KeyboardRow();
        KeyboardRow KeyboardRowThree = new KeyboardRow();
        KeyboardRow KeyboardRowFour = new KeyboardRow();

        KeyboardRowOne.add(new KeyboardButton(PRODUCT_SELECTION));
        KeyboardRowTwo.add(new KeyboardButton(MAKE_ORDER));
        KeyboardRowThree.add(new KeyboardButton(TRACKING));
        KeyboardRowFour.add(new KeyboardButton(BECOME_PARTNER));

        keyboardRows.add(KeyboardRowOne);
        keyboardRows.add(KeyboardRowTwo);
        keyboardRows.add(KeyboardRowThree);
        keyboardRows.add(KeyboardRowFour);

        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public ReplyKeyboard getContact() {
        final ReplyKeyboardMarkup contactKeyboardMarkup = createInstance(true, true, false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow KeyboardRowOne = new KeyboardRow();
        KeyboardButton contact = new KeyboardButton(SHARE_CONTACT);
        contact.setRequestContact(true);

        KeyboardRowOne.add(contact);

        keyboardRows.add(KeyboardRowOne);

        contactKeyboardMarkup.setKeyboard(keyboardRows);

        return contactKeyboardMarkup;
    }
    
}
