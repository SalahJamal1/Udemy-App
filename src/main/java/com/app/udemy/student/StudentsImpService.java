package com.app.udemy.student;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentsImpService implements StudentsService {
    private final StudentsRepository repository;


    @Override
    public Page<Students> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Students> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Students save(Students entity) {

        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Students entity) {
        repository.delete(entity);
    }


}
