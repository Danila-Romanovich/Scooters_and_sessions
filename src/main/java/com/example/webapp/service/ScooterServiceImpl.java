package com.example.webapp.service;

import com.example.webapp.model.ScooterR;
import com.example.webapp.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScooterServiceImpl implements ScooterService{

    @Autowired
    private ScooterRepository scooterRepository;



    @Override
    public List<ScooterR> readAll() {
        List<ScooterR> allEmployees = scooterRepository.findAll();
        return allEmployees;
    }

    @Override
    public ScooterR read(Long id) {
        Optional<ScooterR> scooter = scooterRepository.findById(id);
        return scooter.orElse(null);
    }

    @Override
    public void create(ScooterR scooter) {
        scooterRepository.save(scooter);
    }

    @Override
    public void update(Long id, ScooterR scooter) {
        Optional<ScooterR> oldScooter = scooterRepository.findById(id);

        ScooterR originalEmployee = oldScooter.get();

        if (Objects.nonNull(scooter.getBrandName()) && !"".equalsIgnoreCase(scooter.getEmployeeName())) {
            originalEmployee.setBrandName(scooter.getEmployeeName());
        }
        if (Objects.nonNull(scooter.getEmployeeSalary()) && scooter.getEmployeeSalary() != 0) {
            originalEmployee.setEmployeeSalary(scooter.getEmployeeSalary());
        }
    }

    @Override
    public String delete(Long id) {
        if (scooterRepository.findById(id).isPresent()) {
            scooterRepository.deleteById(id);
            return "Employee deleted successfully";
        }
        return "No such employee in the database";
    }
}


