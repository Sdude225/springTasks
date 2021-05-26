package com.example.springTask.mappers;

import com.example.springTask.dto.StudentDTO;
import com.example.springTask.models.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDTO toStudentDTO(Student student);

    Student toStudent(StudentDTO studentDTO);
}
