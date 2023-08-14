package com.coffeebreak.animalshelter.listener;


import com.coffeebreak.animalshelter.keyboards.AnimalShelterKeyboard;
import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.models.CatOwner;
import com.coffeebreak.animalshelter.models.DogOwner;
import com.coffeebreak.animalshelter.models.OwnershipStatus;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import com.coffeebreak.animalshelter.repositories.CatOwnerRepository;
import com.coffeebreak.animalshelter.repositories.DogOwnerRepository;
import com.coffeebreak.animalshelter.services.AnimalReportDataService;
import com.coffeebreak.animalshelter.services.CatOwnerService;
import com.coffeebreak.animalshelter.services.DogOwnerService;
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
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    CatOwnerRepository catOwnerRepository;

    // проверка
    @Autowired
    CatOwnerService catOwnerService;

    @Autowired
    DogOwnerRepository dogOwnerRepository;

    // проверка
    @Autowired
    DogOwnerService dogOwnerService;

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
        updates.forEach(update -> {
                if (update.message() != null) {
                    logger.info("Handles update: {}", update);
                    Message message = update.message();
                    Long chatId = message.chat().id();
                    String messageText = message.text();
                    Calendar calendar = new GregorianCalendar();
                    daysOfReports = reportDataRepository.findAll().stream()
                            .filter(s -> s.getChatId().equals(chatId))
                            .count() + 1;
                    try {
                        long compareTime = calendar.get(Calendar.DAY_OF_MONTH);
                        Long lastMessageTimeMs = reportDataRepository.findAll().stream()
                                .filter(s -> Objects.equals(s.getChatId(), chatId))
                                .map(AnimalReportData::getLastMessageMs)
                                .max(Long::compare)
                                .orElse(null);
                        if (lastMessageTimeMs != null) {
                            Date lastDateSendMessage = new Date(lastMessageTimeMs * 1000); // получаем количество секунд последнего отправленного сообщения
                            long numberOfDay = lastDateSendMessage.getDate();
                            if (daysOfReports < 30) {
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
                            getOwnerContactData(update);
                        }

                        switch (messageText) {

                            case START_COMMAND:
                                animalShelterKeyboard.chooseMenu(chatId);
                                break;

                            case "Приют для кошек":
                                isCat = true;
                                animalShelterKeyboard.sendMenuCatShelter(chatId);
                                break;

                            case "Приют для собак":
                                isCat = false;
                                animalShelterKeyboard.sendMenuDogShelter(chatId);
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

                            case "Выбрать кота":
                                sendMessage(chatId, CHOOSE_CAT_TEXT);
                                break;

                            case "Выбрать собаку":
                                sendMessage(chatId, CHOOSE_DOG_TEXT);
                                break;

                            case "Вернуться в меню":
                                if (isCat) {
                                    animalShelterKeyboard.sendMenuCatShelter(chatId);
                                    break;
                                } else {
                                    animalShelterKeyboard.sendMenuDogShelter(chatId);
                                }
                                break;

                            case "Часы работы":
                                if (isCat) {
                                    sendMessage(chatId, CAT_SHELTER_WORKING_HOURS);
                                    break;
                                } else {
                                    sendMessage(chatId, DOG_SHELTER_WORKING_HOURS);
                                    break;
                                }

                            case "Оформление пропуска":
                                if (isCat) {
                                    sendMessage(chatId, CAT_SHELTER_REGISTRATION_OF_PASS);
                                    break;
                                } else {
                                    sendMessage(chatId, DOG_SHELTER_REGISTRATION_OF_PASS);
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
                                    sendMessage(chatId, RECOMMENDATIONS_FOR_CAT_TRANSPORTATION);
                                    break;
                                } else {
                                    sendMessage(chatId, RECOMMENDATIONS_FOR_DOG_TRANSPORTATION);
                                    break;
                                }

                            case "Обустройство жилья котёнка":
                                sendMessage(chatId, HOUSE_ARRANGEMENT_FOR_A_KITTEN);
                                break;

                            case "Обустройство жилья взрослого кота":
                                sendMessage(chatId, HOUSE_ARRANGEMENT_FOR_AN_ADULT_CAT);
                                break;

                            case "Обустройство жилья кота с инвалидностью":
                                sendMessage(chatId, HOUSE_ARRANGEMENT_FOR_DISABLED_CAT);
                                break;

                            case "Причины по которым можем не выдать питомца":
                                sendMessage(chatId, REFUSAL_REASONS);
                                break;

                            case "Позвать добровольца":
                                sendMessage(chatId, "Я передал ваше сообщение добровольцу, он скоро с вами свяжется.");
                                sendForwardMessage(chatId, update.message().messageId());
                                break;

                            case "Как взять кота из приюта":
                                animalShelterKeyboard.sendMenuHowToTakeCat(chatId);
                                break;

                            case "Как взять собаку из приюта":
                                animalShelterKeyboard.sendMenuHowToTakeDog(chatId);
                                break;

                            case "Обустройство жилья щенка":
                                sendMessage(chatId, HOUSE_ARRANGEMENT_FOR_A_PUPPY);
                                break;

                            case "Обустройство жилья взрослой собаки":
                                sendMessage(chatId, HOUSE_ARRANGEMENT_FOR_AN_ADULT_DOG);
                                break;

                            case "Обустройство жилья собаки с инвалидностью":
                                sendMessage(chatId, HOUSE_ARRANGEMENT_FOR_DISABLED_DOG);
                                break;

                            case "Советы кинолога":
                                sendMessage(chatId, DOG_HANDLER_TIPS);
                                break;

                            case "Контакты проверенных кинологов":
                                sendMessage(chatId, DOG_HANDLERS_CONTACT_DATA);
                                break;

                            case "":
                                System.out.println("Введено пустое сообщение");
                                sendMessage(chatId, "Пустое сообщение");
                                break;

                            default:
                                sendReplyMessage(chatId, "Неизвестная команда", update.message().messageId());
                                break;
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Возникла ошибка!");
                    }
                }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendReplyMessage(Long chatId, String messageText, Integer messageId) {
        logger.info("Send reply message method was invoked");
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage.replyToMessageId(messageId);
        telegramBot.execute(sendMessage);
        logger.info("Reply message was sent successfully");
    }

    public void sendForwardMessage(Long chatId, Integer messageId) {
        logger.info("Send forward message method was invoked");
        ForwardMessage forwardMessage = new ForwardMessage(TELEGRAM_CHAT_VOLUNTEER, chatId, messageId);
        telegramBot.execute(forwardMessage);
        logger.info("Forward message was sent successfully");
    }

    public void sendMessage(Long chatId, String message) {
        logger.info("Send message method was invoked");
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        logger.info("Message was sent successfully");
        if (!sendResponse.isOk()) {
            logger.error("Error during sending message: {}", sendResponse.description());
        }
    }

    public void getOwnerContactData(Update update) {
        logger.info("Get owner contact data method was invoked");
        if (update.message().contact() != null) {
            String firstName = update.message().contact().firstName();
            String lastName = update.message().contact().lastName();
            String phone = update.message().contact().phoneNumber();
            String username = update.message().chat().username();
            Long finalChatId = update.message().chat().id();
            var sortChatId = dogOwnerRepository.findAll().stream()
                    .filter(i -> Objects.equals(i.getChatId(),finalChatId))
                    .toList();
            var sortChatIdCat = catOwnerRepository.findAll().stream()
                    .filter(i -> Objects.equals(i.getChatId(),finalChatId))
                    .toList();
            if (!sortChatId.isEmpty() || !sortChatIdCat.isEmpty()) {
                sendMessage(finalChatId, "Вы уже в базе!");
                logger.info("Owner contact data already in database");
                return;
            }
            if (lastName != null) {
                String name = firstName + " " + lastName + " " + username;
                if(isCat){
                    catOwnerRepository.save(new CatOwner(finalChatId, name, phone, OwnershipStatus.SEARCH));
                } else {
                    dogOwnerRepository.save(new DogOwner(finalChatId, name, phone, OwnershipStatus.SEARCH));
                }
                sendMessage(finalChatId, "Вас успешно добавили в базу! Скоро вам перезвонят.");
                logger.info("Owner contact data with last name was registered successfully");
                return;
            }
            if (isCat) {
                catOwnerRepository.save(new CatOwner(finalChatId, firstName, phone, OwnershipStatus.SEARCH));
            } else {
                dogOwnerRepository.save(new DogOwner(finalChatId, firstName, phone, OwnershipStatus.SEARCH));
            }
            sendMessage(finalChatId, "Вас успешно добавили в базу! Скоро вам перезвонят.");
            logger.info("Owner contact data was registered successfully");
            // Сообщение в чат добровольцам
            sendMessage(TELEGRAM_CHAT_VOLUNTEER, firstName + " " + phone + " Добавил(а) свой номер в базу");
            sendForwardMessage(finalChatId, update.message().messageId());
        }
    }

    public void getReport(Update update) {
        logger.info("Get report method was invoked");
        Pattern pattern = Pattern.compile(REGEX_MESSAGE_REPORT);
        Matcher matcher = pattern.matcher(update.message().caption());
        if (matcher.matches()) {
            String ration = matcher.group(3);
            String health = matcher.group(6);
            String habits = matcher.group(9);
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                logger.info("Full report method was invoked");
                File file = getFileResponse.file();
                file.fileSize();
                String filePath = file.filePath();
                long dateTime = update.message().date();
                Date sendMessageDate = new Date(dateTime * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportDataService.uploadFullTelegramAnimalReportData(update.message().chat().id(), fileContent, file,
                        ration, health, habits, filePath, sendMessageDate, dateTime, daysOfReports);
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                logger.info("Full report accepted from user: {} with chat id {}", update.message().chat().firstName(),
                        update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
                logger.error("File content was not uploaded");
            }
        } else {
            GetFile getFileRequest = new GetFile(update.message().photo()[1].fileId());
            GetFileResponse getFileResponse = telegramBot.execute(getFileRequest);
            try {
                logger.info("Partly report method was invoked");
                File file = getFileResponse.file();
                file.fileSize();
                String filePath = file.filePath();
                long dateTime = update.message().date();
                Date sendMessageDate = new Date(dateTime * 1000);
                byte[] fileContent = telegramBot.getFileContent(file);
                reportDataService.uploadTelegramAnimalReportData(update.message().chat().id(), fileContent, file, update.message().caption(),
                        filePath, sendMessageDate, dateTime, daysOfReports);
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Отчет успешно принят!"));
                logger.info("Partly report accepted from user: {} with chat id {}", update.message().chat().firstName(),
                        update.message().chat().id());
            } catch (IOException e) {
                System.out.println("Ошибка загрузки фото!");
                logger.error("File content was not uploaded");
            }
        }
    }

    @Scheduled(cron = "* 30 21 * * *")
    public void checkResults() {
        if (daysOfReports < 30) {
            var twoDay = 172800000; // секунды в двух днях
            var nowTime = new Date().getTime() - twoDay;
            var getDistinct = this.reportDataRepository.findAll().stream()
                    .sorted(Comparator
                            .comparing(AnimalReportData::getChatId))
                    .max(Comparator
                            .comparing(AnimalReportData::getLastMessageMs));
            getDistinct.stream()
                    .filter(i -> i.getLastMessageMs() * 1000 < nowTime)
                    .forEach(s -> sendMessage(s.getChatId(), "Вы забыли прислать отчет, пришлите его как можно скорее!"));
        }
    }
}
