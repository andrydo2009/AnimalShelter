package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogOwnerNotFoundException;
import com.coffeebreak.animalshelter.models.DogOwner;
import com.coffeebreak.animalshelter.repositories.DogOwnerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Класс для проверки CRUD-операций класса DogOwnerService
 * @see DogOwnerService
 * @see DogOwnerRepository
 */
@ExtendWith(MockitoExtension.class)
public class DogOwnerServiceTest {

    @Mock
    private DogOwnerRepository dogOwnerRepositoryMock;

    @InjectMocks
    private DogOwnerService dogOwnerService;

    private final DogOwner expected = new DogOwner();
    private final DogOwner expected1 = new DogOwner();

    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setFullName("Василий Васильевич");
        expected.setAge(45);
        expected.setAddress("Москва");
        expected.setPhoneNumber("98887776655");

        expected1.setId(1L);
        expected1.setFullName("Иван Иванович");
        expected1.setAge(30);
        expected1.setAddress("Рязань");
        expected1.setPhoneNumber("12223334455");
    }


    /**
     * Проверка метода <b>createDogOwner()</b> класса DogOwnerService
     * <br>
     * Когда вызывается метод <b>DogOwnerRepository::save()</b>, возвращается ожидаемый объект класса DogOwner
     */
    @Test
    @DisplayName("Проверка добавления нового владельца собаки и сохранения его в базе данных")
    public void createDogOwnerTest() {
        when(dogOwnerRepositoryMock.save(any(DogOwner.class))).thenReturn(expected);
        DogOwner actual = dogOwnerService.createDogOwner(expected);

        Assertions.assertThat(actual.getFullName()).isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }

    /**
     * Проверка метода <b>findDogOwnerById()</b> класса DogOwnerService
     * <br>
     * Когда вызывается метод <b>DogOwnerRepository::findById()</b>, возвращается ожидаемый объект класса DogOwner
     */
    @Test
    @DisplayName("Проверка поиска хозяина собаки по id")
    void findDogOwnerByIdTest() {
        DogOwner expected = new DogOwner(1L, "testFullName", 30, "testAddress", "testPhoneNumber");

        Mockito.when(dogOwnerRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

        DogOwner actual = dogOwnerService.findDogOwnerById(1L);

        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getFullName()).isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());

        // проверяем, что метод findById() был вызван один раз с нужным аргументом
        Mockito.verify(dogOwnerRepositoryMock, Mockito.times(1)).findById(1L);

        // проверяем, что метод findById() был вызван только один раз
        Mockito.verify(dogOwnerRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
    }


    /**
     * Проверка выбрасывания исключения в методе <b>findDogOwnerById()</b> класса DogOwnerService
     * <br>
     * Когда вызывается метод <b>DogOwnerRepository::findById()</b>, выбрасывается исключение <b>DogOwnerNotFoundException</b>
     */
    @Test
    @DisplayName("Проверка исключения в методе поиска владельца собаки по id")
    public void findDogOwnerByIdExceptionTest() {
        when(dogOwnerRepositoryMock.findById(any(Long.class))).thenThrow(DogOwnerNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(DogOwnerNotFoundException.class, () -> dogOwnerService.findDogOwnerById(1L));
    }

    /**
     * Проверка метода <b>findAllDogOwners()</b> класса DogOwnerService
     * <br>
     * Когда вызывается метод <b>DogOwnerRepository::findAll()</b>, возвращается коллекция ожидаемых объектов класса DogOwner
     */
    @Test
    @DisplayName("Проверка поиска всех владельцев собак")
    public void findAllDogOwnersTest() {
        List<DogOwner> dogOwners = new ArrayList<>();
        dogOwners.add(expected);
        dogOwners.add(expected1);
        when(dogOwnerRepositoryMock.findAll()).thenReturn(dogOwners);
        Collection<DogOwner> actual = dogOwnerService.findAllDogOwners();

        Assertions.assertThat(actual.size()).isEqualTo(dogOwners.size());
        Assertions.assertThat(actual).isEqualTo(dogOwners);
    }

    /**
     * Тестирование метода <b>findAllDogOwners()</b> в DogOwnerService
     * <br>
     * Mockito: когда вызывается метод <b>DogOwnerRepository::findAll</b>,
     * возвращается пустая коллекция владельцев собак <b>DogOwner</b>
     */
    @Test
    @DisplayName("Проверка поиска всех собак и возвращения из базы данных пустого списка")
    public void findAllDogOwnersTestReturnsEmpty() {
        List<DogOwner> dogOwners = new ArrayList<>();
        when(dogOwnerRepositoryMock.findAll()).thenReturn(dogOwners);
        assertThat(dogOwnerService.findAllDogOwners()).isEqualTo(dogOwners);
    }


    /**
     * Тестирование метода <b>updateDogOwner()</b> в DogOwnerService
     * <br>
     * Mockito: когда вызывается метод <b>DogOwnerRepository::findById</b> и <b>DogOwnerRepository::save</b>,
     * возвращается отредактированный владелец собаки <b>expected</b>
     */
    @Test
    @DisplayName("Проверка обновления данных владельца собаки, сохранения и возвращения его из базы данных")
    public void updateDogOwnerTest() {
        when(dogOwnerRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(dogOwnerRepositoryMock.save(any(DogOwner.class))).thenReturn(expected);
        DogOwner actual = dogOwnerService.updateDogOwner(expected);

        Assertions.assertThat(actual.getFullName()).isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }

    /**
     * Тест на создание исключения в методе <b>updateDogOwner()</b> в DogOwnerService
     */
    @Test
    @DisplayName("Проверка исключения в методе редактирования владельца собаки")
    public void updateDogOwnerExceptionTest() {
        when(dogOwnerRepositoryMock.findById(any(Long.class))).thenThrow(DogOwnerNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(DogOwnerNotFoundException.class,
                () -> dogOwnerService.updateDogOwner(expected));
    }
}