package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.AnimalReportDataNotFoundException;
import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс-сервис, содержащий CRUD-методы объекта класса AnimalReportData
 * @see AnimalReportData
 * @see AnimalReportDataService
 */
@Service
public class AnimalReportDataService {
    private final AnimalReportDataRepository animalReportDataRepository;

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
        return animalReportDataRepository.save(animalReportData);
    }

    /**
     * Поиск объекта класса AnimalReportData по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param animalReportDataId идентификатор искомого объекта класса CatOwner, не может быть null
     * @return найденный объект класса AnimalReportData
     * @throws AnimalReportDataNotFoundException если объект класса CatOwner не был найден в БД
     */
    public AnimalReportData findAnimalReportDataById(Long animalReportDataId) {
        return animalReportDataRepository.findById(animalReportDataId).orElseThrow(AnimalReportDataNotFoundException::new);
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
        if (animalReportData.getId() != null) {
            if (findAnimalReportDataById(animalReportData.getId()) != null) {
                return animalReportDataRepository.save(animalReportData);
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
        animalReportDataRepository.deleteById(animalReportDataId);
    }

    /**
     * Получение коллекции объектов класса AnimalReportData из БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findAll()}
     * @return коллекция объектов класса AnimalReportData
     */
    public Collection<AnimalReportData> findAllAnimalReportData() {
        return animalReportDataRepository.findAll ();
    }

    //пока в работе
    /**
     * Поиск объекта класса AnimalReportData по идентификатору чата
     * <br>
     * Используется метод репозитория {@link AnimalReportDataRepository#findByChatId(Long)}
     * @param chatId идентификатор искомого объекта класса AnimalReportData, не может быть null
     * @return найденный объект класса AnimalReportData
     */
    public AnimalReportData findAnimalReportDataByChatId(Long chatId) {
        return animalReportDataRepository.findByChatId(chatId);
    }

    /**
     * Получение коллекции объектов класса AnimalReportData по идентификатору чата
     * <br>
     * Используется метод репозитория {@link AnimalReportDataRepository#findAllByChatId(Long)}
     * @return коллекция объектов класса AnimalReportData
     */
    public Collection<AnimalReportData> findAllAnimalReportDataByChatId(Long chatId) {
        return animalReportDataRepository.findAllByChatId(chatId);
    }
}
