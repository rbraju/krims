package com.springboot.krims.repositories;

import com.springboot.krims.dto.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Integer> {

    List<Event> findByDedupekey(String dedupekey);
}
