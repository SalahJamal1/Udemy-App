package com.app.udemy.instructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InstructorService {
    Page<Instructor> findAll(Pageable pageable);

    Optional<Instructor> findById(Integer id);

    Instructor save(Instructor entity);

    void delete(Instructor entity);


    Page<Instructor> findInstructorsByNameOrCourseTitle(String name, String title, Pageable pageable);

}
