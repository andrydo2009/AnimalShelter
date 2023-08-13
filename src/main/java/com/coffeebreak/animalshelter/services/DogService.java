package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogNotFoundException;
import com.coffeebreak.animalshelter.models.Dog;
import com.coffeebreak.animalshelter.repositories.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Класс-сервис, содержащий CRUD-методы объекта класса Dog
 * @see Dog
 * @see DogRepository
 */
@Service
public class DogService {

    private final DogRepository dogRepository;

    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * Создание объекта класса Dog и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param dog объект класса Dog, не может быть null
     * @return созданный объект класса Dog
     */
    public Dog createDog(Dog dog) {
        logger.info("Create dog method was invoked");
        dogRepository.save(dog);
        logger.info("Dog {} was created successfully", dog);
        return dog;
    }

    /**
     * Поиск объекта класса Dog по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findById(Object)}
     * @param dogId идентификатор искомого объекта класса Dog, не может быть null
     * @return найденный объект класса Dog
     * @throws DogNotFoundException если объект класса Dog не был найден в БД
     */
    public Dog findDogById(Long dogId) {
        logger.info("Find dog by id = {} method was invoked", dogId);
        Dog dog = dogRepository.findById(dogId).orElseThrow(DogNotFoundException::new);
        logger.info("Dog with id = {} was successfully found", dogId);
        return dog;
    }

    /**
     * Получение коллекции объектов класса Dog из БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#findAll()}
     * @return коллекция объектов класса Dog
     */
    public Collection<Dog> findAllDogs() {
        logger.info("Find all dogs method was invoked");
        Collection<Dog> dogs = dogRepository.findAll();
        logger.info("All dogs were successfully found");
        return dogs;
    }

    /**
     * Изменение объекта класса Dog и сохранение его в БД
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     * @param dog объект класса Dog, не может быть null
     * @return изменённый объект класса Dog
     * @throws DogNotFoundException если объект класса Dog не был найден в БД
     */
    public Dog updateDog(Dog dog) {
        logger.info("Update dog: {} method was invoked", dog);
        if (dog.getId() != null) {
            if (findDogById(dog.getId()) != null) {
                dogRepository.save(dog);
                logger.info("Dog {} was updated successfully", dog);
                return dog;
            }
        }
        throw new DogNotFoundException();
    }

    /**
     * Удаление объекта класса Dog по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     * @param dogId идентификатор искомого объекта класса Dog, не может быть null
     */
    public void deleteDogById(Long dogId) {
        logger.info("Delete dog by id = {} method was invoked", dogId);
        dogRepository.deleteById(dogId);
        logger.info("Dog with id = {} was deleted successfully", dogId);
    }
}
