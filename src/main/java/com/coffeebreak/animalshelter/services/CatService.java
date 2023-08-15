package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.CatNotFoundException;
import com.coffeebreak.animalshelter.models.Cat;
import com.coffeebreak.animalshelter.repositories.CatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс-сервис, содержащий CRUD-методы объекта класса Cat
 * @see Cat
 * @see CatRepository
 */
@Service
public class CatService {

    private final CatRepository catRepository;

    private static final Logger logger = LoggerFactory.getLogger(CatService.class);

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * Создание объекта класса Cat и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param cat объект класса Cat, не может быть null
     * @return созданный объект класса Cat
     */
    public Cat createCat(Cat cat) {
        logger.info("Create cat method was invoked");
        catRepository.save(cat);
        logger.info("Cat {} was created successfully", cat);
        return cat;
    }

    /**
     * Поиск объекта класса Cat по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param catId идентификатор искомого объекта класса Cat, не может быть null
     * @return найденный объект класса Cat
     * @throws CatNotFoundException если объект класса Cat не был найден в БД
     */
    public Cat findCatById(Long catId) {
        logger.info("Find cat by id = {} method was invoked", catId);
        Cat cat = catRepository.findById(catId).orElseThrow(CatNotFoundException::new);
        logger.info("Cat with id = {} was successfully found", catId);
        return cat;
    }

    /**
     * Получение коллекции объектов класса Cat из БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findAll()}
     * @return коллекция объектов класса Cat
     */
    public Collection<Cat> findAllCats() {
        logger.info("Find all cats method was invoked");
        Collection<Cat> cats = catRepository.findAll();
        logger.info("All cats were successfully found");
        return cats;
    }

    /**
     * Изменение объекта класса Cat и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param cat объект класса Cat, не может быть null
     * @return изменённый объект класса Cat
     * @throws CatNotFoundException если объект класса Cat не был найден в БД
     */
    public Cat updateCat(Cat cat) {
        logger.info("Update cat: {} method was invoked", cat);
        if (cat.getId() != null) {
            if (findCatById(cat.getId()) != null) {
                catRepository.save(cat);
                logger.info("Cat {} was updated successfully", cat);
                return cat;
            }
        }
        throw new CatNotFoundException();
    }

    /**
     * Удаление объекта класса Cat по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     * @param catId идентификатор искомого объекта класса Cat, не может быть null
     */
    public void deleteCatById(Long catId) {
        logger.info("Delete cat by id = {} method was invoked", catId);
        catRepository.deleteById(catId);
        logger.info("Cat with id = {} was deleted successfully", catId);
    }
}
