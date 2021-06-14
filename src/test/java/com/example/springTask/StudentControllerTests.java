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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTests extends TestUtils{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void validateGetRequest() throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("olegr hcanged")))
                .andExpect(jsonPath("$[0].phoneNumber", is("11111")))
                .andExpect(jsonPath("$[0].email", is("toleg2@mail.md")))
                .andExpect(jsonPath("$[0].average", is(5.0)))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].name", is("testing name cha")))
                .andExpect(jsonPath("$[1].phoneNumber", is("2222")))
                .andExpect(jsonPath("$[1].email", is("emailstud@mail.md")))
                .andExpect(jsonPath("$[1].average", is(99.0)))
                .andReturn();
    }

    @Test
    public void validateSingleGetRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("olegr hcanged")))
                .andExpect(jsonPath("$.phoneNumber", is("11111")))
                .andExpect(jsonPath("$.email", is("toleg2@mail.md")))
                .andExpect(jsonPath("$.average", is(5.0)))
                .andReturn();
    }

    @Test
    public void getNotFoundResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/students/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void validateCreateStudent() throws Exception {
        Student student = new Student(4, "testing name", "2222", "emailstud@mail.md", 99f);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(student)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void validatePutStudent() throws Exception {
        Student student = new Student(4, "stud4 name", "33333", "emailstud@mail.md", 99f);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/students/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(super.mapToJson(studentMapper.toStudentDTO(student))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("stud4 name")))
                .andExpect(jsonPath("$.phoneNumber", is("33333")))
                .andExpect(jsonPath("$.email", is("emailstud@mail.md")))
                .andExpect(jsonPath("$.average", is(99.0)))
                .andReturn();
    }

    @Test
    public void validateDeleteStudent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/students/4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.name", is("stud4 name")))
                .andExpect(jsonPath("$.phoneNumber", is("33333")))
                .andExpect(jsonPath("$.email", is("emailstud@mail.md")))
                .andExpect(jsonPath("$.average", is(99.0)))
                .andReturn();
    }

    @Test
    public void validateUnexistingDeleteStudent() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/students/99").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
