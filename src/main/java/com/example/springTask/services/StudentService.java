package com.example.springTask.services;

import com.example.springTask.models.Student;
import com.example.springTask.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);

        if (studentPage.hasContent())
            return studentPage.getContent();
        else
            return new ArrayList<Student>();
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public Student get(Integer id) {
        return studentRepository.findById(id).get();
    }

    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}
