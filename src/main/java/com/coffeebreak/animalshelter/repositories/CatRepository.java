package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, содержащий методы для работы с базой данных объектов класса Cat
 * @see Cat
 * @see com.coffeebreak.animalshelter.services.CatService
 */
public interface CatRepository extends JpaRepository<Cat, Long> {
}
