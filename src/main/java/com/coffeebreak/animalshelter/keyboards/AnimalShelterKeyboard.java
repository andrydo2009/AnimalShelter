package com.coffeebreak.animalshelter.keyboards;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnimalShelterKeyboard {
    private final TelegramBot telegramBot;

    private static final Logger logger = LoggerFactory.getLogger(AnimalShelterKeyboard.class);

    public AnimalShelterKeyboard(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void chooseMenu(Long chatId) {
        logger.info("Choose menu method was invoked");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Приют для кошек"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Приют для собак"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Добро пожаловать! Я бот приюта для животных. Выберите приют: Приют для кошек или Приют для собак.");
        logger.info("Choose menu method was executed successfully");
    }

    public void sendMenuCatShelter(Long chatId) {
        logger.info("Send menu cat shelter method was invoked");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Узнать информацию о приюте"), new KeyboardButton("Как взять кота из приюта"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Выбрать кота"), new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать добровольца"), new KeyboardButton("Главное меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Вы выбрали приют для кошек");
        logger.info("Send menu cat shelter method executed successfully");
    }

    public void sendMenuDogShelter(Long chatId) {
        logger.info("Send menu dog shelter method was invoked");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Узнать информацию о приюте"), new KeyboardButton("Как взять собаку из приюта"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Выбрать собаку"), new KeyboardButton("Прислать отчет о питомце"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать добровольца"), new KeyboardButton("Главное меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Вы выбрали приют для собак");
        logger.info("Send menu dog shelter method executed successfully");
    }

    public void sendMenuInformationAboutShelter(Long chatId) {
        logger.info("Send menu information about shelter method was invoked");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Информация о приюте"), new KeyboardButton("Оставить контактные данные").requestContact(true));
        replyKeyboardMarkup.addRow(new KeyboardButton("Расписание"), new KeyboardButton("Оформление пропуска"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Техника безопасности"), new KeyboardButton("Позвать добровольца"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Какая информация вас интересует?");
        logger.info("Send menu information about shelter method was executed successfully");
    }

    public void sendMenuHowToTakeCat(Long chatId) {
        logger.info("Send menu how to take cat method was invoked");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Важная информация"),
                new KeyboardButton("Правила знакомства"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Список документов"),
                new KeyboardButton("Рекомендации к транспортировке"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Обустройство жилья котёнка"),
                new KeyboardButton("Обустройство жилья взрослого кота"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Обустройство жилья кота с инвалидностью"),
                new KeyboardButton("Причины по которым можем не выдать питомца"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать добровольца"),
                new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Мы очень рады что вы хотите завести нового друга. Выберите что вас интересует:");
        logger.info("Send menu how to take cat method was executed successfully");
    }
    public void sendMenuHowToTakeDog(Long chatId) {
        logger.info("Send menu how to take dog method was invoked");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("Важная информация"),
                new KeyboardButton("Правила знакомства"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Список документов"),
                new KeyboardButton("Рекомендации к транспортировке"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Обустройство жилья щенка"),
                new KeyboardButton("Обустройство жилья взрослой собаки"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Обустройство жилья собаки с инвалидностью"),
                new KeyboardButton("Причины по которым можем не выдать питомца"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Советы кинолога"),
                new KeyboardButton("Контакты проверенных кинологов"));
        replyKeyboardMarkup.addRow(new KeyboardButton("Позвать добровольца"),
                new KeyboardButton("Вернуться в меню"));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Мы очень рады что вы хотите завести нового друга. Выберите что вас интересует:");
        logger.info("Send menu how to take dog method was executed successfully");
    }

    public void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);

        SendMessage request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        SendResponse sendResponse = telegramBot.execute(request);

        if (!sendResponse.isOk()) {
            int codeError = sendResponse.errorCode();
            String description = sendResponse.description();
            logger.error("Error code: {}", codeError);
            logger.error("Error description: {}", description);
        }
    }
}
