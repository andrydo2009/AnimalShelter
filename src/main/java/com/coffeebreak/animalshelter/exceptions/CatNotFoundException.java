package com.coffeebreak.animalshelter.exceptions;

public class CatNotFoundException extends RuntimeException {
    public CatNotFoundException() {
        super("Кошка не найдена!");
    }
}
