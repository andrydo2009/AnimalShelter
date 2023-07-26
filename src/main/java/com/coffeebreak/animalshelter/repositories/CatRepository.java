package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
}
