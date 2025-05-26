package com.student.grade.mvc;

import com.student.grade.mvc.Repository.StudentDao;
import com.student.grade.mvc.model.CollegeStudent;
import com.student.grade.mvc.model.GradebookCollegeStudent;
import com.student.grade.mvc.service.StudentAndGradeService;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradeBookControllerTest {
    private static MockHttpServletRequest request;
    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentDao  studentDao ;
    @Mock
    private StudentAndGradeService studentCreateServiceMock;

    @BeforeAll
    public static void setUp() {
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "ahmed");
        request.setParameter("lastname", "el sayed");
        request.setParameter("emailAddress", "ahmed@gmail.com");
    }

    @BeforeEach
    public void beforeEach() {
        jdbc.execute("insert into student(firstname,lastname ,email_address) " + "values('Ahmed', 'el sayed' ,'ahmed@gmail.com') ");

    }

    @Test
    public void getStudentHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradebookCollegeStudent("Ahmed", "El sayed", "ahmed@gmail.com");
        CollegeStudent studentTwo = new GradebookCollegeStudent("Ali", "El sayed", "Alid@gmail.com");
        List<CollegeStudent> collegeStudents = new ArrayList<>(Arrays.asList(studentOne, studentTwo));
        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudents);
        assertIterableEquals(collegeStudents, studentCreateServiceMock.getGradeBook());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    public void createStudentHttpRequest() throws Exception {
        CollegeStudent studentOne = new CollegeStudent("Ahmed", "El sayed", "ahmed@gmail.com");

        List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne));

        when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentCreateServiceMock.getGradeBook());

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", "Ahmed")
                        .param("lastname", "Sayed")
                        .param("emailAddress", "ahmedsayed@example.com"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView() ;
        ModelAndViewAssert.assertViewName(mav ,"index"); ;

        CollegeStudent verifyStudent = studentDao.findByEmailAddress("ahmedsayed@example.com") ;

        assertNotNull(verifyStudent , "Student Should be found") ;

    }
    @Test
    public void deleteStudentHttpRequest() throws Exception{
        assertTrue(studentDao.findById(1).isPresent());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}" ,1))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");
        assertFalse(studentDao.findById(1).isPresent());
    }
    @Test
    public void deleteStudentHttpRequestErrorPage()throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete/student/{id}" ,0))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbc.execute("delete from student");
        jdbc.execute("alter table student alter column id restart with 1");

    }

}
