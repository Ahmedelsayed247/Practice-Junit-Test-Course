package com.student.grade.mvc.service;

import com.student.grade.mvc.Repository.HistoryGradeDao;
import com.student.grade.mvc.Repository.MathGradeDao;
import com.student.grade.mvc.Repository.ScienceGradeDao;
import com.student.grade.mvc.Repository.StudentDao;
import com.student.grade.mvc.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private MathGradeDao mathGradeDao;

    @Autowired
    private StudentGrades studentGrades;
    @Qualifier("historyGrades")
    @Autowired
    private HistoryGrade historyGrade;
    @Qualifier("scienceGrades")
    @Autowired
    private ScienceGrade scienceGrade ;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;
    @Autowired
    private ScienceGradeDao scienceGradeDao;
    @Autowired
    private HistoryGradeDao historyGradeDao;
    public void createStudent(String firstName, String lastName, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
        student.setId(0);
        studentDao.save(student);

    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentDao.findById(id);
        if (student.isPresent()) return true;

        return false;
    }

    public void deleteStudentById(int id) {
        if (checkIfStudentIsNull(id)) {
            studentDao.deleteById(id);
            mathGradeDao.deleteByStudentId(id) ;
            scienceGradeDao.deleteByStudentId(id) ;
            historyGradeDao.deleteByStudentId(id) ;

        }
    }

    public Iterable<CollegeStudent> getGradeBook() {
        return studentDao.findAll();
    }

    public boolean createGrade(double grade, int studentId, String gradeType) {
        if (!checkIfStudentIsNull(studentId)) return false;
        if (grade >= 0 && grade <= 100) {
            if (gradeType.equals("math")) {

                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradeDao.save(mathGrade);
                return true;
            }
            else if (gradeType.equals("science")){
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradeDao.save(scienceGrade);
                return  true;
            }
            else if (gradeType.equals("history")){
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradeDao.save(historyGrade);
                return true ;
            }

        }
        return false;
    }

    public int deleteGrade(int id, String gradeType) {
        int studentId = 0 ;
        if (gradeType.equals("math")){
            Optional<MathGrade> grade = mathGradeDao.findById(id) ;
            if(!grade.isPresent()) return studentId ;
            studentId = grade.get().getStudentId();
            mathGradeDao.deleteById(id);
        }
        else if (gradeType.equals("science")) {
            Optional<ScienceGrade> grade = scienceGradeDao.findById(id) ;
            if(!grade.isPresent()) return studentId ;
            studentId = grade.get().getStudentId();
            scienceGradeDao.deleteById(id);
        }
        else if (gradeType.equals("history")) {
            Optional<HistoryGrade> grade = historyGradeDao.findById(id) ;
            if(!grade.isPresent()) return studentId ;
            studentId = grade.get().getStudentId();
            historyGradeDao.deleteById(id);
        }
        return studentId ;
    }

    public GradebookCollegeStudent studentInformation(int id) {
        if(!checkIfStudentIsNull(id)) return null ;
        Optional<CollegeStudent> student = studentDao.findById(id);
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(id) ;

        List<Grade> mathGradeList = new ArrayList<>() ;
        mathGrades.forEach(mathGradeList::add);

        List<Grade> scienceGradeList = new ArrayList<>() ;
        scienceGrades.forEach(scienceGradeList::add);

        List<Grade> historyGradeList = new ArrayList<>() ;
        historyGrades.forEach(historyGradeList::add);

        studentGrades.setMathGradeResults(mathGradeList);
        studentGrades.setScienceGradeResults(scienceGradeList);
        studentGrades.setHistoryGradeResults(historyGradeList);
        GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(student.get().getId(),
                student.get().getFirstname(), student.get().getLastname(),student.get().getEmailAddress(),
                studentGrades
                );
        return gradebookCollegeStudent ;


    }
}
