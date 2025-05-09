package com.app.udemy.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourseService {
    Page<Course> findAll(Pageable pageable);

    Optional<Course> findById(Integer id);

    Course save(Course entity);

    void delete(Course entity);

    Page<Course> findByTitleContaining(String title, Pageable page);

}
