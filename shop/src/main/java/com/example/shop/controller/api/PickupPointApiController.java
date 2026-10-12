package com.example.shop.controller.api;

import com.example.shop.dto.PickupPointDto;
import com.example.shop.repository.PickupPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pickup-points")
public class PickupPointApiController {

    @Autowired
    private PickupPointRepository pickupPointRepository;

    @GetMapping
    public List<PickupPointDto> list() {
        return pickupPointRepository.findAll().stream().map(p -> {
            PickupPointDto dto = new PickupPointDto();
            dto.setId(p.getId());
            dto.setAddress(p.getAddress());
            return dto;
        }).collect(Collectors.toList());
    }
}