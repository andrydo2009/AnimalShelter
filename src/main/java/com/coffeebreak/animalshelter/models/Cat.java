package com.coffeebreak.animalshelter.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nick_name", nullable = false, length = 25)
    private String nickName;  // прозвище

    @Column(name = "age")
    private Integer age;  // возраст

    @Column(name = "cat_breed", nullable = false, length = 25)
    private String catBreed;  // порода кошек

    @Column(name = "description")
    private String description;  // описание питомца

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id) && Objects.equals(nickName, cat.nickName) && Objects.equals(age, cat.age) && Objects.equals(catBreed, cat.catBreed) && Objects.equals(description, cat.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, age, catBreed, description);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", age=" + age +
                ", catBreed='" + catBreed + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
