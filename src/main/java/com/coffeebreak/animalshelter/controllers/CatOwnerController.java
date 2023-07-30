package com.coffeebreak.animalshelter.controllers;

import com.coffeebreak.animalshelter.models.CatOwner;

import com.coffeebreak.animalshelter.services.CatOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cat_owner")
@Tag(name = "Cat owner", description = "CRUD-операции для работы с владельцами кошек")

public class CatOwnerController {
    private final CatOwnerService catOwnerService;

    public CatOwnerController(CatOwnerService catOwnerService) {
        this.catOwnerService = catOwnerService;
    }

    @PutMapping
    @Operation(
            summary = "Изменить (обновить) данные владельца кошки",
            description = "Изменение (обновление) данных владельца кошки"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные владельца кошки успешно изменены (обновлены)",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = CatOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Владелец кошки не найден",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = CatOwner.class))
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    public ResponseEntity<CatOwner> updateCatOwner (@RequestBody CatOwner catOwner) {
        CatOwner updateCatOwner = catOwnerService.updateCatOwner(catOwner);
        if (updateCatOwner != null) {
            return ResponseEntity.ok(updateCatOwner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{catId}")
    @Operation(
            summary = "Удаление владельца кошки по ее уникальному идентификатору",
            description = "Поиск владельца кошки для удаления по ее уникальному идентификатору"
    )
    @Parameters(value = {
            @Parameter(name = "Уникальный идентификатор владельца кошки", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Владелец кошки успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Владелец кошки не найден"
            )
    })
    public ResponseEntity<Void> deleteCatOwnerById(@PathVariable("catOwnerId") Long catOwnerId) {
        catOwnerService.deleteCatOwnerById(catOwnerId);
        return ResponseEntity.ok().build();
    }

}
