package com.monguichet.catalog.Repository;


import com.monguichet.catalog.Entity.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findBySubCategoryId(Long id);
    List<Event> findByNameContainingIgnoreCase(String keyword);

    List<Event> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

}
