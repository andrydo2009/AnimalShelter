package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс, содержащий методы для работы с базой данных объектов класса CatOwner
 * @see CatOwner
 * @see com.coffeebreak.animalshelter.services.CatOwnerService
 */
public interface CatOwnerRepository  extends JpaRepository<CatOwner, Long> {
}
