package com.coffeebreak.animalshelter.exceptions;

public class DogOwnerNotFoundException extends RuntimeException{
    public DogOwnerNotFoundException() {
        super("Хозяин собаки не найден!");
    }
}
