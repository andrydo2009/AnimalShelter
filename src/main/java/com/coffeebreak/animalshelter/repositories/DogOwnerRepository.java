package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogOwnerRepository extends JpaRepository<DogOwner, Long> {
}
