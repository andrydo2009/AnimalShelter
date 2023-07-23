package com.coffeebreak.animalshelter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String filePath; // путь к файлу
    private long fileSize; //размер файла

    public AnimalReportData() {
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        AnimalReportData that = (AnimalReportData) o;
        return fileSize == that.fileSize && Objects.equals ( id , that.id ) && Objects.equals ( chatId , that.chatId ) && Objects.equals ( rationOfAnimal , that.rationOfAnimal ) && Objects.equals ( healthOfAnimal , that.healthOfAnimal ) && Objects.equals ( habitsOfAnimal , that.habitsOfAnimal ) && Objects.equals ( daysOfOwnership , that.daysOfOwnership ) && Objects.equals ( filePath , that.filePath );
    }

    @Override
    public int hashCode() {
        return Objects.hash ( id , chatId , rationOfAnimal , healthOfAnimal , habitsOfAnimal , daysOfOwnership , filePath , fileSize );
    }
}
