package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Класс для проверки CRUD-операций класса AnimalReportDataService
 * @see AnimalReportData
 * @see AnimalReportDataRepository
 */
@ExtendWith(MockitoExtension.class)
class AnimalReportDataServiceTest {
    @Mock
    private AnimalReportDataRepository animalReportDataRepository;

    @InjectMocks
    private AnimalReportDataService animalReportDataService;

    AnimalReportData reportTestOne = new AnimalReportData();
    AnimalReportData reportTestTwo = new AnimalReportData();
    Collection<AnimalReportData> reportListTest=new ArrayList<>();
    @BeforeEach
    void setUp() {
        reportTestOne.setId(1L);
        reportTestOne.setChatId(1L);
        reportTestOne.setRationOfAnimal("Еда для животного");
        reportTestOne.setHealthOfAnimal("Состояние животного");
        reportTestOne.setHabitsOfAnimal("Привычки животного");
        reportTestOne.setDaysOfOwnership(7L);

        reportTestTwo.setId(2L);
        reportTestTwo.setChatId(2L);
        reportTestTwo.setRationOfAnimal("Еда для животного 2");
        reportTestTwo.setHealthOfAnimal("Состояние животного 2");
        reportTestTwo.setHabitsOfAnimal("Привычки животного 2");
        reportTestTwo.setDaysOfOwnership(8L);

        reportListTest.add(reportTestOne);
        reportListTest.add(reportTestTwo);
    }

    @AfterEach
    public void resetDb() {
        animalReportDataRepository.deleteAll();
    }

    /**
     * Тестирование метода <b>createAnimalReport()</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::save</b>,
     * возвращается объект класса AnimalReportData <b>reportTestOne</b>
     */
    @Test
    void createAnimalReportDataTest() {
        // Устанавливаем поведение mock-репозитория при вызове findById
        when(animalReportDataRepository.save(reportTestOne)).thenReturn(reportTestOne);
        AnimalReportData result = animalReportDataService.createAnimalReportData(reportTestOne);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result,reportTestOne);
        Assertions.assertNotEquals(result,reportTestTwo);
    }

    /**
     * Тестирование метода <b>findById()</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::findById</b>,
     * возвращается объект класса AnimalReportData <b>reportTestOne</b>
     */
    @Test
    void findByIdTest() {
        // Устанавливаем поведение mock-репозитория при вызове findById
        when(animalReportDataRepository.findById(anyLong())).thenReturn(Optional.of(reportTestOne));

        // Выполняем метод findById
        AnimalReportData result = animalReportDataService.findAnimalReportDataById(1L);

        // Проверяем, что возвращенный объект не равен null и имеет ожидаемые значения полей
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(),reportTestOne.getId());
        Assertions.assertEquals(result.getChatId(),reportTestOne.getChatId());
        Assertions.assertEquals(result.getRationOfAnimal(),reportTestOne.getRationOfAnimal());
        Assertions.assertEquals(result.getHealthOfAnimal(),reportTestOne.getHealthOfAnimal());
        Assertions.assertEquals(result.getHabitsOfAnimal(),reportTestOne.getHabitsOfAnimal());
        Assertions.assertEquals(result.getDaysOfOwnership(),reportTestOne.getDaysOfOwnership());
        // и наоборот
        Assertions.assertNotEquals(result,reportTestTwo);
    }

    /**
     * Тестирование метода <b>findAll()</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::findAll</b>,
     * возвращается объект типа Collection класса AnimalReportData
     */
    @Test
    void findAllAnimalReportTest() {
        // Устанавливаем поведение mock-репозитория при вызове findAll
        when(animalReportDataRepository.findAll()).thenReturn(Arrays.asList(reportTestOne, reportTestTwo));
        // Выполняем метод findAllAnimalReport
        Collection<AnimalReportData> result = animalReportDataService.findAllAnimalReport();
        // Проверяем, что возвращенная коллекция содержит ожидаемое количество объектов
        Assertions.assertEquals(reportListTest.size(), result.size());
        Assertions.assertArrayEquals(result.toArray(), reportListTest.toArray());
    }

    /**
     * Тестирование метода <b>updateAnimalReportData</b> в AnimalReportDataService
     * <br>
     * Mockito: когда вызывается метод <b>AnimalReportDataRepository::findById</b>,
     * возвращается объект класса AnimalReportData <b>reportTestOne</b>
     */
    @Test
    void updateAnimalReportDataTest(){
        // Устанавливаем поведение mock-репозитория при вызове findById
        when(animalReportDataRepository.findById(reportTestTwo.getId())).thenReturn(java.util.Optional.of(reportTestTwo));

        // Устанавливаем поведение mock-репозитория при вызове save
        when(animalReportDataRepository.save(reportTestTwo)).thenReturn(reportTestTwo);

        // Выполняем метод updateAnimalReportData
        AnimalReportData result = animalReportDataService.updateAnimalReportData(reportTestTwo);

        // Проверяем, что возвращенный объект не равен null
        Assertions.assertNotNull(result);

        // Проверяем, что объект сохраненный в репозитории равен возвращенному объекту
        Assertions.assertEquals(reportTestTwo, result);
        // и наоборот
        Assertions.assertNotEquals(reportTestOne,result);
    }

    /**
     * Тестирование метода <b>deleteAnimalReportData</b> в AnimalReportDataService
     */
    @Test
    public void deleteAnimalReportDataTest() {
        // Устанавливаем поведение mock-репозитория при вызове findById
        when(animalReportDataRepository.save(reportTestOne)).thenReturn(reportTestOne);
        AnimalReportData result = animalReportDataService.createAnimalReportData(reportTestOne);

        // Выполняем метод deleteAnimalReportData
        animalReportDataService.deleteAnimalReportDataById(result.getId());

        // Проверяем, что метод deleteById был вызван с указанным идентификатором
        Mockito.verify(animalReportDataRepository, Mockito.times(1)).deleteById(result.getId());
        Mockito.verify(animalReportDataRepository, Mockito.never()).deleteById(reportTestTwo.getId());
    }
}
