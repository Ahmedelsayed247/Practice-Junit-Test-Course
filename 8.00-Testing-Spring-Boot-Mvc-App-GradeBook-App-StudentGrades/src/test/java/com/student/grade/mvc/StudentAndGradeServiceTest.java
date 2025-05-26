package com.student.grade.mvc;

import com.student.grade.mvc.Repository.HistoryGradeDao;
import com.student.grade.mvc.Repository.MathGradeDao;
import com.student.grade.mvc.Repository.ScienceGradeDao;
import com.student.grade.mvc.Repository.StudentDao;

import com.student.grade.mvc.model.*;

import com.student.grade.mvc.service.StudentAndGradeService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    StudentAndGradeService studentService ;
    @Autowired
    StudentDao studentDao ;
    @Autowired
    MathGradeDao mathGradeDao ;
    @Autowired
    ScienceGradeDao scienceGradeDao ;
    @Autowired
    HistoryGradeDao historyGradeDao ;
    @Autowired
    private JdbcTemplate jdbc ;

    @BeforeEach
    public void setupDataBase () {
        jdbc.execute("insert into student(firstname,lastname ,email_address) "+
                "values('Ahmed', 'el sayed' ,'ahmed@gmail.com') "
                );
        jdbc.execute("insert into math_grade(student_id,grade) "+
                "values(1,100.0) "
        );
        jdbc.execute("insert into science_grade(student_id,grade) "+
                "values(1,100.0) "
        );
        jdbc.execute("insert into history_grade(student_id,grade) "+
                "values(1,100.0) "
        );
    }
    @Test
    public void createStudentService() {
        studentService.createStudent("Chad","Darby" ,"chad.darby@luv2code_school.com");

        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");

        assertEquals("chad.darby@luv2code_school.com" , student.getEmailAddress() ,"find by email");
    }
    @Test
    public void isStudentNullCheck(){
        assertTrue(studentService.checkIfStudentIsNull(1));
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> deleteCollegeStudent = studentDao.findById(1);
        Optional<MathGrade> deleteMathGrade =mathGradeDao.findById(1) ;
        Optional<ScienceGrade> deleteScienceGrade =scienceGradeDao.findById(1) ;
        Optional<HistoryGrade> deleteHistoryGrade =historyGradeDao.findById(1) ;

        assertTrue(deleteCollegeStudent.isPresent() , "Return true");
        assertTrue(deleteMathGrade.isPresent());
        assertTrue(deleteHistoryGrade.isPresent());
        assertTrue(deleteScienceGrade.isPresent());
        studentService.deleteStudentById(1) ;
        deleteCollegeStudent = studentDao.findById(1);
        deleteMathGrade = mathGradeDao.findById(1);
        deleteHistoryGrade = historyGradeDao.findById(1) ;
        deleteScienceGrade = scienceGradeDao.findById(1) ;

        assertFalse(deleteCollegeStudent.isPresent() , "Return false");
        assertFalse(deleteMathGrade.isPresent());
        assertFalse(deleteHistoryGrade.isPresent());
        assertFalse(deleteScienceGrade.isPresent());
    }
    @Sql("/insertData.sql")
    @Test
    public void getGradeBookService() {
        Iterable<CollegeStudent> iterableCollageStudents = studentService.getGradeBook() ;
        List<CollegeStudent> collegeStudents = new ArrayList<>() ;
        for(CollegeStudent collegeStudent : iterableCollageStudents) {
            collegeStudents.add(collegeStudent) ;
        }
        assertEquals(5, collegeStudents.size());
    }
    @Test
    public void createGradeService()  {
        // create the Grade
        assertTrue(studentService.createGrade(80.50,1,"math"));
        assertTrue(studentService.createGrade(80.50,1,"science"));
        assertTrue(studentService.createGrade(80.50,1,"history"));
        //get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradeDao.findGradeByStudentId(1) ;
        Iterable<ScienceGrade> scienceGrades = scienceGradeDao.findGradeByStudentId(1) ;
        Iterable<HistoryGrade> historyGrades = historyGradeDao.findGradeByStudentId(1) ;
        //verify there is grade
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,"Student has math grades");
        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,"Student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2,"Student has history grades");


    }
    @Test
    public void createGradeServiceReturnFalse() {
        assertFalse(studentService.createGrade(105 , 1, "math"));
        assertFalse(studentService.createGrade(-5 , 1, "math"));
        assertFalse(studentService.createGrade(80.40 , 2, "math"));
        assertFalse(studentService.createGrade(80.40 , 2, "literature   "));

    }
    @Test
    public void deleteGradeService() {
        assertEquals(1,studentService.deleteGrade(1,"math") , "Return student id after delete");
        assertEquals(1,studentService.deleteGrade(1,"science") , "Return student id after delete");
        assertEquals(1,studentService.deleteGrade(1,"history") , "Return student id after delete");

    }
    @Test
    public void deleteGradeServiceReturnStudentIdOfZero() {
        assertEquals(0,studentService.deleteGrade(0,"math") , "no student should have 0 id");
        assertEquals(0,studentService.deleteGrade(0,"literature") , "no student should have a literature class");
    }

    @Test
    public void studentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(1 ,gradebookCollegeStudent.getId());
        assertEquals("Ahmed" ,gradebookCollegeStudent.getFirstname());
        assertEquals("el sayed" , gradebookCollegeStudent.getLastname());
        assertEquals("ahmed@gmail.com",gradebookCollegeStudent.getEmailAddress());
        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1 );
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);

    }
    @Test
    public void studentInformationServiceReturnNull ()  {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0) ;
        assertNull(gradebookCollegeStudent);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
        jdbc.execute("delete from math_grade");
        jdbc.execute("delete from science_grade");
        jdbc.execute("delete from history_grade");

        jdbc.execute("Alter table student alter column id restart with 1");
        jdbc.execute("Alter table math_grade alter column id restart with 1");
        jdbc.execute("Alter table science_grade alter column id restart with 1");
        jdbc.execute("Alter table history_grade alter column id restart with 1");

    }
}
