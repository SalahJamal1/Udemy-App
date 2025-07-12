package com.app.udemy.instructor;

import com.app.udemy.course.Course;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InstructorMapper {
    void updateInstructor(Instructor source, @MappingTarget Instructor target);
}
