package com.coffeebreak.animalshelter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "pet_cat")
public class petCat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id", nullable = false)
    private Long catId;

    @Column(name = " nick_name ", nullable = false, length = 25)
    private String nickName;  // прозвище

    @Column(name = "year_birth")
    private int yearOfBirth;  // год рождения

    @Column(name = "breed_cat", nullable = false, length = 25)
    private String breedCat;  // порода кошек

    @Column(name = "description")
    private String description;  // описание питомца


}
