package com.app.udemy.instructor;


import com.app.udemy.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository repository;


    
    public List<Instructor> findAll() {
        return repository.findAll();
    }

    
    public Optional<Instructor> findById(Integer id) {

        return repository.findById(id);
    }

    
    @Transactional
    public Instructor save(Instructor entity) {
        if (entity.getCourses() != null) {
            for (Course course : entity.getCourses()) {
                course.setInstructor(entity);
            }
        }
        return repository.save(entity);
    }

    
    @Transactional
    public void delete(Instructor entity) {
        repository.delete(entity);
    }


}
