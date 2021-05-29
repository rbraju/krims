package com.springboot.krims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "alerts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long alert_id;

    private String dedupekey;

    private String description;

    private String source;

    private String location;

    private String service;

    private String base;

    private String status;

    private String severity;

    private int total_events;

    private boolean processed;

    private String assignee;

}
