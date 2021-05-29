package com.springboot.krims.repositories;

import com.springboot.krims.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
