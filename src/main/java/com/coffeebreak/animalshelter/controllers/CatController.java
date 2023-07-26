package com.coffeebreak.animalshelter.controllers;

import com.coffeebreak.animalshelter.models.Cat;
import com.coffeebreak.animalshelter.services.CatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/cat")
@Tag(name = "Cats", description = "CRUD-операции для работы с кошками")
public class CatController {
    private final CatService catService;
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping
    @Operation(
            summary = "Найти список всех кошек",
            description = "Показать список всех кошек"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Кошки успешно найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Cat.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Кошки не найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = Cat.class))
                            )
                    }
            )
    })
    public ResponseEntity<Collection<Cat>> getAllCats() {
        Collection<Cat> cats = catService.findAllCats();
        if (cats == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cats);
    }
}
