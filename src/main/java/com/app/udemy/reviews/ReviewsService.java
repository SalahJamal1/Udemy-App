package com.app.udemy.reviews;

import java.util.List;
import java.util.Optional;

public interface ReviewsService {
    List<Reviews> findAll();

    Optional<Reviews> findById(Integer id);

    Reviews save(Reviews entity);

    void delete(Reviews entity);


}
