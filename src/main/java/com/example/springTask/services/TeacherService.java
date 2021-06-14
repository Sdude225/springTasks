package com.example.springTask.services;

import com.example.springTask.models.Teacher;
import com.example.springTask.repository.TeacherRepository;
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
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> getAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Teacher> teacherPage = teacherRepository.findAll(paging);

        if (teacherPage.hasContent())
            return teacherPage.getContent();
        else
            return new ArrayList<Teacher>();
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
