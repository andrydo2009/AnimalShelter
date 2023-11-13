package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.DogNotFoundException;
import com.coffeebreak.animalshelter.models.Dog;
import com.coffeebreak.animalshelter.repositories.DogRepository;
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
 * Класс для проверки CRUD-операций класса DogService
 * @see DogService
 * @see DogRepository
 */
@ExtendWith(MockitoExtension.class)
public class DogServiceTest {
    @Mock
    private DogRepository dogRepository;
    @InjectMocks
    private DogService dogService;

    private final Dog expected = new Dog();
    private final Dog expected1 = new Dog();
    @BeforeEach
    public void setUp() {
        expected.setId(1L);
        expected.setNickName("Graf");
        expected.setDogBreed("sheepdog");
        expected.setAge(1);
        expected.setDescription("Test");

        expected1.setId(2L);
        expected1.setNickName("Rich");
        expected1.setDogBreed("doberman");
        expected1.setAge(2);
        expected1.setDescription("Test");
    }

    /**
     * Проверка метода <b>createDog()</b> класса DogService
     * <br>
     * Когда вызывается метод <b>DogRepository::save()</b>, возвращается ожидаемый объект класса Dog
     */
    @Test
    @DisplayName("Проверка добавления собаки и сохранения ее в БД")
    public void createDogTest() {
        when(dogRepository.save(any(Dog.class))).thenReturn(expected);
        Dog actual = dogService.createDog(expected);

        Assertions.assertThat(actual.getNickName()).isEqualTo(expected.getNickName());
        Assertions.assertThat(actual.getDogBreed()).isEqualTo(expected.getDogBreed());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Проверка метода <b>findDogById()</b> класса DogService
     * <br>
     * Когда вызывается метод <b>DogRepository::findById()</b>, возвращается ожидаемый объект класса Dog
     */
    @Test
    @DisplayName("Проверка поиска собаки по id")
    void findDogByIdTest() {
        Mockito.when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Dog actual = dogService.findDogById(1L);

        Assertions.assertThat(actual.getId()).isEqualTo(expected.getId());
        Assertions.assertThat(actual.getNickName()).isEqualTo(expected.getNickName());
        Assertions.assertThat(actual.getDogBreed()).isEqualTo(expected.getDogBreed());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());

        // проверяем, что метод findById() был вызван один раз с нужным аргументом
        Mockito.verify(dogRepository, Mockito.times(1)).findById(1L);

        // проверяем, что метод findById() был вызван только один раз
        Mockito.verify(dogRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    /**
     * Проверка выбрасывания исключения в методе <b>findDogById()</b> класса DogService
     * <br>
     * Когда вызывается метод <b>DogRepository::findById()</b>, выбрасывается исключение <b>DogNotFoundException</b>
     */
    @Test
    @DisplayName("Проверка выбрасывания исключения при поиске собаки по id")
    public void findDogByIdExceptionTest() {
        when(dogRepository.findById(any(Long.class))).thenThrow(DogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(DogNotFoundException.class, () -> dogService.findDogById(1L));
    }

    /**
     * Проверка метода <b>findAllDogs()</b> класса DogService
     * <br>
     * Когда вызывается метод <b>DogRepository::findAll()</b>, возвращается коллекция ожидаемых объектов класса Dog
     */
    @Test
    @DisplayName("Проверка поиска списка всех собак")
    public void findAllDogsTest() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(expected);
        dogs.add(expected1);
        when(dogRepository.findAll()).thenReturn(dogs);
        Collection<Dog> actual = dogService.findAllDogs();

        Assertions.assertThat(actual.size()).isEqualTo(dogs.size());
        Assertions.assertThat(actual).isEqualTo(dogs);
    }

    /**
     * Проверка метода <b>findAllDogs()</b> класса DogService
     * <br>
     * Когда вызывается метод <b>DogRepository::findAll()</b>, возвращается пустая коллекция ожидаемых объектов класса Dog
     */
    @Test
    @DisplayName("Проверка поиска всех собак и возвращения из базы данных пустого списка")
    public void findAllDogsTestReturnsEmpty() {
        List<Dog> dogs = new ArrayList<>();
        when(dogRepository.findAll()).thenReturn(dogs);
        assertThat(dogService.findAllDogs()).isEqualTo(dogs);
    }

    /**
     * Проверка метода <b>updateDog()</b> класса DogService
     * <br>
     * Когда вызывается метод <b>DogRepository::save()</b>, возвращается ожидаемый объект класса Dog
     */
    @Test
    @DisplayName("Проверка изменения (обновления) данных собаки, сохранения и возвращения ее из базы данных")
    public void updateDogTest() {
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));
        when(dogRepository.save(any(Dog.class))).thenReturn(expected);
        Dog actual = dogService.updateDog(expected);

        Assertions.assertThat(actual.getNickName()).isEqualTo(expected.getNickName());
        Assertions.assertThat(actual.getDogBreed()).isEqualTo(expected.getDogBreed());
        Assertions.assertThat(actual.getAge()).isEqualTo(expected.getAge());
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
    }

    /**
     * Проверка выбрасывания исключения в методе <b>updateDog()</b> класса DogService
     */
    @Test
    @DisplayName("Проверка исключения в методе редактирования собаки")
    public void updateDogExceptionTest() {
        when(dogRepository.findById(any(Long.class))).thenThrow(DogNotFoundException.class);
        org.junit.jupiter.api.Assertions.assertThrows(DogNotFoundException.class,
                () -> dogService.updateDog(expected));
    }
}
