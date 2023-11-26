package com.example.webapp.service;

import com.example.webapp.model.ScooterR;

import java.util.List;

public interface ScooterService {
    List<ScooterR> readAll();

    ScooterR read(Long id);

    void create(ScooterR scooter);
    void update(Long id, ScooterR scooter);

    String delete(Long id);
}
