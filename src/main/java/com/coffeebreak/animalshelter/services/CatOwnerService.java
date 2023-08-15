package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.CatOwnerNotFoundException;
import com.coffeebreak.animalshelter.models.CatOwner;
import com.coffeebreak.animalshelter.repositories.CatOwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс-сервис, содержащий CRUD-методы объекта класса CatOwner
 * @see CatOwner
 * @see CatOwnerRepository
 */
@Service
public class CatOwnerService {

    private final CatOwnerRepository catOwnerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CatOwnerService.class);

    public CatOwnerService(CatOwnerRepository catOwnerRepository) {
        this.catOwnerRepository = catOwnerRepository;
    }

    /**
     * Создание объекта класса CatOwner и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param catOwner объект класса CatOwner, не может быть null
     * @return созданный объект класса CatOwner
     */
    public CatOwner createCatOwner(CatOwner catOwner) {
        logger.info("Create cat owner method was invoked");
        catOwnerRepository.save(catOwner);
        logger.info("Cat owner {} was created successfully", catOwner);
        return catOwner;
    }

    /**
     * Поиск объекта класса CatOwner по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param catOwnerId идентификатор искомого объекта класса CatOwner, не может быть null
     * @return найденный объект класса CatOwner
     * @throws CatOwnerNotFoundException если объект класса CatOwner не был найден в БД
     */
    public CatOwner findCatOwnerById(Long catOwnerId) {
        logger.info("Find cat owner by id = {} method was invoked", catOwnerId);
        CatOwner catOwner = catOwnerRepository.findById(catOwnerId).orElseThrow(CatOwnerNotFoundException::new);
        logger.info("Cat owner with id = {} was successfully found", catOwnerId);
        return catOwner;
    }

    /**
     * Поиск объекта класса CatOwner по идентификатору чата
     * <br>
     * Используется метод репозитория {@link CatOwnerRepository#findByChatId(Long)}
     * @param chatId идентификатор искомого объекта класса CatOwner, не может быть null
     * @return найденный объект класса CatOwner
     */
    public CatOwner findCatOwnerByChatId(Long chatId) {
        logger.info("Find cat owner by chat id = {} method was invoked", chatId);
        CatOwner catOwner = catOwnerRepository.findByChatId(chatId);
        logger.info("Cat owner with chat id = {} was successfully found", chatId);
        return catOwner;
    }

    /**
     * Получение коллекции объектов класса CatOwner из БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findAll()}
     * @return коллекция объектов класса CatOwner
     */
    public Collection<CatOwner> findAllCatOwners() {
        logger.info("Find all cat owners method was invoked");
        Collection<CatOwner> catOwners = catOwnerRepository.findAll();
        logger.info("All cat owners were successfully found");
        return catOwners;
    }

    /**
     * Изменение объекта класса CatOwner и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param catOwner объект класса CatOwner, не может быть null
     * @return изменённый объект класса CatOwner
     * @throws CatOwnerNotFoundException если объект класса CatOwner не был найден в БД
     */
    public CatOwner updateCatOwner(CatOwner catOwner) {
        logger.info("Update cat owner: {} method was invoked", catOwner);
        if (catOwner.getId() != null) {
            if (findCatOwnerById(catOwner.getId()) != null) {
                catOwnerRepository.save(catOwner);
                logger.info("Cat owner {} was updated successfully", catOwner);
                return catOwner;
            }
        }
        throw new CatOwnerNotFoundException();
    }

    /**
     * Удаление объекта класса CatOwner по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     * @param catOwnerId идентификатор искомого объекта класса CatOwner, не может быть null
     */
    public void deleteCatOwnerById(Long catOwnerId) {
        logger.info("Delete cat owner by id = {} method was invoked", catOwnerId);
        catOwnerRepository.deleteById(catOwnerId);
        logger.info("Cat owner with id = {} was deleted successfully", catOwnerId);
    }
}

