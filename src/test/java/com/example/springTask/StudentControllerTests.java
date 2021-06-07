package com.example.springTask;

import com.example.springTask.mappers.StudentMapper;
import com.example.springTask.models.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTests extends TestUtils{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void validateGetRequest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());

        Student[] students = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Student[].class);
        Assertions.assertTrue(students.length > 0);
    }

    @Test
    public void validateSingleGetRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());

        Student student = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Student.class);
        Assertions.assertEquals("11111", student.getPhoneNumber());
    }

    @Test
    public void getNotFoundResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/99")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void validateCreateTeacher() throws Exception {
        Student student = new Student(3, "testing name", "2222", "emailstud@mail.md", 99f);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(student))).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void validatePutTeacher() throws Exception {
        Student student = new Student(3, "testing name cha", "2222", "emailstud@mail.md", 99f);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/students/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(studentMapper.toStudentDTO(student)))).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void validateDeleteTeacher() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/students/2")).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
