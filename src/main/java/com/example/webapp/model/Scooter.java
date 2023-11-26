package com.example.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Scooter {
    @Id
    @GeneratedValue
    private Long id;


}
