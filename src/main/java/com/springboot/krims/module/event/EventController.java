package com.springboot.krims.module.event;

import com.springboot.krims.dto.*;
import com.springboot.krims.repositories.AlertRepository;
import com.springboot.krims.repositories.EventRepository;
import com.springboot.krims.repositories.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/krims", produces = "application/json")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @GetMapping("/events")
    public @ResponseBody
    Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }

    private String dedupeKey = null;

    /**
     * Create an event, alert (if necessary), update event count in alerts
     *
     * @param event
     * @return
     */
    @PostMapping(path = "/events", consumes = "application/json")
    public @ResponseBody
    DefaultResponse createEvent(@RequestBody Event event) {
        // Generate dedupe key
        dedupeKey = String.format("%s:%s:%s", event.getSource(), event.getBase(), event.getService()).toLowerCase();
        event.setDedupekey(dedupeKey);

        Event newEvent = eventRepository.save(event);
        Alert alert = createAlert(newEvent);
        createIncident(alert);
        return new DefaultResponse("success", "Event added");
    }

    private Alert createAlert(Event event) {
        Alert x = null;

        // Check for open alerts with dedupe key
        Alert existingAlert = alertRepository.findAlertsByDedupekeyAndStatus(dedupeKey, "Open");

        if (existingAlert == null) {
            Alert alert = Alert.builder().dedupekey(dedupeKey).description(event.getDescription()).source(event.getSource())
                    .location(event.getLocation()).service(event.getService()).base(event.getBase())
                    .status("Open").severity(event.getSeverity()).total_events(1).assignee("Unassigned").build();
            alertRepository.save(alert);
            x = alert;
        } else {
            // Update existing alert
            existingAlert.setDescription(event.getDescription());
            existingAlert.setLocation(event.getLocation());
            existingAlert.setSeverity(event.getSeverity());
            existingAlert.setTotal_events(existingAlert.getTotal_events() + 1);
            existingAlert.setProcessed(true);
            alertRepository.save(existingAlert);
            System.out.println("Updating existing alert");
            x = existingAlert;
        }

        return x;
    }

    private Incident createIncident(Alert alert) {
        Incident x = null;
        boolean incidentUpdated = false;

        // Get all open incidents
        List<Incident> incidents = incidentRepository.findIncidentsByStatus("Open");
        for (Incident incident : incidents) {
            int total_sources = incident.getSources().split(",").length;
            Set<String> sources = new HashSet<>(Arrays.asList(incident.getSources().split(",")));
            if (sources.contains(alert.getSource())) {
                // Update current incident and break
                incident.setDescription(total_sources + " sources affected");
                incident.setTotal_alerts(incident.getTotal_alerts() + 1);
                incidentRepository.save(incident);
                incidentUpdated = true;
                break;
            } else {
                for (String source : sources) {
                    int similarity = Shingles.getSimilarityPercent(source, alert.getSource());
                    if (similarity > 50) {
                        // Update current incident and break
                        ++total_sources;
                        incident.setDescription(total_sources + " sources affected");
                        incident.setSources(incident.getSources() + ", " + alert.getSource());
                        incident.setTotal_alerts(incident.getTotal_alerts() + 1);
                        incidentRepository.save(incident);
                        incidentUpdated = true;
                        break;
                    }
                }
            }
        }

        // Create new incident
        if (!incidentUpdated) {
            Incident incident = Incident.builder()
                    .description(alert.getSource() + " affected")
                    .assignee("Unassigned")
                    .severity(alert.getSeverity())
                    .status("Open")
                    .sources(alert.getSource())
                    .total_alerts(1)
                    .build();
            incidentRepository.save(incident);
        }
        return x;
    }
}
