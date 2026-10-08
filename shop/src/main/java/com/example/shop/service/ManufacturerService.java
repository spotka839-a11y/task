package com.example.shop.service;

import com.example.shop.dto.ManufacturerForm;
import com.example.shop.entity.Manufacturer;
import com.example.shop.repository.ManufacturerRepository;
import com.example.shop.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    public Manufacturer findById(Integer id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Производитель не найден: " + id));
    }

    @Transactional
    public Manufacturer createFromForm(ManufacturerForm form) {
        Manufacturer m = new Manufacturer();
        m.setTitle(ValidationUtils.normalize(form.getTitle()));
        return manufacturerRepository.save(m);
    }
}