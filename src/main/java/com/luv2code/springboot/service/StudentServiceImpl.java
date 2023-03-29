package com.luv2code.springboot.service;

import com.luv2code.springboot.dao.StudentRepository;
import com.luv2code.springboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository theStudentRepository) {
        studentRepository = theStudentRepository;
    }

    @Override
    public String findAll() {
        return studentRepository.findAll().toString();
    }

    @Override
    public Optional<Student> findById(int theId) {
        return studentRepository.findById(theId);
    }

    @Override
    public Student save(Student theStudent) {

        studentRepository.save(theStudent);
        return theStudent;
    }

    @Override
    public void deleteById(int theId) {

        studentRepository.deleteById(theId);
    }


}

