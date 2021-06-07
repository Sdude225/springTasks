package com.example.springTask;

import com.example.springTask.dto.StudentDTO;
import com.example.springTask.dto.TeacherDTO;
import com.example.springTask.mappers.StudentMapperImpl;
import com.example.springTask.mappers.TeacherMapperImpl;
import com.example.springTask.models.Student;
import com.example.springTask.models.Teacher;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MapperTests {
    @Autowired
    private TeacherMapperImpl teacherMapper;

    @Autowired
    private StudentMapperImpl studentMapper;

    @Before
    public void setup() {
        teacherMapper = new TeacherMapperImpl();
    }

    @Test
    public void shouldMapTeacherEntityToDTO() {
        Teacher teacher = new Teacher(10, "name", "email", 1000f, 2);

        TeacherDTO teacherDTO = teacherMapper.toTeacherDTO(teacher);

        Assertions.assertEquals(teacherDTO.getName(), teacher.getName());
        Assertions.assertEquals(teacherDTO.getEmail(), teacher.getEmail());
        Assertions.assertEquals(teacherDTO.getSalary(), teacher.getSalary());
        Assertions.assertEquals(teacherDTO.getDegree(), teacher.getDegree());
    }

    @Test
    public void shouldMapStudentEntityToDTO() {
        Student student = new Student(10, "name", "06978781", "email", 5.5f);

        StudentDTO studentDTO = studentMapper.toStudentDTO(student);

        Assertions.assertEquals(studentDTO.getName(), student.getName());
        Assertions.assertEquals(studentDTO.getEmail(), student.getEmail());
        Assertions.assertEquals(studentDTO.getPhoneNumber(), student.getPhoneNumber());
        Assertions.assertEquals(studentDTO.getAverage(), student.getAverage());
    }
}
