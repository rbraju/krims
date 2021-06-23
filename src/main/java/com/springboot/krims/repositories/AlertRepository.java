package com.springboot.krims.repositories;

import com.springboot.krims.dto.Alert;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableJpaRepositories
public interface AlertRepository extends CrudRepository<Alert, Integer> {

    Alert findAlertsByDedupekeyAndStatus(String dedupekey, String status);

    List<Alert> findAlertsByProcessed(boolean processed);

}
