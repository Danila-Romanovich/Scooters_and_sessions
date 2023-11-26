package com.example.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


import com.example.webapp.model.ScooterR;

@RestController
@RequestMapping("api/scooters")
public class ScooterController {

    private List<ScooterR> scooters;
    private static AtomicLong nextId = new AtomicLong(1);

    public ScooterController() {
        ScooterR newScooter1 = new ScooterR(nextId.getAndIncrement(), "Accesstyle", "Typhoon 30s", 30, true);
        ScooterR newScooter2 = new ScooterR(nextId.getAndIncrement(), "Tribe", "chin", 25, true);

        scooters = new ArrayList<>();
        scooters.add(newScooter1);
        scooters.add(newScooter2);
    }

    @GetMapping
    public List<ScooterR> getScooter() {
        return scooters;
    }

    @GetMapping("/{scooter_id}")
    public ScooterR getScooter(@PathVariable("scooter_id") Long scooterId) {
        return scooters.stream()
                .filter(scooter -> scooter.id().equals(scooterId))
                .findAny()
                .orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addScooter(@RequestParam("brand") String brand,
                           @RequestParam("model") String model,
                           @RequestParam("maxSpeed") int maxSpeed) {

        ScooterR newScooter = new ScooterR(nextId.getAndIncrement(), brand, model, maxSpeed, true);
        scooters.add(newScooter);

    }

    @PutMapping("/{scooter_id}")
    public String updateScooter(
            @PathVariable("scooter_id") Long scooterId,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("maxSpeed") int maxSpeed,
            @RequestParam("active") boolean active) {

        Optional<ScooterR> optionalScooter = scooters.stream()
                .filter(scooter -> scooter.id().equals(scooterId))
                .findFirst();

        if (optionalScooter.isPresent()) {
            ScooterR existingScooter = optionalScooter.get();

            ScooterR updatedScooter = new ScooterR(
                    existingScooter.id(),
                    brand,
                    model,
                    maxSpeed,
                    active
            );

            scooters.remove(existingScooter);
            scooters.add(updatedScooter);

            return "Запись с id=" + scooterId + "успешно изменена.";
        } else {
            return "Такой записи нет.";
        }
    }

    @DeleteMapping("/{scooter_id}")
    public String deleteScooter(@PathVariable("scooter_id") Long scooterId) {
        boolean removed = scooters.removeIf(scooter -> scooter.id().equals(scooterId));

        if (removed) {
            return "Запись удалена.";
        } else {
            return "Возникли проблемы при удалении.";
        }
    }



}