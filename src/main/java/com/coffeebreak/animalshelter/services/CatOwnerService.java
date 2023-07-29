package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogNotFoundException;
import com.coffeebreak.animalshelter.models.CatOwner;
import com.coffeebreak.animalshelter.repositories.CatOwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class CatOwnerService {
    private final CatOwnerRepository catOwnerRepository;

    public CatOwnerService(CatOwnerRepository catOwnerRepository) {
        this.catOwnerRepository = catOwnerRepository;
    }



    public CatOwner updateCatOwner (CatOwner catOwner) {
        if (catOwner.getId() != null) {
            if (findCatOwnerById(catOwner.getId()) != null) { // Нет метода по поиску по ID
                return catOwnerRepository.save(catOwner);
            }
        }
        throw new DogNotFoundException();
    }

    public void deleteCatOwnerById(Long catOwnerId) {
        catOwnerRepository.deleteById(catOwnerId);
    }

}

