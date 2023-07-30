package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogOwnerNotFoundException;
import com.coffeebreak.animalshelter.models.DogOwner;
import com.coffeebreak.animalshelter.repositories.DogOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DogOwnerService {
    private final DogOwnerRepository dogOwnerRepository;

    public DogOwnerService(DogOwnerRepository dogOwnerRepository) {
        this.dogOwnerRepository = dogOwnerRepository;
    }

    public DogOwner createDogOwner(DogOwner dogOwner) {
        return dogOwnerRepository.save ( dogOwner );
    }

    public DogOwner findDogOwnerById(Long dogOwnerId) {
        return dogOwnerRepository.findById ( dogOwnerId ).orElseThrow ( DogOwnerNotFoundException::new );
    }

    public Collection<DogOwner> findAllDogOwner() {
        return dogOwnerRepository.findAll ();
    }

    public void deleteDogOwner(Long dogOwnerId) {
        dogOwnerRepository.deleteById ( dogOwnerId );
    }

    public DogOwner updateDogOwner(DogOwner dogOwner) {
        if (dogOwner.getId () != null) {
            if (findDogOwnerById ( dogOwner.getId () ) != null) {
                return dogOwnerRepository.save ( dogOwner );
            }
        }
        throw new DogOwnerNotFoundException ();
    }
}
