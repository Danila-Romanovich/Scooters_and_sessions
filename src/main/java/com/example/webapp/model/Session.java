package com.example.webapp.model;

import java.util.Date;
public record Session(Long id, Long scooterId, Date start, Date end) {
}

