package com.coffeebreak.animalshelter.repositories;

import com.coffeebreak.animalshelter.models.AnimalReportData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Интерфейс, содержащий методы для работы с базой данных объектов класса AnimalReportData
 * @see AnimalReportData
 * @see com.coffeebreak.animalshelter.services.AnimalReportDataService
 */
public interface AnimalReportDataRepository extends JpaRepository<AnimalReportData,Long> {
    AnimalReportData findByChatId(Long id);
    List<AnimalReportData> findAllByChatId(Long id);
}
