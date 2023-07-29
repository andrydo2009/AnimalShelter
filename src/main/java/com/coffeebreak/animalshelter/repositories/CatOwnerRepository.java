package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.Cat;
import com.coffeebreak.animalshelter.models.CatOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatOwnerRepository  extends JpaRepository<CatOwner, Long> {
}
