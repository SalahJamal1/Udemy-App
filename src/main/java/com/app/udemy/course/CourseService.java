package com.app.udemy.course;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;


    public List<Course> findAll() {
        return repository.findAll();
    }


    public Optional<Course> findById(Integer id) {
        return repository.findById(id);
    }


    @Transactional
    public Course save(Course entity) {
        return repository.save(entity);
    }


    @Transactional
    public void delete(Course entity) {
        repository.delete(entity);
    }


}
