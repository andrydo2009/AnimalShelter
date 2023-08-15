package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogOwnerNotFoundException;
import com.coffeebreak.animalshelter.models.DogOwner;
import com.coffeebreak.animalshelter.repositories.DogOwnerRepository;
import com.coffeebreak.animalshelter.repositories.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс-сервис, содержащий CRUD-методы объекта класса DogOwner
 * @see DogOwner
 * @see DogOwnerRepository
 */
@Service
public class DogOwnerService {
    private final DogOwnerRepository dogOwnerRepository;

    private static final Logger logger = LoggerFactory.getLogger(DogOwnerService.class);

    public DogOwnerService(DogOwnerRepository dogOwnerRepository) {
        this.dogOwnerRepository = dogOwnerRepository;
    }

    /**
     * Создание объекта класса DogOwner и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param dogOwner объект класса DogOwner, не может быть null
     * @return созданный объект класса DogOwner
     */
    public DogOwner createDogOwner(DogOwner dogOwner) {
        logger.info("Create dog owner method was invoked");
        dogOwnerRepository.save(dogOwner);
        logger.info("Dog owner {} was created successfully", dogOwner);
        return dogOwner;
    }

    /**
     * Поиск объекта класса DogOwner по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param dogOwnerId идентификатор искомого объекта класса DogOwner, не может быть null
     * @return найденный объект класса DogOwner
     * @throws DogOwnerNotFoundException если объект класса DogOwner не был найден в БД
     */
    public DogOwner findDogOwnerById(Long dogOwnerId) {
        logger.info("Find dog owner by id = {} method was invoked", dogOwnerId);
        DogOwner dogOwner = dogOwnerRepository.findById(dogOwnerId).orElseThrow(DogOwnerNotFoundException::new);
        logger.info("Dog owner with id = {} was successfully found", dogOwnerId);
        return dogOwner;
    }

    /**
     * Поиск объекта класса DogOwner по идентификатору чата
     * <br>
     * Используется метод репозитория {@link DogOwnerRepository#findByChatId(Long)}
     * @param chatId идентификатор искомого объекта класса DogOwner, не может быть null
     * @return найденный объект класса DogOwner
     */
    public DogOwner findDogOwnerByChatId(Long chatId) {
        logger.info("Find dog owner by chat id = {} method was invoked", chatId);
        DogOwner dogOwner = dogOwnerRepository.findByChatId(chatId);
        logger.info("Dog owner with chat id = {} was successfully found", chatId);
        return dogOwner;
    }

    /**
     * Получение коллекции объектов класса DogOwner из БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findAll()}
     * @return коллекция объектов класса DogOwner
     */
    public Collection<DogOwner> findAllDogOwners() {
        logger.info("Find all dog owners method was invoked");
        Collection<DogOwner> dogOwners = dogOwnerRepository.findAll();
        logger.info("All dog owners were successfully found");
        return dogOwners;
    }

    /**
     * Изменение объекта класса DogOwner и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param dogOwner объект класса DogOwner, не может быть null
     * @return изменённый объект класса DogOwner
     * @throws DogOwnerNotFoundException если объект класса DogOwner не был найден в БД
     */
    public DogOwner updateDogOwner(DogOwner dogOwner) {
        logger.info("Update dog owner: {} method was invoked", dogOwner);
        if (dogOwner.getId() != null) {
            if (findDogOwnerById(dogOwner.getId()) != null) {
                dogOwnerRepository.save(dogOwner);
                logger.info("Dog owner {} was updated successfully", dogOwner);
                return dogOwner;
            }
        }
        throw new DogOwnerNotFoundException();
    }

    /**
     * Удаление объекта класса DogOwner по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     * @param dogOwnerId идентификатор искомого объекта класса DogOwner, не может быть null
     */
    public void deleteDogOwnerById(Long dogOwnerId) {
        logger.info("Delete dog owner by id = {} method was invoked", dogOwnerId);
        dogOwnerRepository.deleteById(dogOwnerId);
        logger.info("Dog owner with id = {} was deleted successfully", dogOwnerId);
    }
}
