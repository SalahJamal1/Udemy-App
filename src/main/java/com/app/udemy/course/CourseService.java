package com.app.udemy.course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();

    Optional<Course> findById(Integer id);

    Course save(Course entity);

    void delete(Course entity);


}
