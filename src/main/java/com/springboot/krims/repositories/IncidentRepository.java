package com.springboot.krims.repositories;

import com.springboot.krims.dto.Incident;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableJpaRepositories
public interface IncidentRepository extends CrudRepository<Incident, Integer> {

    List<Incident> findIncidentsByStatus(String status);

}
