package com.example.springTask.cotrollers;

import com.example.springTask.dto.TeacherDTO;
import com.example.springTask.mappers.TeacherMapper;
import com.example.springTask.models.Teacher;
import com.example.springTask.services.TeacherService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService service;

    @Autowired
    private TeacherMapper teacherMapper;



    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> list() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherDTO> get(@PathVariable Integer id) {
        try {
            Teacher teacher = service.get(id);
            return new ResponseEntity(teacherMapper.toTeacherDTO(teacher), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/teachers")
    public void add(@RequestBody TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toTeacher(teacherDTO);
        if (EmailValidator.getInstance(true).isValid(teacher.getEmail()))
            service.save(teacher);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<?> update(@RequestBody TeacherDTO teacher, @PathVariable Integer id) {
        try {
            Teacher updatedTeacher = teacherMapper.toTeacher(teacher);
            updatedTeacher.setId(id);
            service.save(updatedTeacher);
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
