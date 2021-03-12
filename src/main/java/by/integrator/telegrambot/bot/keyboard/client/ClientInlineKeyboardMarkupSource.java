package by.integrator.telegrambot.bot.keyboard.client;

import by.integrator.telegrambot.bot.keyboard.InlineKeyboardMarkupSource;
import by.integrator.telegrambot.model.Brand;
import by.integrator.telegrambot.model.Client;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ClientInlineKeyboardMarkupSource extends InlineKeyboardMarkupSource {
    public final static Integer MAX_ITEMS_AT_PAGE = 8;
    public final static String NEXT_PAGE_EMOJI = ":arrow_right:";
    public final static String PREVIOUS_PAGE_EMOJI = ":arrow_left:";
    public final static String BACK_EMOJI = ":leftwards_arrow_with_hook:";
    public final static String SELECTED_EMOJI = ":white_check_mark:";
    public final static String CONTINUE_EMOJI = ":arrow_down:";
    public final static String STOP_WATCH = ":stopwatch:";
    public final static String GREY_EXCLAMATION = ":grey_exclamation:";
    public final static String BALLOT_BOX_WITH_CHECK = ":ballot_box_with_check:";
    public final static String FIRE_EMOJI = ":fire:";

    public final static String WILL_VISIT_EVENT_PREFIX = "notification_callback.visit_event=";
    public final static String WILL_VISIT_TRIAL_PREFIX = "notification_callback.visit_trial=";
    public final static String PAY_CALLBACK = "notification_callback.pay";
    public final static String PAY_WITH_TRIAL_CALLBACK = "notification_callback.pay=";

    public final InlineKeyboardMarkup generateBrandListPageableInlineMarkup(List<Brand> brands, Client client) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>();
        Integer page = client.getUser().getCurrentPage();
        for (int i = ((page - 1) * MAX_ITEMS_AT_PAGE); i < page * MAX_ITEMS_AT_PAGE && i < brands.size(); i++) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();

            button.setText(brands.get(i).getName());
            button.setCallbackData(brands.get(i).getId().toString());
            button.setUrl(brands.get(i).getLink());

            buttons.add(button);

            keyboardRows.add(buttons);
        }

        if (brands.size() > MAX_ITEMS_AT_PAGE) {
            keyboardRows.add(getNavigateInlineButtons(brands, page, client.getClientBotState()));
        }

        inlineKeyboardMarkup.setKeyboard(keyboardRows);

        return inlineKeyboardMarkup;
    }
}
