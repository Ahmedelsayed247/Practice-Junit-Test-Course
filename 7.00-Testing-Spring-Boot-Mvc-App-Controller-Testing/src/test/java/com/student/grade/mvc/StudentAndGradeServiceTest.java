package com.student.grade.mvc;

import com.student.grade.mvc.Repository.StudentDao;
import com.student.grade.mvc.model.CollegeStudent;
import com.student.grade.mvc.service.StudentAndGradeService;
import jakarta.persistence.Access;
import jakarta.persistence.Table;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
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
    private JdbcTemplate jdbc ;

    @BeforeEach
    public void setupDataBase () {
        jdbc.execute("insert into student(firstname,lastname ,email_address) "+
                "values('Ahmed', 'el sayed' ,'ahmed@gmail.com') "
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
        assertTrue(deleteCollegeStudent.isPresent() , "Return true");
        studentService.deleteStudentById(1) ;
        deleteCollegeStudent = studentDao.findById(1);
        assertFalse(deleteCollegeStudent.isPresent() , "Return false");
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
    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
        jdbc.execute("alter table student alter column id restart with 1");

    }
}
