package com.example.springTask.controllerTests;

import com.example.springTask.utility.TestUtils;
import com.example.springTask.mappers.TeacherMapper;
import com.example.springTask.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTests extends TestUtils {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void validateGetRequest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("teacher 1 changed")))
                .andExpect(jsonPath("$[0].email", is("test.emailt@mail.md")))
                .andExpect(jsonPath("$[0].salary", is(10000.0)))
                .andExpect(jsonPath("$[0].degree", is(2)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("teacher 2 changed")))
                .andExpect(jsonPath("$[1].email", is("test.2@mail.md")))
                .andExpect(jsonPath("$[1].salary", is(2000.0)))
                .andExpect(jsonPath("$[1].degree", is(11)))
                .andReturn();

    }

    @Test
    public void validateSingleGetRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("teacher 1 changed")))
                .andExpect(jsonPath("$.email", is("test.emailt@mail.md")))
                .andExpect(jsonPath("$.salary", is(10000.0)))
                .andExpect(jsonPath("$.degree", is(2)))
                .andReturn();
    }

    @Test
    public void getNotFoundResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/teachers/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void validateCreateTeacher() throws Exception {
        Teacher teacher = new Teacher(4, "test name", "test123@mail.md", 150000f, 4);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(teacher)))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void validatePutTeacher() throws Exception {
        Teacher teacher = new Teacher(4, "testchangename", "test123@mail.md", 150000f, 4);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/teachers/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(teacherMapper.toTeacherDTO(teacher))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("testchangename")))
                .andExpect(jsonPath("$.email", is("test123@mail.md")))
                .andExpect(jsonPath("$.salary", is(150000.0)))
                .andExpect(jsonPath("$.degree", is(4)))
                .andReturn();
    }

    @Test
    public void validateDeleteTeacher() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/7"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.name", is("test name")))
                .andExpect(jsonPath("$.email", is("test123@mail.md")))
                .andExpect(jsonPath("$.salary", is("150000.0")))
                .andExpect(jsonPath("$.degree", is(4)))
                .andReturn();
    }

    @Test
    public void validateUnexistingDeleteTeacher() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
