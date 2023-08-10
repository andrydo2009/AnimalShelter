package com.coffeebreak.animalshelter.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalReportData {

    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private String rationOfAnimal; //рацион питания животного
    private String healthOfAnimal; //самочувствие животного и привыкание животного к новому месту
    private String habitsOfAnimal; //привычки животного
    private Long daysOfOwnership; //время владения

    public AnimalReportData() {
    }

    public AnimalReportData(Long id, Long chatId, String rationOfAnimal, String healthOfAnimal, String habitsOfAnimal, Long daysOfOwnership) {
        this.id = id;
        this.chatId = chatId;
        this.rationOfAnimal = rationOfAnimal;
        this.healthOfAnimal = healthOfAnimal;
        this.habitsOfAnimal = habitsOfAnimal;
        this.daysOfOwnership = daysOfOwnership;
    }

    public AnimalReportData(String rationOfAnimal, String healthOfAnimal, String habitsOfAnimal) {
        this.rationOfAnimal = rationOfAnimal;
        this.healthOfAnimal = healthOfAnimal;
        this.habitsOfAnimal = habitsOfAnimal;
    }

    public AnimalReportData(Long chatId, String rationOfAnimal, String healthOfAnimal, String habitsOfAnimal) {
        this.chatId = chatId;
        this.rationOfAnimal = rationOfAnimal;
        this.healthOfAnimal = healthOfAnimal;
        this.habitsOfAnimal = habitsOfAnimal;
    }

    public AnimalReportData(Long chatId) {
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getRationOfAnimal() {
        return rationOfAnimal;
    }

    public void setRationOfAnimal(String rationOfAnimal) {
        this.rationOfAnimal = rationOfAnimal;
    }

    public String getHealthOfAnimal() {
        return healthOfAnimal;
    }

    public void setHealthOfAnimal(String healthOfAnimal) {
        this.healthOfAnimal = healthOfAnimal;
    }

    public String getHabitsOfAnimal() {
        return habitsOfAnimal;
    }

    public void setHabitsOfAnimal(String habitsOfAnimal) {
        this.habitsOfAnimal = habitsOfAnimal;
    }

    public Long getDaysOfOwnership() {
        return daysOfOwnership;
    }

    public void setDaysOfOwnership(Long daysOfOwnership) {
        this.daysOfOwnership = daysOfOwnership;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalReportData that = (AnimalReportData) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(rationOfAnimal, that.rationOfAnimal) && Objects.equals(healthOfAnimal, that.healthOfAnimal) && Objects.equals(habitsOfAnimal, that.habitsOfAnimal) && Objects.equals(daysOfOwnership, that.daysOfOwnership);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, rationOfAnimal, healthOfAnimal, habitsOfAnimal, daysOfOwnership);
    }
}
