package com.luv2code.springboot.controller;

import com.luv2code.springboot.entity.Student;
import com.luv2code.springboot.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTests {

    @Mock
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    private MockMvc mockMvc;

    private Student student;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        student = new Student(1, "John", "Doe", "john.doe@example.com", "Male");
    }

    @Test
    void listStudents() throws Exception {
        when(studentService.findAll()).thenReturn(Arrays.asList(student).toString());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/student")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[Student{id=1, firstName='John', lastName='Doe', email='john.doe@example.com', gender='Male'}]"
                ));
    }

    @Test
    void getStudentById() throws Exception {
        when(studentService.findById(anyInt())).thenReturn(Optional.of(student));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/student/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"));
    }

    @Test
    void addStudent() throws Exception {
        when(studentService.save(any())).thenReturn(student);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"gender\":\"Male\"}");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"));
    }
    @Test
    void testListStudents() {
        // Given
        List<Student> students = new ArrayList<>();
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "Male");
        students.add(student);
        when(studentService.findAll()).thenReturn(students.toString());

        // When
        String result = studentController.listStudents();

        // Then
        assertEquals(students.toString(), result);
        verify(studentService, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        // Given
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "Male");
        when(studentService.findById(anyInt())).thenReturn(Optional.of(student));

        // When
        ResponseEntity<Student> result = studentController.getStudentById(1);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());
        verify(studentService, times(1)).findById(1);
    }

    @Test
    void testGetStudentByIdNotFound() {
        // Given
        when(studentService.findById(anyInt())).thenReturn(Optional.empty());

        // When
        ResponseEntity<Student> result = studentController.getStudentById(1);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(studentService, times(1)).findById(1);
    }

    @Test
    void testAddStudent() {
        // Given
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "Male");
        when(studentService.save(student)).thenReturn(student);

        // When
        Student result = studentController.addStudent(student);

        // Then
        assertEquals(student, result);
        verify(studentService, times(1)).save(student);
    }

    @Test
    void testUpdateStudent() {
        // Given
        Student student = new Student(1, "John", "Doe", "john.doe@example.com", "Male");
        when(studentService.save(student)).thenReturn(student);

        // When
        Student result = studentController.updateStudent(student);

        // Then
        assertEquals(student, result);
        verify(studentService, times(1)).save(student);
    }

    @Test
    void testDeleteStudent() {
        // Given
        int studentId = 1;

        // When
        ResponseEntity<String> result = studentController.deleteStudent(studentId);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Employee deleted successfully!.", result.getBody());
        verify(studentService, times(1)).deleteById(studentId);
    }
}