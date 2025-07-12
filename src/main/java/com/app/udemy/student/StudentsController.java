package com.app.udemy.student;

import com.app.udemy.course.Course;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentsController {
    private final StudentsService service;
    private final StudentsMapper mapper;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getStudents
            (@RequestParam(value = "title", required = false) String title,
             @RequestParam(value = "name", required = false) String name,
             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
             @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        System.out.println(title);
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> map = new LinkedHashMap<>();
        Page<Students> Students = service.findAll(pageable);
        map.put("status", "success");
        map.put("result", Students.getContent().size());
        map.put("data", Students.getContent());
        map.put("current Page", page);
        map.put("total page", Students.getTotalPages());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(map);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Students> getStudents(@PathVariable Integer id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id).orElseThrow(() -> new RuntimeException("We cannot found the doc")));
    }


    @PostMapping("/addStudentCourse")
    @Transactional
    public ResponseEntity<?>
    addCourse(@AuthenticationPrincipal User user,
              @RequestBody
              List<Course> entity) {
        System.out.println(entity);
        if (user.getRoles() == Roles.ROLE_STUDENT) {
            Students students = user.getStudents();
            List<Course> existingCourses = students.getCourses();
            Set<Integer> courseIds = existingCourses.stream()
                    .map(Course::getId).collect(Collectors.toSet());

            List<Course> courses = entity.stream().filter(course ->
                    !courseIds.contains(course.getId())).toList();

            if (courses.isEmpty()) {
                throw new RuntimeException("All courses are already added.");
            }
            existingCourses.addAll(courses);
            service.save(students);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(courses);
        } else {
            throw new RuntimeException("You don't have permission to add courses");
        }
    }

    @PatchMapping
    public ResponseEntity<Students> updateStudents(@RequestBody Students entity) {
        Students students = service.findById(entity.getId()).orElseThrow();
mapper.updateStudents(entity, students);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(students));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudents(@PathVariable Integer id) {
        Students entity = service.findById(id).orElseThrow(() -> new RuntimeException("We cannot found the doc"));
        service.delete(entity);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("The doc deleted successfully");
    }
}
