package com.mapCollage.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mapCollage.Models.Student;

import jakarta.servlet.http.HttpServletRequest;
@RestController
public class StudentController {

    List<Student> stuList = new ArrayList<>(List.of(
        new Student("Virendra", 18, "Viren@gmail.com"),
        new Student("Virendra", 19, "Viren@gmail.com"),
        new Student("Virendra", 20, "Viren@gmail.com")
    ));


    @GetMapping("/student")
    public ResponseEntity<List<Student>> getStudent() {
        System.out.println(stuList);
        return ResponseEntity.ok(stuList);
    }

    @PostMapping("/student")
    public ResponseEntity<Student> addStudentwithResponseEntity(@RequestBody Student stu) {
        stuList.add(stu);
        return ResponseEntity.status(HttpStatus.CREATED).body(stu);

//        return ResponseEntity.status(HttpStatus.CREATED).body(stu);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student stu) {
    	System.out.println("POSTMEthode");      
    	System.out.println(stu);
        stuList.add(stu);
        return stu;
    }
    
    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
    	System.out.println("CSRF toke getting");
    	return (CsrfToken)request.getAttribute("_csrf");
    }
}

//| Method                                      | Status |HTTP RESPONSE =
//Status + Headers + Body
//| ------------------------------------------- | ------ |
//| `ResponseEntity.ok()`                       | 200    |
//| `ResponseEntity.status(HttpStatus.CREATED)` | 201    |
//| `ResponseEntity.notFound()`                 | 404    |
//| `ResponseEntity.badRequest()`               | 400    |
//| `ResponseEntity.noContent()`                | 204    |

