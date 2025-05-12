package com.app.udemy.instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorService {
    List<Instructor> findAll();

    Optional<Instructor> findById(Integer id);

    Instructor save(Instructor entity);

    void delete(Instructor entity);


}
