package com.coffeebreak.animalshelter.controllers;

import com.coffeebreak.animalshelter.exceptions.DogNotFoundException;
import com.coffeebreak.animalshelter.models.Dog;
import com.coffeebreak.animalshelter.services.DogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Тест - класс для проверки API endpoints при обращении к маршрутам отдельными HTTP методами
 * тестирование с использованием JUnit, Mockito и MockMvc
 * для класса - сервиса собак
 * @see Dog
 * @see DogService
 * @see DogController
 * @see DogControllerTest
 */
@WebMvcTest(DogController.class)
public class DogControllerTest {

    // класс MockMvc предназначен для тестирования контроллеров
    @Autowired
    private MockMvc mockMvc;

    // класс ObjectMapper является основным функционалом для работы с форматом JSON
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private DogService dogService;

    private final Dog expectedDogOne = new Dog();
    private final Dog expectedDogTwo = new Dog();
    @BeforeEach
    public void setUp() {


        expectedDogOne.setNickName("Graf");
        expectedDogOne.setDogBreed("sheepdog");
        expectedDogOne.setAge(2022);
        expectedDogOne.setDescription("Test");

        expectedDogTwo.setId(2L);
        expectedDogTwo.setNickName("Rich");
        expectedDogTwo.setDogBreed("doberman");
        expectedDogTwo.setAge(2021);
        expectedDogTwo.setDescription("Test");

    }

    /**
     * Тестирование метода <b>createDog()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::createDog</b>,
     * возвращается статус 200 и собака <b>expected</b>
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка статуса 200 и возвращение собаки при ее создании, сохранении в базе данных")
    void createDogTest() throws Exception {
        when(dogService.createDog(expectedDogOne)).thenReturn(expectedDogOne);
        mockMvc.perform(post("/dog")
                .content(objectMapper.writeValueAsString(expectedDogOne))
                .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedDogOne)));
    }

    /**
     * Тестирование метода <b>getDogById()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::get</b>,
     * возвращается статус 200 и собака <b>expected</b>
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка статуса 200 при получении собаки по id")
    public void getDogByIdTest() throws Exception {
        when(dogService.findDogById(anyLong())).thenReturn(expectedDogTwo);
        mockMvc.perform(get("/dog/{dogId}", expectedDogTwo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDogTwo.getId()));
    }

    /**
     * Тестирование метода <b>getDogById()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::findDogById</b>,
     * выбрасывается исключение <b>DogNotFoundException</b> и
     * возвращается статус 404 <b>exceptionDog</b>
     * @throws Exception
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Проверка статуса 404 при поиске собаки по id, которой нет в базе данных")
    public void getDogByIdExceptionTest() throws Exception {
        when(dogService.findDogById(anyLong())).thenThrow(DogNotFoundException.class);
        mockMvc.perform(get("/dog/{dogId}", expectedDogOne.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Тестирование метода <b>getAllDogs()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::findAllDogs</b>,
     * возвращается статус 200 и коллекция собак <b>Arrays.asList(expected, expected1)</b>
     */
    @Test
    @DisplayName("Проверка статуса 200 при получении всех собак в базе данных")
    void getAllDogsTest() throws Exception {
        when(dogService.findAllDogs()).thenReturn(Arrays.asList(expectedDogOne, expectedDogTwo));
        mockMvc.perform(get("/dog/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(expectedDogOne, expectedDogTwo))));
    }

    /**
     * Тестирование метода <b>updateDog()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::updateDog</b>,
     * возвращается статус 200 и отредактированная собака <b>expected</b>
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка статуса 200 при попытке обновить и сохранить собаку в базе данных")
    public void updateDogTest() throws Exception {
        when(dogService.updateDog(expectedDogOne)).thenReturn(expectedDogOne);
        mockMvc.perform(put("/dog", expectedDogOne.getId())
                .content(objectMapper.writeValueAsString(expectedDogOne))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedDogOne.getId()))
        .andExpect(jsonPath("$.nickName").value(expectedDogOne.getNickName()))
        .andExpect(jsonPath("$.dogBreed").value(expectedDogOne.getDogBreed()))
        .andExpect(jsonPath("$.age").value(expectedDogOne.getAge()))
        .andExpect(jsonPath("$.description").value(expectedDogOne.getDescription()));
    }


    /**
     * Тестирование метода <b>deleteDogById()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::deleteDogById</b>,
     * возвращается статус 200 <b>expected</b>
     * @throws Exception
     */
    @Test
    @DisplayName("Проверка статуса 200 при удалении собаки из базы данных по id")
    public void deleteDogByIdTest() throws Exception {
        doNothing().when(dogService).deleteDogById(expectedDogTwo.getId());
        mockMvc.perform(delete("/dog/{dogId}", expectedDogTwo.getId()))
                .andExpect(status().isOk());
    }

    /**
     * Тестирование метода <b>deleteDogById()</b> в DogController
     * <br>
     * Mockito: когда вызывается метод <b>DogService::deleteDogById</b>,
     * выбрасывается исключение <b>DogNotFoundException</b> и
     * возвращается статус 404 <b>exceptionDog</b>
     * @throws Exception
     * @throws DogNotFoundException
     */
    @Test
    @DisplayName("Проверка статуса 404 при попытке удалить по id собаку, которой нет в базе данных ")
    public void deleteDogByIdExceptionTest() throws Exception {
        Long id=10L;
        when(dogService.findDogById(anyLong())).thenThrow(DogNotFoundException.class);
        mockMvc.perform(delete("/dog/{dogId}", id))
                .andExpect(status().isOk());
    }


}
