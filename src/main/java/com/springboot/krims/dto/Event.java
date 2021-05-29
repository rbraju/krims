package com.springboot.krims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long event_id;

    private String dedupekey;

    private String description;

    private String source;

    private String location;

    private String service;

    private String base;

    private String severity;
}
