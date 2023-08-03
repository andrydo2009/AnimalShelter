package com.coffeebreak.animalshelter.controllers;

import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.repositories.AnimalReportDataRepository;
import com.coffeebreak.animalshelter.services.AnimalReportDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

@WebMvcTest(AnimalReportDataController.class)
class AnimalReportDataControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AnimalReportDataRepository animalReportDataRepository;
    @MockBean
    private AnimalReportDataService animalReportDataService;
    @Autowired
    private MockMvc mockMvc;

    AnimalReportData reportTestOne = new AnimalReportData();

    @AfterEach
    public void resetDb() {
        animalReportDataRepository.deleteAll();
    }

    @BeforeEach
    void setUp(){
        reportTestOne.setId(1L);
        reportTestOne.setChatId(1L);
        reportTestOne.setRationOfAnimal("Еда для животного");
        reportTestOne.setHealthOfAnimal("Состояние животного");
        reportTestOne.setHabitsOfAnimal("Привычки животного");
        reportTestOne.setDaysOfOwnership(7L);
        reportTestOne.setFilePath("/path/to/file1");
        reportTestOne.setFileSize(1024L);
        reportTestOne.setData(new byte[]{1, 2, 3});
        reportTestOne.setCaption("Описание отчета");
        reportTestOne.setLastMessage(new Date());
        reportTestOne.setLastMessageMs(1590879578000L);
    }

    @Test
    void postAnimalReportDataById_shouldReturnOk() throws Exception {

    }
}