package com.coffeebreak.animalshelter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pet_dog")
public class petDog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id", nullable = false)
    private Long dogId;

    @Column(name = " nick_name ", nullable = false, length = 25)
    private String nickName;  // прозвище

    @Column(name = "year_birth")
    private int yearOfBirth;  // год рождения

    @Column(name = "breed_dog", nullable = false, length = 25)
    private String breedDog;  // порода собак

    @Column(name = "description")
    private String description;  // описание питомца

}
