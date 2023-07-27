package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogNotFoundException;
import com.coffeebreak.animalshelter.models.Cat;
import com.coffeebreak.animalshelter.models.Dog;
import com.coffeebreak.animalshelter.repositories.DogRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 *Класс - сервис, содержащий набор CRUD операций над объектом Dog
 * @see Dog
 * @see DogRepository
 */
@Service
public class DogService {
    private final DogRepository dogRepository;
    private long lastId = 0;
    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * Метод создает новую собаку
     * @param dog
     * @return {@link DogRepository#save(Object)}
     * @see DogService
     */
    public Dog createDog (Dog dog) {
        dog.setId(++lastId);
        return dogRepository.save(dog);
    }

    /**
     * Метод находит и возвращает собаку по id
     * @param id
     * @return {@link DogRepository#findById(Object)}
     * @throws DogNotFoundException если собака с указанным id не найдена
     * @see DogService
     */
    public Dog getDogById(Long id) {
        return dogRepository.findById(id)
                .orElseThrow(DogNotFoundException::new);
    }

    /**
     * Метод находит и удаляет собаку по id
     * @param id
     * @return true если удаление прошло успешно
     * @throws DogNotFoundException если собака с указанным id не найдена
     */
    public boolean remove(Long id) {
        if (dogRepository.existsById(id)) {
            if (dogRepository.getReferenceById(id).getId() != null) {
                dogRepository.getReferenceById(id).getId().longValue();
            }
            dogRepository.deleteById(id);
            return true;
        }
        throw new DogNotFoundException();
    }

    /**
     * Метод обновляет собаку
     * @param dog
     * @return {@link DogRepository#save(Object)}
     * @throws DogNotFoundException если собака с указанным id не найдена
     * @see DogService
     */
    public Dog editDog(Dog dog) {
        if (dog.getId() != null && getDogById(dog.getId()) != null) {
            Dog findDog = getDogById(dog.getId());
            findDog.setNickName(dog.getNickName());
            findDog.setDogBreed(dog.getDogBreed());
            findDog.setAge(dog.getAge());
            findDog.setDescription(dog.getDescription());
            return this.dogRepository.save(findDog);
        }
        throw new DogNotFoundException();
    }

    /**
     * Метод находит и возвращает всех собак
     * @return {@link DogRepository#findById(Object)}
     * @see DogService
     */
    public Collection<Dog> getAllDog() {
        return this.dogRepository.findAll();
    }

}
