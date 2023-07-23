package com.coffeebreak.animalshelter.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "dog")
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nick_name", nullable = false, length = 25)
    private String nickName;  // прозвище

    @Column(name = "age")
    private Integer age;  // возраст

    @Column(name = "dog_breed", nullable = false, length = 25)
    private String dogBreed;  // порода собак

    @Column(name = "description")
    private String description;  // описание питомца

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dog dog = (Dog) o;
        return Objects.equals(id, dog.id) && Objects.equals(nickName, dog.nickName) && Objects.equals(age, dog.age) && Objects.equals(dogBreed, dog.dogBreed) && Objects.equals(description, dog.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, age, dogBreed, description);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", age=" + age +
                ", dogBreed='" + dogBreed + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
