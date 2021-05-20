package com.example.springTask.cotrollers;

import com.example.springTask.models.Teacher;
import com.example.springTask.services.TeacherService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService service;

    @GetMapping("/teachers")
    public List<Teacher> list() {
        return service.getAll();
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> get(@PathVariable Integer id) {
        try {
            Teacher teacher = service.get(id);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/teachers")
    public void add(@RequestBody Teacher teacher) {
        if (EmailValidator.getInstance(true).isValid(teacher.getEmail()))
            service.save(teacher);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<?> update(@RequestBody Teacher teacher, @PathVariable Integer id) {
        try {
            Teacher existingTeacher = service.get(id);
            existingTeacher.setName(teacher.getName());
            existingTeacher.setEmail(teacher.getEmail());
            existingTeacher.setSalary(teacher.getSalary());
            existingTeacher.setDegree(teacher.getDegree());
            service.save(existingTeacher);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/teachers/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
