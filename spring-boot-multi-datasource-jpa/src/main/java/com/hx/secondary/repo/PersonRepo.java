package com.hx.secondary.repo;

import com.hx.secondary.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person,String> {
}
