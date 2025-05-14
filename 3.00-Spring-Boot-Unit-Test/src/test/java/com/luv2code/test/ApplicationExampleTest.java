package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

    private static int count = 0;
    @Value("${info.app.name}")
    private String appInfo;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Value("${info.school.name}")
    private String SchoolName;

    @Autowired
    CollegeStudent collegeStudent;
    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext context ;

    @BeforeEach
    public void beforEach() {
        count += 1;
        System.out.println("Testing: " + appInfo + "which is " + appDescription
                + " Version: " + appVersion + ". Execution of test method " + count
        );
        collegeStudent.setFirstname("Ahmed");
        collegeStudent.setLastname("el sayed");
        collegeStudent.setEmailAddress("ahmed@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
        collegeStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("Add grade results for student grades")
    @Test
    public void addGradeResultsForStudentGrades() {
        assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Add grade results for student grades not equal")
    @Test
    public void addGradeResultsForStudentGradesAssertNotEquals() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Is grade greater")
    @Test
    public void isGradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(90, 70), "failure - Should be true");
    }

    @DisplayName("Is grade greater false")
    @Test
    public void isGradeGreaterStudentGradesFalse() {
        assertFalse(studentGrades.isGradeGreater(70, 90), "failure - Should be False");
    }

    @DisplayName("Check Null For Student grades")
    @Test
    public void CheckNullForStudentGrades() {
        assertNotNull(studentGrades.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()), "Object Should not be null");
    }

    @DisplayName("Create student without grade init")
    @Test
    public void CreateStudentWithoutGradeInit() {
        CollegeStudent student2 = context.getBean("collegeStudent" , CollegeStudent.class);
        student2.setFirstname("Ali");
        student2.setLastname("el sayed");
        student2.setEmailAddress("Ali@gmail.com");
        assertNotNull(student2.getFirstname());
        assertNotNull(student2.getLastname());
        assertNotNull(student2.getEmailAddress());
        assertNull(student2.getStudentGrades());

    }
    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsArePrototypes() {
        CollegeStudent student2 = context.getBean("collegeStudent" , CollegeStudent.class);
        assertNotSame(collegeStudent,student2);
    }
    @Test
    public void findGradePointAverage() {
        assertAll("Testing all assertEquals",
                () -> assertEquals(
                        353.25,
                        studentGrades.addGradeResultsForSingleClass(
                                collegeStudent.getStudentGrades().getMathGradeResults()
                        )
                ),
                () -> assertEquals(
                        88.31,
                        studentGrades.findGradePointAverage(
                                collegeStudent.getStudentGrades().getMathGradeResults()
                        )
                )
        );
    }



}

