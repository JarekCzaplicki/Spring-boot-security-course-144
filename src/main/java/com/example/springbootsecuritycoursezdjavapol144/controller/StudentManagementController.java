package com.example.springbootsecuritycoursezdjavapol144.controller;

import com.example.springbootsecuritycoursezdjavapol144.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Adam Małysz"),
            new Student(2, "Britney Spears"),
            new Student(3, "Anna Smith")
    );
    // Strategie dla @PreAuthorize
    // hasRole('ROLE_'), hasAnyRole('ROLE_'), hasAuthority('permission'), hasAuthority('permission')
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTREINEE')")
    public List<Student> getAllStudents() {
        System.out.println("getAllStudents");
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerNewStudent(@RequestBody Student student){
        System.out.println("registerNewStudent");
        System.out.println(student);
    }
    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable Integer studentId){
        System.out.println("deleteStudent");
        System.out.println(studentId);
    }

    @PutMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable Integer studentId, @RequestBody Student student){
        System.out.println("updateStudent");
        System.out.printf("id: %s, student: %s%n", studentId, student);
    }

    // dodatkowy endpoint który wyświetli na konsoli komunikat "witaj drogi " + name
    // "localhost:8080/management/api/v1/students/guest/jarek
    // ma być dostępny tylko dla użytkownika posiadającego 'guest:read'

}
