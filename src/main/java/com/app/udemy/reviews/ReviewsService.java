package com.app.udemy.reviews;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsRepository repository;


    
    public List<Reviews> findAll() {
        return repository.findAll();
    }

    
    public Optional<Reviews> findById(Integer id) {
        return repository.findById(id);
    }

    
    @Transactional
    public Reviews save(Reviews entity) {

        return repository.save(entity);
    }

    
    @Transactional
    public void delete(Reviews entity) {
        repository.delete(entity);
    }


}
