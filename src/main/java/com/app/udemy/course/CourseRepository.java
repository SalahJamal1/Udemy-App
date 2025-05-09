package com.app.udemy.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c FROM Course c WHERE c.title LIKE CONCAT('%',:title,'%')")
    Page<Course> findByTitleContaining(@Param("title") String title, Pageable pageable);
}
