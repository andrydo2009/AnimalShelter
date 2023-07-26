package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.models.Cat;
import com.coffeebreak.animalshelter.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }
    public Cat createCat(Cat cat) {
        return catRepository.save(cat);
    }

    public Cat getCatById(Long id) {
        return catRepository.findById(id).orElse(null);
    }
}
