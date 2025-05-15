package com.student.grade.mvc.service;

import com.student.grade.mvc.Repository.StudentDao;
import com.student.grade.mvc.model.CollegeStudent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentDao studentDao ;

    public void createStudent(String firstName, String lastName , String emailAddress){
        CollegeStudent student = new CollegeStudent(firstName,lastName,emailAddress) ;
        student.setId(0);
        studentDao.save(student);

    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id) ;
        if(student.isPresent()) return true ;

        return false;
    }

    public void deleteStudentById(int id) {
        if(checkIfStudentIsNull(id)){
            studentDao.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }
}
