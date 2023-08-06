package com.coffeebreak.animalshelter.services;

import com.coffeebreak.animalshelter.exceptions.CatNotFoundException;
import com.coffeebreak.animalshelter.models.Cat;
import com.coffeebreak.animalshelter.repositories.CatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

/**
 * Класс для проверки CRUD-операций класса CatService
 * @see CatService
 * @see CatRepository
 */
@ExtendWith(MockitoExtension.class)
public class CatServiceTest {
    @Mock
    private CatRepository catRepositoryMock;

    @InjectMocks
    private CatService catService;

    /**
     * Проверка метода <b>createCat()</b> класса CatService
     * <br>
     * Когда вызывается метод <b>CatRepository::save()</b>, возвращается ожидаемый объект класса Cat
     */
    @Test
    @DisplayName("Проверка создания кошки и добавления ее в БД")
    public void createCatTest() {
        // Создание нового ожидаемого объекта класса Cat
        Cat expected = new Cat();
        expected.setId(1L);
        expected.setNickName("Barsik");
        expected.setAge(3);
        expected.setCatBreed("Persian");

        // Установка значения для заглушки
        Mockito.when(catRepositoryMock.save(any(Cat.class))).thenReturn(expected);

        // Создание фактического объекта класса Cat
        Cat actual = catService.createCat(expected);

        // Проверка результата
        Assertions.assertEquals(actual.getId(), expected.getId());
        Assertions.assertEquals(actual.getNickName(), expected.getNickName());
        Assertions.assertEquals(actual.getAge(), expected.getAge());
        Assertions.assertEquals(actual.getCatBreed(), expected.getCatBreed());
    }


    /**
     * Проверка метода <b>findCatById()</b> класса CatService
     * <br>
     * Когда вызывается метод <b>CatRepository::findById()</b>, возвращается ожидаемый объект класса Cat
     */
    @Test
    @DisplayName("Проверка поиска кошки по id")
    public void findCatByIdTest() {
        // Создание нового ожидаемого объекта класса Cat
        Cat expected = new Cat();
        expected.setId(1L);
        expected.setNickName("Barsik");
        expected.setAge(3);
        expected.setCatBreed("Persian");

        // Установка значения для заглушки
        Mockito.when(catRepositoryMock.findById(1L)).thenReturn(Optional.of(expected));

        // Вызов метода findCatById
        Optional<Cat> actual = Optional.ofNullable(catService.findCatById(1L));

        // Проверка результата
        Assertions.assertTrue(actual.isPresent());
        Cat cat = actual.get();
        Assertions.assertEquals(cat.getId(), expected.getId());
        Assertions.assertEquals(cat.getNickName(), expected.getNickName());
        Assertions.assertEquals(cat.getAge(), expected.getAge());
        Assertions.assertEquals(cat.getCatBreed(), expected.getCatBreed());
    }

    /**
     * Проверка выбрасывания исключения в методе <b>findCatById()</b> класса CatService
     * <br>
     * Когда вызывается метод <b>CatRepository::findById()</b>, выбрасывается исключение <b>CatNotFoundException</b>
     */
    @Test
    @DisplayName("Проверка выбрасывания исключения при поиске кошки по id")
    public void findCatByIdExceptionTest() {
        // Установка значения для заглушки
        Mockito.when(catRepositoryMock.findById(1L)).thenThrow(CatNotFoundException.class);

        // Проверка выбрасывания исключения
        Assertions.assertThrows(CatNotFoundException.class, () -> catService.findCatById(1L));
    }

    /**
     * Проверка метода <b>findAllCats()</b> класса CatService
     * <br>
     * Когда вызывается метод <b>CatRepository::findAll()</b>, возвращается коллекция ожидаемых объектов класса Cat
     */
    @Test
    @DisplayName("Проверка поиска списка всех кошек")
    public void findAllCatsTest() {
        // Создание списка ожидаемых объектов класса Cat
        List<Cat> expected = new ArrayList<>();
        Cat cat1 = new Cat();
        cat1.setId(1L);
        cat1.setNickName("Barsik");
        cat1.setAge(3);
        cat1.setCatBreed("Persian");
        expected.add(cat1);
        Cat cat2 = new Cat();
        cat2.setId(2L);
        cat2.setNickName("Murka");
        cat2.setAge(2);
        cat2.setCatBreed("Siamese");
        expected.add(cat2);
        Cat cat3 = new Cat();
        cat3.setId(3L);
        cat3.setNickName("Persik");
        cat3.setAge(5);
        cat3.setCatBreed("Scottish Fold");
        expected.add(cat3);

        // Установка значения для заглушки
        Mockito.when(catRepositoryMock.findAll()).thenReturn(expected);

        // Вызов метода findAllCats
        List<Cat> actual = (List<Cat>) catService.findAllCats();

        // Проверка результата
        Assertions.assertEquals(actual.size(), expected.size());
        Assertions.assertEquals(actual, expected);
    }

    /**
     * Проверка метода <b>updateCat()</b> класса CatService
     * <br>
     * Когда вызывается метод <b>CatRepository::save()</b>, возвращается ожидаемый объект класса Cat
     */
    @Test
    @DisplayName("Проверка изменения (обновления) данных кошки и добавления ее в БД")
    public void updateCatTest() {
        // Создание нового ожидаемого объекта класса Cat
        Cat expected = new Cat();
        expected.setId(1L);
        expected.setNickName("Barsik");
        expected.setAge(3);
        expected.setCatBreed("Persian");

        // Установка значения для заглушки
        Mockito.when(catRepositoryMock.findById(1L)).thenReturn(Optional.of(expected));
        Mockito.when(catRepositoryMock.save(expected)).thenReturn(expected);

        // Вызов метода updateCat
        Cat actual = catService.updateCat(expected);

        // Проверка результата
        Assertions.assertEquals(actual.getNickName(), expected.getNickName());
        Assertions.assertEquals(actual.getAge(), expected.getAge());
        Assertions.assertEquals(actual.getCatBreed(), expected.getCatBreed());
    }

    /**
     * Проверка выбрасывания исключения в методе <b>updateCat()</b> класса CatService
     * <br>
     */
    @Test
    @DisplayName("Проверка выбрасывания исключения при изменении (обновлении) данных кошки и добавления ее в БД")
    public void updateCatExceptionTest() {
        // Создание нового ожидаемого объекта класса Cat
        Cat expected = new Cat();
        expected.setId(1L);
        expected.setNickName("Barsik");
        expected.setAge(3);
        expected.setCatBreed("Persian");

        // Установка значения для заглушки
        Mockito.when(catRepositoryMock.findById(1L)).thenThrow(CatNotFoundException.class);

        // Проверка выбрасывания исключения
        Assertions.assertThrows(CatNotFoundException.class, () -> catService.updateCat(expected));
    }
}


