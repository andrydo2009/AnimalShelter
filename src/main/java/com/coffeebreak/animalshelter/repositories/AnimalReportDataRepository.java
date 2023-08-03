package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.AnimalReportData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalReportDataRepository extends JpaRepository<AnimalReportData,Long> {
    AnimalReportData findByChatId(Long id);
    List<AnimalReportData> findAllByChatId(Long id);
}
