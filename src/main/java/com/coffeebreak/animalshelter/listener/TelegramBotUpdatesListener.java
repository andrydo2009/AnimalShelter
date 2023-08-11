package com.coffeebreak.animalshelter.listener;


import com.coffeebreak.animalshelter.keyboards.AnimalShelterKeyboard;
import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import com.coffeebreak.animalshelter.services.AnimalReportDataService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.coffeebreak.animalshelter.listener.Constants.*;

@Component
public class TelegramBotUpdatesListener implements UpdatesListener {

    @Autowired
    AnimalReportDataService reportDataService;

    @Autowired
    AnimalReportDataRepository reportDataRepository;

    private final TelegramBot telegramBot;
    private final AnimalShelterKeyboard animalShelterKeyboard;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    public TelegramBotUpdatesListener(TelegramBot telegramBot, AnimalShelterKeyboard animalShelterKeyboard) {
        this.telegramBot = telegramBot;
        this.animalShelterKeyboard = animalShelterKeyboard;
    }

    private Long daysOfReports;

    private boolean isCat = false;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        for (Update update : updates) {
            if (update.message() != null) {
                logger.info("Handles update: {}", update);
                Message message = update.message();
                Long chatId = message.chat().id();
                String messageText = message.text();
                Calendar calendar = new GregorianCalendar();
                daysOfReports = reportDataRepository.findAll().stream()
                        .filter(s -> s.getChatId().equals(chatId))
                        .count() + 1;

                    long compareTime = calendar.get(Calendar.DAY_OF_MONTH);
                    Long lastMessageTime = reportDataRepository.findAll().stream()
                            .filter(s -> Objects.equals(s.getChatId(), chatId))
                            .map(AnimalReportData::getLastMessageMs)
                            .max(Long::compare)
                            .orElseGet(() -> null);
                    if (lastMessageTime != null) {
                        Date lastDateSendMessage = new Date(lastMessageTime * 1000);
                        long numberOfDay = lastDateSendMessage.getDate();

                        if (daysOfReports < 30 ) {
                            if (compareTime != numberOfDay) {
                                if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                                    getReport(update);
                                }
                            } else {
                                if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                                    sendMessage(chatId, "Вы уже отправляли отчет сегодня");
                                }
                            }
                            if (daysOfReports == 31) {
                                sendMessage(chatId, "Вы прошли испытательный срок!");
                            }
                        }
                    } else {
                        if (update.message() != null && update.message().photo() != null && update.message().caption() != null) {
                            getReport(update);
                        }
                    }
                    if (update.message() != null && update.message().photo() != null && update.message().caption() == null) {
                        sendMessage(chatId, "Отчет нужно присылать с описанием!");
                    }

                    if (update.message() != null && update.message().contact() != null) {
                    //    getContactOwner(update);
                    }

               switch (messageText) {

                    case START_COMMAND:
                        animalShelterKeyboard.chooseMenu(chatId);
                        break;

                    case "Приют для кошек":
                        isCat = true;
                        animalShelterKeyboard.sendMenuCatShelter(chatId);
//                            sendMessage(chatId, "Вы выбрали приют для кошек");
                        break;

                    case "Приют для собак":
                        isCat = false;
                        animalShelterKeyboard.sendMenuDogShelter(chatId);
//                        sendMessage(chatId, "Вы выбрали приют для собак");
                        break;


                    case "Главное меню":
                        animalShelterKeyboard.chooseMenu(chatId);
                        break;

                    case "Узнать информацию о приюте":
                        animalShelterKeyboard.sendMenuInformationAboutShelter(chatId);
                        break;

                    case "Информация о приюте":
                        if (isCat) {
                            sendMessage(chatId, INFORMATION_ABOUT_CAT_SHELTER);
                            break;
                        } else {
                            sendMessage(chatId, INFORMATION_ABOUT_DOG_SHELTER);
                        }
                        break;

                    case "Прислать отчет о питомце":
                        sendMessage(chatId, INFORMATION_ABOUT_REPORT);
                        sendMessage(chatId, REPORT_EXAMPLE);
                        break;

                    case "Вернуться в меню":
                        if (isCat) {
                            animalShelterKeyboard.sendMenuCatShelter(chatId);
                            break;
                        } else {
                            animalShelterKeyboard.sendMenuDogShelter(chatId);
                        }
                        break;

                    case "Расписание":
                        if (isCat) {
                            sendMessage(chatId, SCHEDULE_ABOUT_CAT_SHELTER);
                            break;
                        } else {
                            sendMessage(chatId, SCHEDULE_ABOUT_DOG_SHELTER);
                            break;
                        }

                    case "Оформление пропуска":
                        if (isCat) {
                            sendMessage(chatId, REGISTRATION_OF_PASS_ABOUT_CAT_SHELTER);
                            break;
                        } else {
                            sendMessage(chatId, REGISTRATION_OF_PASS_ABOUT_DOG_SHELTER);
                            break;
                        }

                    case "Техника безопасности":
                        sendMessage(chatId, TECHNICAL_SAFETY);
                        break;

                    case "Важная информация":
                        if (isCat) {
                            sendMessage(chatId, INFORMATION_HOW_TO_TAKE_CAT);
                            break;
                        } else {
                            sendMessage(chatId, INFORMATION_HOW_TO_TAKE_DOG);
                            break;
                        }

                    case "Правила знакомства":
                        if (isCat) {
                            sendMessage(chatId, RULES_FOR_GETTING_TO_KNOW_A_CAT);
                            break;
                        } else {
                            sendMessage(chatId, RULES_FOR_GETTING_TO_KNOW_A_DOG);
                            break;
                        }

                    case "Список документов":
                        sendMessage(chatId, LIST_OF_DOCUMENTS);
                        break;

                    case "Рекомендации к транспортировке":
                        if (isCat) {
                            sendMessage(chatId, RECOMMENDATIONS_FOR_TRANSPORTATION_CAT);
                            break;
                        } else {
                            sendMessage(chatId, RECOMMENDATIONS_FOR_TRANSPORTATION_DOG);
                            break;
                        }

                    case "Обустройство жилья котёнка":
                        sendMessage(chatId, ARRANGEMENT_OF_OF_HOUSING_FOR_A_KITTEN);
                        break;

                    case "Обустройство жилья взрослого кота":
                        sendMessage(chatId, ARRANGEMENT_OF_OF_HOUSING_FOR_A_ADULT_CAT);
                        break;

                    case "Обустройство жилья кота с инвалидностью":
                        sendMessage(chatId, ARRANGEMENT_OF_OF_HOUSING_FOR_A_CAT_WITH_A_DISABILITY);
                        break;

                    case "Причины по которым можем не выдать питомца":
                        sendMessage(chatId, INFORMATION_WE_DO_NOT_GIVE_OUT_PETS);
                        break;

                    case "Позвать волонтёра":
                        sendMessage(chatId, "Я передал ваше сообщение волонтёру, он скоро с вами свяжется. " +
                                "Если у вас закрытый профиль - поделитесь контактом. " +
                                "Нажмите справа сверху на  - \"отправить контактные данные\" и волонтёр вам перезвонит");
                        sendForwardMessage(chatId, update.message().messageId());
                        break;

                    case "Как взять кота из приюта":
                        animalShelterKeyboard.sendMenuHowToTakeCat(chatId);
                        break;

                    case "Как взять собаку из приюта":
                        animalShelterKeyboard.sendMenuHowToTakeDog(chatId);
                        break;

                    case "Обустройство жилья щенка":
                        sendMessage(chatId, ARRANGEMENT_OF_OF_HOUSING_FOR_A_PUPPY);
                        break;

                    case "Обустройство жилья взрослой собаки":
                        sendMessage(chatId, ARRANGEMENT_OF_OF_HOUSING_FOR_A_DOG);
                        break;

                    case "Обустройство жилья собаки с инвалидностью":
                        sendMessage(chatId, ARRANGEMENT_OF_OF_HOUSING_FOR_A_DOG_WITH_A_DISABILITY);
                        break;

                    case "Советы кинолога":
                        sendMessage(chatId, TIPS_FROM_A_DOG_HANDLER);
                        break;

                    case "Контакты проверенных кинологов":
                        sendMessage(chatId, CONTACTS_OF_THE_DOG_HANDLER);
                        break;

                    case "":
                        System.out.println("Так нельзя");
                        sendMessage(chatId, "Пустое сообщение");
                        break;

                    default:
                        // Обработка незапланированного сценария
                        sendReplyMessage(chatId, "Неизвестная команда", update.message().messageId());
                        break;
                        }
                }
            }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendReplyMessage(Long chatId, String messageText, Integer messageId) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyToMessageId(messageId);
        telegramBot.execute(sendMessage);
    }

    public void sendForwardMessage(Long chatId, Integer messageId) {
        ForwardMessage forwardMessage = new ForwardMessage(TELEGRAM_CHAT_VOLUNTEERS, chatId, messageId);
        telegramBot.execute(forwardMessage);
    }

    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    public void getReport(Update update) {
        Pattern pattern = Pattern.compile(REGEX_MESSAGE_REPORT);
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String ration = matcher.group(3);
            String health = matcher.group(7);
            String habits = matcher.group(11);

            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportDataService.uploadTelegramReportData(update.message().chat().id(), fileContent, file,
                        ration, health, habits, fullPathPhoto, dateSendMessage, timeDate, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));

                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        } else {
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                File file = getFileResponse.file();
                file.fileSize();
                String fullPathPhoto = file.filePath();

                long timeDate = update.message().date();
                Date dateSendMessage = new Date(timeDate * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportDataService.uploadTelegramReportData(update.message().chat().id(), fileContent, file, update.message().caption(),
                        fullPathPhoto, dateSendMessage, timeDate, daysOfReports);

                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                System.out.println("Отчет успешно принят от: " + update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
            }
        }
    }

}
