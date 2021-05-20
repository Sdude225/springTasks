package com.example.springTask.cotrollers;

import com.example.springTask.models.Student;
import com.example.springTask.services.StudentService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping("/students")
    public List<Student> list() {
        return service.getAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> get(@PathVariable Integer id) {
        try {
            Student student = service.get(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/students")
    public void add(@RequestBody Student student) {
        if (EmailValidator.getInstance(true).isValid(student.getEmail()))
            service.save(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable Integer id) {
        try {
            Student existingStudent = service.get(id);
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAverage(student.getAverage());
            existingStudent.setPhoneNumber(student.getPhoneNumber());
            service.save(existingStudent);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
