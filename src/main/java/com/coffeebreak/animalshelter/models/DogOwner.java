package com.coffeebreak.animalshelter.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dog_owner")
public class DogOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "full_name", nullable = false)
    private String fullName; // полное имя хозяина животного
    @Column(name = "age")
    private Integer age; // возраст хозяина животного
    @Column(name = "address")
    private String address; // адрес хозяина животного
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // контактный номер телефона хозяина животного

    private Long chatId;

    @Enumerated
    private OwnershipStatus status;

    public DogOwner() {
    }

    public DogOwner(Long chatId, String fullName, String phoneNumber) {
        this.chatId = chatId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public DogOwner(Long chatId, String fullName, String phoneNumber, OwnershipStatus status) {
        this.chatId = chatId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public DogOwner(Long chatId, String fullName, String phoneNumber, String address) {
        this.chatId = chatId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public DogOwner(String fullName, Integer age, String address, String phoneNumber) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public DogOwner(Long id, String fullName, Integer age, String address, String phoneNumber, Long chatId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.chatId = chatId;
    }

    public DogOwner(Long id, String fullName, Integer age, String address, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public DogOwner(Long id, String fullName, Integer age, String address, String phoneNumber, Long chatId, OwnershipStatus status) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.chatId = chatId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public OwnershipStatus getStatus() {
        return status;
    }

    public void setStatus(OwnershipStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DogOwner dogOwner = (DogOwner) o;
        return Objects.equals(id, dogOwner.id) && Objects.equals(fullName, dogOwner.fullName) && Objects.equals(age, dogOwner.age) && Objects.equals(address, dogOwner.address) && Objects.equals(phoneNumber, dogOwner.phoneNumber) && Objects.equals(chatId, dogOwner.chatId) && status == dogOwner.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, age, address, phoneNumber, chatId, status);
    }

    @Override
    public String toString() {
        return "DogOwner{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", chatId=" + chatId +
                ", status=" + status +
                '}';
    }
}
