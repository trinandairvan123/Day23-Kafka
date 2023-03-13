package com.mafr.kafka.catalog.controller;

import com.mafr.kafka.catalog.entity.CatalogEntity;
import com.mafr.kafka.catalog.services.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {
    private final CatalogService service;

    @GetMapping
    public List<CatalogEntity> getAll() {
        List<CatalogEntity> response = service.getAll();
        log.info("Success get data");
        return response;
    }

    @GetMapping("/{id}")
    public CatalogEntity getByID(@PathVariable Long id) {
        CatalogEntity response = service.getByID(id);
        log.info("Success get data with ID {}", id);
        return response;
    }

    @PostMapping("/order")
    public String order(@RequestBody CatalogEntity param) {
        try {
            String response = service.order(param);
            log.info("Success {}", param.getId());
            return response;
        } catch (Exception e) {
            log.error("Failed {}", e.getMessage());
            return "Failed : " + e.getMessage() + ", " + e.getCause();
        }
    }

}