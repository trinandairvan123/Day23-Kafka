package com.mafr.kafka.catalog.services.implementation;

import com.mafr.kafka.catalog.entity.CatalogEntity;
import com.mafr.kafka.catalog.repository.CatalogRepository;
import com.mafr.kafka.catalog.services.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogImpl implements CatalogService {
    private final CatalogRepository repository;

    @Override
    public List<CatalogEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public CatalogEntity getByID(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public String order(CatalogEntity param) throws Exception {
        if (param.getQuantity() <= 0 || param.getQuantity() == null) throw new Exception("Please input quantity!");
        if (param.getId() == null) throw new Exception("Please input id product!");

        CatalogEntity dataCurrent = this.getByID(param.getId());
        if (dataCurrent.getQuantity() <= 0) throw new Exception("Product not available!");

        dataCurrent.setQuantity(dataCurrent.getQuantity() - param.getQuantity());
        if (dataCurrent.getQuantity() < 0) throw new Exception("Product not available! for " + param.getQuantity() + " quantities");

        repository.save(dataCurrent);
        return "Success";
    }

}