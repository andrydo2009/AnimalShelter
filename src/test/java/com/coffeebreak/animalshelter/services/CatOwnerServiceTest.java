package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.CatNotFoundException;
import com.coffeebreak.animalshelter.exceptions.CatOwnerNotFoundException;
import com.coffeebreak.animalshelter.models.CatOwner;
import com.coffeebreak.animalshelter.repositories.CatOwnerRepository;
import org.assertj.core.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CatOwnerServiceTest {

    @Mock
    CatOwnerRepository catOwnerRepositoryMock;

    @InjectMocks
    private CatOwnerService catOwnerService;

    @Test
    @DisplayName("Проверка создания владельца кошки и добавления его в БД")
    void createCatOwnerTest() {
        CatOwner expected = new CatOwner(1L, "testFullName", 30, "testAddress", "testPhoneNumber");

        Mockito.when(catOwnerRepositoryMock.save(any(CatOwner.class))).thenReturn(expected);

        CatOwner actual = catOwnerService.createCatOwner(expected);

        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getFullName()).isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }

    @Test
    @DisplayName("Проверка поиска хозяина кошки по id")
    void findCatOwnerByIdTest() {
        CatOwner expected = new CatOwner(1L, "testFullName", 30, "testAddress", "testPhoneNumber");

        Mockito.when(catOwnerRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));

        CatOwner actual = catOwnerService.findCatOwnerById(1L);

        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getFullName()).isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }

    @Test
    @DisplayName("Проверка выбрасывания исключения при поиске хозяина кошки по id")
    void findCatOwnerByIdExceptionTest() {
        Mockito.when(catOwnerRepositoryMock.findById(any(Long.class))).thenThrow(CatNotFoundException.class);

        org.junit.jupiter.api.Assertions.assertThrows(CatNotFoundException.class, () -> catOwnerService.findCatOwnerById(1L));
    }

    @Test
    @DisplayName("Проверка поиска списка всех хозяев кошек")
    void findAllCatOwnersTest() {
        List<CatOwner> expected = new ArrayList<>();

        CatOwner catOwnerTest1 = new CatOwner(1L, "testFullName1", 30, "testAddress1", "testPhoneNumber1");
        expected.add(catOwnerTest1);
        CatOwner catOwnerTest2 = new CatOwner(2L, "testFullName2", 30, "testAddress2", "testPhoneNumber2");
        expected.add(catOwnerTest2);
        CatOwner catOwnerTest3 = new CatOwner(3L, "testFullName3", 30, "testAddress3", "testPhoneNumber3");
        expected.add(catOwnerTest3);

        Mockito.when(catOwnerRepositoryMock.findAll()).thenReturn(expected);

        Collection<CatOwner> actual = catOwnerService.findAllCatOwners();

        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Проверка изменения (обновления) данных владельца кошки и добавления его в БД")
    void updateCatOwnerTest() {
        CatOwner expected = new CatOwner(1L, "testFullName", 30, "testAddress", "testPhoneNumber");

        Mockito.when(catOwnerRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(expected));
        Mockito.when(catOwnerRepositoryMock.save(any(CatOwner.class))).thenReturn(expected);

        CatOwner actual = catOwnerService.updateCatOwner(expected);

        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getFullName()).isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        Assertions.assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }

    @Test
    @DisplayName("Проверка выбрасывания исключения при изменении (обновлении) данных владельца кошки и добавления его в БД")
    void updateCatOwnerExceptionTest() {
        CatOwner expected = new CatOwner();

        org.junit.jupiter.api.Assertions.assertThrows(CatOwnerNotFoundException.class, () -> catOwnerService.updateCatOwner(expected));
    }
}
