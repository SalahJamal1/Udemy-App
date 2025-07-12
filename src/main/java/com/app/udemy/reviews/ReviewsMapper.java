package com.app.udemy.reviews;

import com.app.udemy.instructor.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewsMapper {
    void updateReviews(Reviews source, @MappingTarget Reviews target);
}
