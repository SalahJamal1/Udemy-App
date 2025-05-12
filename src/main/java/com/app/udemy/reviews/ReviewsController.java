package com.app.udemy.reviews;

import com.app.udemy.course.Course;
import com.app.udemy.course.CourseService;
import com.app.udemy.student.Students;
import com.app.udemy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService service;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Reviews>> getReviews() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviews(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id).orElseThrow(() -> new RuntimeException("We cannot found the doc")));
    }

    @PostMapping("/courses/{courseId}/reviewCourse")
    public ResponseEntity<?> addReviewForCourse
            (@AuthenticationPrincipal User user,
             @PathVariable Integer courseId,
             @RequestBody Reviews entity) {
        Course course = courseService.findById(courseId).orElseThrow();
        List<Course> existingCourses = user.getStudents().getCourses().stream()
                .filter(a -> a.getId().equals(courseId)).toList();
        if (existingCourses.isEmpty()) {
            throw new RuntimeException("you dont buy this course ");
        }


        Students students = user.getStudents();

        entity.setStudents(students);
        entity.setCourse(course);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.save(entity));
    }


    @PatchMapping
    public ResponseEntity<Reviews> updateReviews(@RequestBody Reviews entity) {
        Reviews Reviews = service.findById(entity.getId()).orElseThrow();
        Reviews.setComment(entity.getComment());
        Reviews.setRating(entity.getRating());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(Reviews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReviews(@PathVariable Integer id) {
        Reviews entity = service.findById(id).orElseThrow(() -> new RuntimeException("We cannot found the doc"));
        service.delete(entity);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("The doc deleted successfully");
    }
}
