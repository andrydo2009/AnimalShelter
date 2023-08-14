package com.coffeebreak.animalshelter.services;


import com.coffeebreak.animalshelter.exceptions.AnimalReportDataNotFoundException;
import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import com.pengrad.telegrambot.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@Service
public class AnimalReportDataService {
    private final AnimalReportDataRepository animalReportDataRepository;

    private static final Logger logger = LoggerFactory.getLogger(AnimalReportDataService.class);

    public AnimalReportDataService(AnimalReportDataRepository animalReportDataRepository) {
        this.animalReportDataRepository = animalReportDataRepository;
    }

    /**
     * Создание объекта класса AnimalReportData и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param animalReportData объект класса AnimalReportData, не может быть null
     * @return созданный объект класса AnimalReportData
     */
    public AnimalReportData createAnimalReportData(AnimalReportData animalReportData) {
        logger.info("Create animal report data method was invoked");
        animalReportDataRepository.save(animalReportData);
        logger.info("Animal report data {} was created successfully", animalReportData);
        return animalReportData;
    }

    /**
     * Поиск объекта класса AnimalReportData по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param animalReportDataId идентификатор искомого объекта класса AnimalReportData, не может быть null
     * @return найденный объект класса AnimalReportData
     * @throws AnimalReportDataNotFoundException если объект класса AnimalReportData не был найден в БД
     */
    public AnimalReportData findAnimalReportDataById(Long animalReportDataId) {
        logger.info("Find animal report data by id = {} method was invoked", animalReportDataId);
        AnimalReportData animalReportData = animalReportDataRepository.findById(animalReportDataId).orElseThrow(AnimalReportDataNotFoundException::new);
        logger.info("Animal report data with id = {} was successfully found", animalReportDataId);
        return animalReportData;
    }

    /**
     * Получение коллекции объектов класса AnimalReportData из БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findAll()}
     * @return коллекция объектов класса AnimalReportData
     */
    public Collection<AnimalReportData> findAllAnimalReport() {
        logger.info("Find all animal report data method was invoked");
        Collection<AnimalReportData> animalReportData = animalReportDataRepository.findAll();
        logger.info("All animal report data were successfully found");
        return animalReportData;
    }

    /**
     * Изменение объекта класса AnimalReportData и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param animalReportData объект класса AnimalReportData, не может быть null
     * @return изменённый объект класса AnimalReportData
     * @throws AnimalReportDataNotFoundException если объект класса AnimalReportData не был найден в БД
     */
    public AnimalReportData updateAnimalReportData(AnimalReportData animalReportData) {
        logger.info("Update animal report data: {} method was invoked", animalReportData);
        if (animalReportData.getId() != null) {
            if (findAnimalReportDataById(animalReportData.getId()) != null) {
                animalReportDataRepository.save(animalReportData);
                logger.info("Animal report data {} was updated successfully", animalReportData);
                return animalReportData;
            }
        }
        throw new AnimalReportDataNotFoundException();
    }

    /**
     * Удаление объекта класса AnimalReportData по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     * @param animalReportDataId идентификатор искомого объекта класса AnimalReportData, не может быть null
     */
    public void deleteAnimalReportDataById(Long animalReportDataId) {
        logger.info("Delete animal report data by id = {} method was invoked", animalReportDataId);
        animalReportDataRepository.deleteById(animalReportDataId);
        logger.info("Animal report data with id = {} was deleted successfully", animalReportDataId);
    }

    /**
     * Метод загрузки отчета о животном.
     * @throws IOException может возникнуть если при загрузке отчета что-то пошло не так
     * @see AnimalReportDataService
     */
    public void uploadTelegramAnimalReportData(
            Long chatId, byte[] fileContent, File file, String ration, String health, String habits, String filePath,
            Date sendMessageDate, Long dateTime, Long daysOfReports) throws IOException {
        logger.info("Upload full telegram report data method was invoked");
        AnimalReportData report = new AnimalReportData();
        report.setChatId(chatId);
        report.setData(fileContent);
        report.setFileSize(file.fileSize());
        report.setRationOfAnimal(ration);
        report.setHealthOfAnimal(health);
        report.setHabitsOfAnimal(habits);
        report.setFilePath(filePath);
        report.setLastMessage(sendMessageDate);
        report.setLastMessageMs(dateTime);
        report.setDaysOfOwnership(daysOfReports);
        animalReportDataRepository.save(report);
    }

    /**
     * Метод загрузки отчета о животном.
     * @throws IOException может возникнуть если при загрузке отчета что-то пошло не так
     * @see AnimalReportDataService
     */
    public void uploadTelegramAnimalReportData(
            Long chatId, byte[] fileContent, File file, String caption, String filePath, Date sendMessageDate,
            Long dateTime, Long daysOfReports) throws IOException {
        logger.info("Upload telegram report data method was invoked");
        AnimalReportData report = new AnimalReportData();
        report.setChatId(chatId);
        report.setData(fileContent);
        report.setFileSize(file.fileSize());
        report.setCaption(caption);
        report.setFilePath(filePath);
        report.setLastMessage(sendMessageDate);
        report.setLastMessageMs(dateTime);
        report.setDaysOfOwnership(daysOfReports);
        animalReportDataRepository.save(report);
    }
}
