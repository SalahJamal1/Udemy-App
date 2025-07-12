package com.app.udemy.course;

import com.app.udemy.course.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService service;
private final CourseMapper mapper;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getCourses() {
        List<Course> courses = service.findAll();
        List<CourseDto> courseRecourse = courses.stream()
                .map(a -> CourseDto.builder()
                        .description(a.getDescription())
                        .price(a.getPrice()).imgUrl(a.getImgUrl())
                        .id(a.getId())
                        .instructorName(a.getInstructor().getName())
                        .title(a.getTitle())
                        .StudentsNumber(a.getStudents().size())
                        .QuantityRating(a.getReviews().size())
                        .AvgRating(a.AvgRating((a)))
                        .reviews(a.ReviewsDto(a))
                        .build()).toList();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(courseRecourse);
        
    }


    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id).orElseThrow(() ->
                        new RuntimeException("We didn't found the doc by Id")));
    }

    @PatchMapping
    public ResponseEntity<Course> updateCourse(@RequestBody Course entity) {
        Course exstingCourse = service.findById(entity.getId()).orElseThrow();
            mapper.updateCourse(entity, exstingCourse);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(exstingCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        Course entity = service.findById(id).orElseThrow(() -> new RuntimeException("We didn't found the doc by Id"));
        service.delete(entity);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("The doc deleted successfully");
    }
}
