package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.models.CatOwner;
import com.coffeebreak.animalshelter.repositories.CatOwnerRepository;
import org.springframework.stereotype.Service;

/**
 * Класс-сервис, содержащий CRUD-методы объекта класса CatOwner
 * @see CatOwner
 * @see CatOwnerRepository
 */
@Service
public class CatOwnerService {
    private final CatOwnerRepository catOwnerRepository;

    public CatOwnerService(CatOwnerRepository catOwnerRepository) {
        this.catOwnerRepository = catOwnerRepository;
    }


//    /**
//     * Изменение объекта класса CatOwner и сохранение его в БД
//     * <br>
//     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
//     * @param catOwner объект класса CatOwner, не может быть null
//     * @return изменённый объект класса CatOwner
//     * @throws CatOwnerNotFoundException если объект класса CatOwner не был найден в БД
//     */
//    public CatOwner updateCatOwner(CatOwner catOwner) {
//        if (catOwner.getId() != null) {
//            if (findCatOwnerById(catOwner.getId()) != null) { // Нет метода по поиску по ID
//                return catOwnerRepository.save(catOwner);
//            }
//        }
//        throw new CatOwnerNotFoundException(); // Нет этого исключения
//    }

    /**
     * Удаление объекта класса CatOwner по его идентификатору
     * <br>
     * Используется метод репозитория {@link org.springframework.data.jpa.repository.JpaRepository#deleteById(Object)}
     * @param catOwnerId идентификатор искомого объекта класса CatOwner, не может быть null
     */
    public void deleteCatOwnerById(Long catOwnerId) {
        catOwnerRepository.deleteById(catOwnerId);
    }
}

