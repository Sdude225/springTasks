package com.example.springTask;

import com.example.springTask.dto.StudentDTO;
import com.example.springTask.dto.TeacherDTO;
import com.example.springTask.mappers.StudentMapper;
import com.example.springTask.mappers.TeacherMapper;
import com.example.springTask.models.Student;
import com.example.springTask.models.Teacher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MapperTests {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void shouldMapTeacherEntityToDTO() {
        Teacher teacher = new Teacher(10, "name", "email", 1000f, 2);

        TeacherDTO teacherDTO = teacherMapper.toTeacherDTO(teacher);

        Assertions.assertEquals(teacherDTO.getName(), "name");
        Assertions.assertEquals(teacherDTO.getEmail(), "email");
        Assertions.assertEquals(teacherDTO.getSalary(), 1000f);
        Assertions.assertEquals(teacherDTO.getDegree(), 2);
    }

    @Test
    public void shouldMapStudentEntityToDTO() {
        Student student = new Student(10, "name", "06978781", "email", 5.5f);

        StudentDTO studentDTO = studentMapper.toStudentDTO(student);

        Assertions.assertEquals(studentDTO.getName(), "name");
        Assertions.assertEquals(studentDTO.getEmail(), "email");
        Assertions.assertEquals(studentDTO.getPhoneNumber(), "06978781");
        Assertions.assertEquals(studentDTO.getAverage(), 5.5f);
    }
}
