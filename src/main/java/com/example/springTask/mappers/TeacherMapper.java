package com.example.springTask.mappers;


import com.example.springTask.cotrollers.TeacherController;
import com.example.springTask.dto.TeacherDTO;
import com.example.springTask.models.Teacher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDTO toTeacherDTO(Teacher teacher);

    List<TeacherDTO> toTeacherDTOs(List<Teacher> teachers);

    Teacher toTeacher(TeacherDTO teacherDTO);
}
