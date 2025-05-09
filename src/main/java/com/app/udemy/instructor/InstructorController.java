package com.app.udemy.instructor;

import com.app.udemy.course.Course;
import com.app.udemy.course.CourseService;
import com.app.udemy.user.Roles;
import com.app.udemy.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService service;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getInstructors
            (@RequestParam(value = "title", required = false) String title,
             @RequestParam(value = "name", required = false) String name,
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        System.out.println(title);
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> map = new LinkedHashMap<>();
        Page<Instructor> instructors = name != null && title != null ?
                service.findInstructorsByNameOrCourseTitle(name, title, pageable) :
                name != null ? service.findInstructorsByNameOrCourseTitle(name, null, pageable) :
                        title != null ? service.findInstructorsByNameOrCourseTitle(null, title, pageable) :
                                service.findAll(pageable);
        map.put("status", "success");
        map.put("result", instructors.getContent().size());
        map.put("data", instructors.getContent());
        map.put("current Page", page);
        map.put("total page", instructors.getTotalPages());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(map);

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
