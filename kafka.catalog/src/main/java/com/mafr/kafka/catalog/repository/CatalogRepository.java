package com.mafr.kafka.catalog.repository;

import com.mafr.kafka.catalog.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
}
