package com.student.grade.mvc.controller;

import com.student.grade.mvc.model.CollegeStudent;
import com.student.grade.mvc.model.Gradebook;
import com.student.grade.mvc.service.StudentAndGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeBookController {

    @Autowired
    private Gradebook gradebook;

    @Autowired
    private StudentAndGradeService studentService ;

    @PostMapping(value = "/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student , Model m) {
        studentService.createStudent(student.getFirstname(),student.getLastname(),student.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        m.addAttribute("students" , collegeStudents) ;
       return "index" ;
    }
    @GetMapping(value = "/delete/student/{id}")
    public String deleteStudentById(@PathVariable int id , Model m ) {
        if(!studentService.checkIfStudentIsNull(id)) return "error";
        studentService.deleteStudentById(id);
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        m.addAttribute("students" , collegeStudents) ;
        return "index" ;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradeBook();
        m.addAttribute("students" , collegeStudents) ;
        return "index";

    }


    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

}
