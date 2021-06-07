package com.example.springTask;

import com.example.springTask.mappers.TeacherMapper;
import com.example.springTask.models.Teacher;
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
public class TeacherControllerTests extends TestUtils{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void validateGetRequest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());

        Teacher[] teachers = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Teacher[].class);
        Assertions.assertTrue(teachers.length > 0);
    }

    @Test
    public void validateSingleGetRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());

        Teacher teacher = super.mapFromJson(mvcResult.getResponse().getContentAsString(), Teacher.class);
        Assertions.assertEquals(10000, teacher.getSalary());
    }

    @Test
    public void getNotFoundResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers/99")
        .accept(MediaType.APPLICATION_JSON)).andReturn();
        Assertions.assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void validateCreateTeacher() throws Exception {
        Teacher teacher = new Teacher(4, "test name", "test123@mail.md", 150000f, 4);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(teacher))).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void validatePutTeacher() throws Exception {
        Teacher teacher = new Teacher(4, "testchangename", "test123@mail.md", 150000f, 4);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/teachers/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(teacherMapper.toTeacherDTO(teacher)))).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void validateDeleteTeacher() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/6")).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
