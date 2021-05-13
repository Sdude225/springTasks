package com.example.springTask.services;

import com.example.springTask.models.Teacher;
import com.example.springTask.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    public void save(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public Teacher get(Integer id) {
        return teacherRepository.findById(id).get();
    }

    public void delete(Integer id) {
        teacherRepository.deleteById(id);
    }
}
