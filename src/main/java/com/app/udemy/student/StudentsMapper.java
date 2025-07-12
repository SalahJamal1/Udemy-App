package com.app.udemy.student;

import com.app.udemy.reviews.Reviews;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentsMapper {
    void updateStudents(Students source, @MappingTarget Students target);
}
