package com.springboot.krims.module.event;

import com.springboot.krims.dto.Alert;
import com.springboot.krims.dto.DefaultResponse;
import com.springboot.krims.dto.Event;
import com.springboot.krims.repositories.AlertRepository;
import com.springboot.krims.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(value = "/krims", produces = "application/json")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AlertRepository alertRepository;

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
        createAlert(newEvent);

        return new DefaultResponse("success", "Event added");
    }

    private void createAlert(Event event) {
        // Check for open alerts with dedupe key
        Alert existingAlert = alertRepository.findAlertsByDedupekeyAndStatus(dedupeKey, "Open");

        if (existingAlert == null) {
            Alert alert = Alert.builder().dedupekey(dedupeKey).description(event.getDescription()).source(event.getSource())
                    .location(event.getLocation()).service(event.getService()).base(event.getBase())
                    .status("Open").severity(event.getSeverity()).total_events(1).assignee("").build();
            alertRepository.save(alert);
        } else {
            // Update existing alert
            existingAlert.setDescription(event.getDescription());
            existingAlert.setLocation(event.getLocation());
            existingAlert.setSeverity(event.getSeverity());
            existingAlert.setTotal_events(existingAlert.getTotal_events() + 1);
            existingAlert.setProcessed(true);
            alertRepository.save(existingAlert);
            System.out.println("Updating existing alert");
        }
    }
}
