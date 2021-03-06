package com.example.springTask.cotrollers;

import com.example.springTask.dto.StudentDTO;
import com.example.springTask.mappers.StudentMapper;
import com.example.springTask.models.Student;
import com.example.springTask.models.Teacher;
import com.example.springTask.services.StudentService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/students")
    public ResponseEntity<Page<Student>> list(Pageable pageable) {
        Page<Student> page = service.getAll(pageable);

        return new ResponseEntity<Page<Student>>(page, HttpStatus.OK);
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
    public ResponseEntity<?> update(@RequestBody StudentDTO student, @PathVariable Integer id) {
        try {
            Student updatedStudent = studentMapper.toStudent(student);
            updatedStudent.setId(id);
            service.save(updatedStudent);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Student student = service.get(id);
            service.delete(id);
            return new ResponseEntity(student, HttpStatus.OK);
        }

        catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
