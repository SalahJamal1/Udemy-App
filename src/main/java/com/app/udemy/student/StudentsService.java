package com.app.udemy.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentsService {
    Page<Students> findAll(Pageable pageable);

    Optional<Students> findById(Integer id);

    Students save(Students entity);

    void delete(Students entity);


}
