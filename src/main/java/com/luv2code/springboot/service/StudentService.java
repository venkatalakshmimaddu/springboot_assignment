package com.luv2code.springboot.service;


import com.luv2code.springboot.entity.Student;

import java.util.Optional;

public interface StudentService {

    public String findAll();

    public Optional<Student> findById(int theId);

    public Student save(Student theStudent);

    public void deleteById(int theId);
}
