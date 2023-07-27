package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, содержащий методы для работы с базой данных животных
 * @see Dog
 * @see com.coffeebreak.animalshelter.services.DogService
 */
public interface DogRepository extends JpaRepository<Dog, Long> {

}
