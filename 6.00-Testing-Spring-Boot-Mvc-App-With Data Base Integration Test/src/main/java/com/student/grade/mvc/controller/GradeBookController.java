package com.student.grade.mvc.controller;

import com.student.grade.mvc.model.Gradebook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GradeBookController {

    @Autowired
    private Gradebook gradebook;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getStudents(Model m) {
        return "index";
    }


    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model m) {
        return "studentInformation";
    }

}
