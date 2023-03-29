package com.luv2code.springboot.service;

import com.luv2code.springboot.dao.StudentRepository;
import com.luv2code.springboot.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() throws Exception {
        student = new Student(1, "John", "Doe", "john.doe@example.com", "male");
    }

    @Test
    void testFindAll() {
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentRepository.findAll()).thenReturn(students);

        String result = studentService.findAll();

        assertThat(result).isEqualTo(students.toString());
    }

    @Test
    void testFindById() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.findById(1);

        assertThat(result).isEqualTo(Optional.of(student));
    }

    @Test
    void testSave() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentService.save(student);

        assertThat(result).isEqualTo(student);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteById() {
        studentService.deleteById(1);

        verify(studentRepository, times(1)).deleteById(1);
    }

}


