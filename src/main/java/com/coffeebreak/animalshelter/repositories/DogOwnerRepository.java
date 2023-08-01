package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, содержащий методы для работы с базой данных объектов класса DogOwner
 * @see DogOwner
 * @see com.coffeebreak.animalshelter.services.DogOwnerService
 */
public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
}
