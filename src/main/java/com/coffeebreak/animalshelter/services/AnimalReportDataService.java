package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.AnimalReportDataNotFoundException;
import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import com.pengrad.telegrambot.model.File;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@Service
public class AnimalReportDataService {
    private final AnimalReportDataRepository animalReportDataRepository;

    public AnimalReportDataService(AnimalReportDataRepository animalReportDataRepository) {
        this.animalReportDataRepository = animalReportDataRepository;
    }



    public AnimalReportData createAnimalReportData(AnimalReportData animalReportData) {
        return animalReportDataRepository.save ( animalReportData );
    }

    public AnimalReportData findById(Long id) {
        return animalReportDataRepository.findById ( id ).orElseThrow ( AnimalReportDataNotFoundException::new );
    }

    public AnimalReportData updateAnimalReportData(AnimalReportData animalReportData) {
        if (animalReportData.getId () != null) {
            if (findById ( animalReportData.getId () ) != null) {
                return animalReportDataRepository.save ( animalReportData );
            }
        }
        throw new AnimalReportDataNotFoundException ();

    }

    public void deleteAnimalReportData(Long id) {
        animalReportDataRepository.deleteById ( id );
    }

    public Collection<AnimalReportData> findAllAnimalReport() {
        return animalReportDataRepository.findAll ();
    }
    
    //пока в работе
    public AnimalReportData findByChatId(Long chatId) {
        return animalReportDataRepository.findByChatId ( chatId );
    }

    public Collection<AnimalReportData> findListAnimalReport(Long chatId) {
        return animalReportDataRepository.findAllByChatId ( chatId );
    }



    //работа с файлами пока тестовый вариант
    public void uploadReportData(Long personId, byte[] pictureFile, File file, String ration, String health,
                                 String habits, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports) throws IOException {
        AnimalReportData report = new AnimalReportData();
        report.setLastMessage(dateSendMessage);
        report.setDaysOfOwnership(daysOfReports);
        report.setFilePath(filePath);
        report.setFileSize(file.fileSize());
        report.setLastMessageMs(timeDate);
        report.setChatId(personId);
        report.setData(pictureFile);
        report.setRationOfAnimal(ration);
        report.setHealthOfAnimal(health);
        report.setHabitsOfAnimal(habits);
        this.animalReportDataRepository.save(report);
    }

    public void uploadReportData(Long personId, byte[] pictureFile, File file,
                                 String caption, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports) throws IOException {
        AnimalReportData report = new AnimalReportData();//findById(ownerId);
        report.setLastMessage(dateSendMessage);
        report.setDaysOfOwnership(daysOfReports);
        report.setFilePath(filePath);
        report.setChatId(personId);
        report.setFileSize(file.fileSize());
        report.setData(pictureFile);
        report.setCaption(caption);
        report.setLastMessageMs(timeDate);
        this.animalReportDataRepository.save(report);
    }

    public AnimalReportData transformationReport(AnimalReportData animalReportData){
        return new AnimalReportData(
                animalReportData.getId(),
                animalReportData.getChatId(),
                animalReportData.getRationOfAnimal(),
                animalReportData.getHealthOfAnimal(),
                animalReportData.getHabitsOfAnimal(),
                animalReportData.getDaysOfOwnership(),
                animalReportData.getFilePath(),
                animalReportData.getFileSize());
    }
}
