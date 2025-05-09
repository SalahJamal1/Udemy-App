package com.app.udemy.instructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    
    @Query("SELECT i FROM Instructor i JOIN i.courses c ON i.id=c.instructor.id WHERE i.name LIKE CONCAT('%',:name,'%') OR c.title LIKE CONCAT('%',:title,'%')")
    Page<Instructor> findInstructorsByNameOrCourseTitle(@Param("name") String name, @Param("title") String title, Pageable pageable);
}
