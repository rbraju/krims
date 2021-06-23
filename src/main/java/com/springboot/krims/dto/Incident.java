package com.springboot.krims.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "incidents")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long incident_id;

    private String description;

    private String status;

    private String severity;

    private int total_alerts;

    @Column(columnDefinition = "TEXT")
    private String sources;

    private String assignee;

}
