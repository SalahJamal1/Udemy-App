package com.app.udemy.instructor;

import com.app.udemy.course.Course;
import com.app.udemy.course.CourseService;
import com.app.udemy.user.Roles;
import com.app.udemy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService service;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Instructor>> getInstructors() {

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getInstructor(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id).orElseThrow(() -> new RuntimeException("We cannot found the doc")));
    }

    @PostMapping
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor entity) {
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.save(entity));
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            throw new RuntimeException(exc.getMessage());
        }

    }

    @PatchMapping
    public ResponseEntity<Instructor> updateInstructor(@RequestBody Instructor entity) {
        Instructor instructor = service.findById(entity.getId()).orElseThrow();
        instructor.setName(entity.getName());
        instructor.setImage(entity.getImage());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(instructor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer id) {
        Instructor entity = service.findById(id).orElseThrow(() -> new RuntimeException("We cannot found the doc"));
        service.delete(entity);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("The doc deleted successfully");
    }

    @PostMapping("/addCourse")
    @Transactional
    public ResponseEntity<Course>
    addCourse(@AuthenticationPrincipal User user,
              @RequestBody
              Course entity) {
        if (user.getRoles() == Roles.ROLE_INSTRUCTOR) {
            Instructor instructor = service.findById(user.getInstructor()
                    .getId()).orElseThrow();
            entity.setInstructor(instructor);
            courseService.save(entity);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(entity);
        } else {
            throw new RuntimeException("You dont have permeation");
        }
    }
}
