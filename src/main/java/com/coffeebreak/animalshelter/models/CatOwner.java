package com.coffeebreak.animalshelter.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cat_owner")
public class CatOwner {
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

    public CatOwner() {
    }

    public CatOwner(Long chatId, String fullName, String phoneNumber) {
        this.chatId = chatId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public CatOwner(Long chatId, String fullName, String phoneNumber, OwnershipStatus status) {
        this.chatId = chatId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public CatOwner(Long chatId, String fullName, String phoneNumber, String address) {
        this.chatId = chatId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public CatOwner(String fullName, Integer age, String address, String phoneNumber) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public CatOwner(Long id, String fullName, Integer age, String address, String phoneNumber, Long chatId) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.chatId = chatId;
    }

    public CatOwner(Long id, String fullName, Integer age, String address, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public CatOwner(Long id, String fullName, Integer age, String address, String phoneNumber, Long chatId, OwnershipStatus status) {
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatOwner catOwner = (CatOwner) o;
        return Objects.equals(id, catOwner.id) && Objects.equals(fullName, catOwner.fullName) && Objects.equals(age, catOwner.age) && Objects.equals(address, catOwner.address) && Objects.equals(phoneNumber, catOwner.phoneNumber) && Objects.equals(chatId, catOwner.chatId) && status == catOwner.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, age, address, phoneNumber, chatId, status);
    }

    @Override
    public String toString() {
        return "CatOwner{" +
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
