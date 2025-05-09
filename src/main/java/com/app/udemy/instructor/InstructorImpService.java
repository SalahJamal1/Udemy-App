package com.app.udemy.instructor;


import com.app.udemy.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorImpService implements InstructorService {
    private final InstructorRepository repository;


    @Override
    public Page<Instructor> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Instructor> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Instructor save(Instructor entity) {
        if (entity.getCourses() != null) {
            for (Course course : entity.getCourses()) {
                course.setInstructor(entity);
            }
        }
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Instructor entity) {
        repository.delete(entity);
    }


    @Override
    public Page<Instructor> findInstructorsByNameOrCourseTitle(String name, String title, Pageable pageable) {
        return repository.findInstructorsByNameOrCourseTitle(name, title, pageable);
    }
}
