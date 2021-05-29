package com.springboot.krims.module.event;

import com.springboot.krims.dto.Alert;
import com.springboot.krims.dto.DefaultResponse;
import com.springboot.krims.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/krims", produces = "application/json")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping("/alerts")
    public @ResponseBody
    Iterable<Alert> getEvents() {
        return alertRepository.findAll();
    }

    @PostMapping(path = "/alerts", consumes = "application/json")
    public @ResponseBody
    DefaultResponse createAlert(@RequestBody Alert alert) {
        String key = String.format("%s:%s:%s", alert.getSource(), alert.getBase(), alert.getService()).toLowerCase();
        alert.setDedupekey(key);
        alertRepository.save(alert);
        return new DefaultResponse("success", "Event added");
    }
}
