package com.springboot.krims.module.event;

import com.springboot.krims.dto.Alert;
import com.springboot.krims.dto.Incident;
import com.springboot.krims.dto.DefaultResponse;
import com.springboot.krims.repositories.AlertRepository;
import com.springboot.krims.repositories.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/krims", produces = "application/json")
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private AlertRepository alertRepository;

    @GetMapping("/incidents")
    public @ResponseBody
    Iterable<Incident> getIncidents() {
        // Get all unprocessed alerts
        List<Alert> unprocessedAlerts = alertRepository.findAlertsByProcessed(false);
        for (Alert a : unprocessedAlerts) {
            System.out.println(a);
        }

        // Group them into incidents
        return incidentRepository.findIncidentsByStatus("Open");
    }

}
