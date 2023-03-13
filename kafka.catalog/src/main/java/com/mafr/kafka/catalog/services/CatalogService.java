package com.mafr.kafka.catalog.services;

import com.mafr.kafka.catalog.entity.CatalogEntity;

import java.util.List;

public interface CatalogService {
    List<CatalogEntity> getAll();

    CatalogEntity getByID(Long id);

    String order(CatalogEntity param) throws Exception;
}
