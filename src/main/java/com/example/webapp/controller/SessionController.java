package com.example.webapp.controller;

import com.example.webapp.model.Session;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("api/scooters/{scooter_id}/sessions")
public class SessionController {

    private final List<Session> sessions;
    private static AtomicLong nextId = new AtomicLong(1);

    public SessionController() {
        try {
            DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            String strDateStart1 = "11.10.2023 10:12:39";
            String strDateEnd1 = "11.10.2023 10:23:12";
            Date dateStart1 = formatter.parse(strDateStart1);
            Date dateEnd1 = formatter.parse(strDateEnd1);

            String strDateStart2 = "11.10.2023 12:10:14";
            String strDateEnd2 = "11.10.2023 10:12:39";
            Date dateStart2 = formatter.parse(strDateStart2);
            Date dateEnd2 = formatter.parse(strDateEnd2);

            String strDateStart3 = "12.10.2023 10:12:39";
            String strDateEnd3 = "12.10.2023 10:23:12";
            Date dateStart3 = formatter.parse(strDateStart3);
            Date dateEnd3 = formatter.parse(strDateEnd3);

            Session s1 = new Session(nextId.getAndIncrement(), 2L, dateStart1, dateEnd1);
            Session s2 = new Session(nextId.getAndIncrement(), 1L, dateStart2, dateEnd2);
            Session s3 = new Session(nextId.getAndIncrement(), 1L, dateStart3, dateEnd3);

            sessions = new ArrayList<>();
            sessions.add(s1);
            sessions.add(s2);
            sessions.add(s3);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping()
    public List<Session> getSession(@PathVariable("scooter_id") Long scooterId) {
        return sessions.stream()
                .filter(session -> session.scooterId().equals(scooterId))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@PathVariable("scooter_id") Long scooterId,
                           @RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") Date start,
                           @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") Date end) {
        Session newSession = new Session(nextId.getAndIncrement(), scooterId, start, end);
        sessions.add(newSession);
    }

    @PutMapping("/{session_id}")
    public String updateSession(@PathVariable("session_id") Long sessionId,
                                                @RequestParam("scooter_id") Long scooterId,
                                                @RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") Date start,
                                                @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") Date end) {

        Optional<Session> optionalSession = sessions.stream()
                .filter(session -> session.id().equals(sessionId))
                .findFirst();

        if (optionalSession.isPresent()) {
            Session existingSession = optionalSession.get();

            Session updatedSession = new Session(
                    existingSession.id(),
                    scooterId,
                    start,
                    end
            );

            sessions.remove(existingSession);
            sessions.add(updatedSession);
            return "Запись с id=" + sessionId + "успешно изменена.";
        } else {
            return "Такой записи нет.";
        }
    }

    @DeleteMapping("/{session_id}")
    public String deleteSession(@PathVariable("session_id") Long sessionId) {
        boolean removed = sessions.removeIf(session -> session.id().equals(sessionId));
        if (removed) {
            return "Запись удалена";
        } else {
            return "Возникли проблемы при удалении";
        }

    }


}

// Добавлнеие, редактирование удаление.