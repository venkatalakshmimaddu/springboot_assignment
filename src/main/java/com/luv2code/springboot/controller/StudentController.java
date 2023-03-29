package com.luv2code.springboot.controller;

import com.luv2code.springboot.entity.Student;
import com.luv2code.springboot.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService theStudentService) {
        studentService = theStudentService;
    }

    @GetMapping("/student")
    public String listStudents() {
        return studentService.findAll();
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {

        return studentService.findById(id).
                map(ResponseEntity::ok).
                orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/student")
    public Student addStudent(@RequestBody Student student){
        return studentService.save(student);

    }
    @PutMapping("/student")
    public Student updateStudent(@RequestBody Student student){
        return studentService.save(student);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") int studentId){

        studentService.deleteById(studentId);

        return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);

    }
}

