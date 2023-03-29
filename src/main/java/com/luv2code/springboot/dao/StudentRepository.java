package com.luv2code.springboot.dao;

import com.luv2code.springboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    // that's it ... no need to write any code LOL!

    // add a method to sort by last name
    //public List<Student> findAllByOrderByLastNameAsc();

}