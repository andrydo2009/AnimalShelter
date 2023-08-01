package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.AnimalReportDataNotFoundException;
import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

}
