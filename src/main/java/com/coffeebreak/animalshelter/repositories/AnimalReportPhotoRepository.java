package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.AnimalReportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalReportPhotoRepository extends JpaRepository<AnimalReportPhoto, Long> {
    Optional <AnimalReportPhoto> findAnimalReportPhotoByAnimalReportDataId(Long id);
}