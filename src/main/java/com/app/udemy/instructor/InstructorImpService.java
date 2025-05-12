package com.app.udemy.instructor;


import com.app.udemy.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorImpService implements InstructorService {
    private final InstructorRepository repository;


    @Override
    public List<Instructor> findAll() {
        return repository.findAll();
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


}
