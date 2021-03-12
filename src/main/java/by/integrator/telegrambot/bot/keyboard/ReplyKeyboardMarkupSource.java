package by.integrator.telegrambot.bot.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class ReplyKeyboardMarkupSource {

    public static final String PRODUCT_SELECTION = "Выбор товара";
    public static final String MAKE_ORDER = "Сделать заказ";
    public static final String TRACKING = "Трекинг";
    public static final String BECOME_PARTNER = "Стать партнёром";
    public static final String SHARE_CONTACT = "Поделиться контактом";

    protected ReplyKeyboardMarkup createInstance(Boolean selective, Boolean resizeKeyboard, Boolean oneTimeKeyboard) {
        return ReplyKeyboardMarkup.builder()
                                  .selective(selective)
                                  .resizeKeyboard(resizeKeyboard)
                                  .oneTimeKeyboard(oneTimeKeyboard)
                                  .build();
    }

}
