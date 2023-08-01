package com.coffeebreak.animalshelter.controllers;

import com.coffeebreak.animalshelter.models.AnimalReportData;
import com.coffeebreak.animalshelter.services.AnimalReportDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/report")
@Tag(name = "AnimalReportData", description = "CRUD-операции для работы с отчетами.")
public class AnimalReportDataController {
    private  final AnimalReportDataService animalReportDataService;

    public AnimalReportDataController(AnimalReportDataService animalReportDataService) {
        this.animalReportDataService = animalReportDataService;
    }

    @PostMapping
    public ResponseEntity<AnimalReportData> createAnimalReport(@RequestBody AnimalReportData animalReportData) {
        AnimalReportData createdAnimalReportData = animalReportDataService.createAnimalReportData ( animalReportData );
        return ResponseEntity.ok(createdAnimalReportData);
    }

    @GetMapping
    public ResponseEntity<Collection<AnimalReportData>> getAllDAnimalReportData() {
        Collection<AnimalReportData> animalReportDataAll = animalReportDataService.findAllAnimalReport (  );
        if (animalReportDataAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(animalReportDataAll);
    }

    @PutMapping
    public ResponseEntity<AnimalReportData> updateAnimalReportData(@RequestBody AnimalReportData animalReportData) {
        AnimalReportData updatedAnimalReportData = animalReportDataService.updateAnimalReportData ( animalReportData );
        if (updatedAnimalReportData == null) {
            return ResponseEntity.notFound().build ();
        }
        return ResponseEntity.ok(updatedAnimalReportData);
    }

    @DeleteMapping("/{animalReportDataId}")
    public ResponseEntity<Void> deleteAnimalReportDataById(@PathVariable("animalReportDataId") Long animalReportData) {
        animalReportDataService.deleteAnimalReportData (animalReportData);
        return ResponseEntity.ok().build();
    }

}
