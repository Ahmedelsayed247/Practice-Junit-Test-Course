package junitdemp;

import org.example.junitdemo.DemoUtils;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {
    DemoUtils demoUtils ;
    @BeforeEach
    void setupBeforeEach() {
        //setup
         demoUtils = new DemoUtils() ;
        System.out.println("@BeforeEach execution before the execution of each test method");
    }
    @AfterEach
    void tearDownAfterEach() {
        System.out.println("Running @AfterEach\n");
    }
    @BeforeAll
    static void setupBeforeEachClass() {
        System.out.println("@BeforeAll executes only once before all test methods execution in the class");
    }
    @AfterAll
    static void tearDownAfterEachClass() {
        System.out.println("@AfterAll executes only once before all test methods execution in the class");
    }
    @Test
    @Order(1)
    void testEqualsAndNotEquals() {
        System.out.println("Running test: testEqualsAndNotEquals");
        assertEquals(6 , demoUtils.add(2,4) , "2+4 must be 6") ;
        assertNotEquals(7, demoUtils.add(2,4) , "2+4 must not be 7");
    }
    @Test
    @Order(2)
    void testNullAndNotNull () {
        System.out.println("Running test: testNullAndNotNull");
        String str1 = null ;
        String str2 = "ahmed" ;
        assertNull(demoUtils.checkNull(str1), "Object should be null ");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null ");
    }
    @Test
    @Order(3)
    void testSameAndNotSame() {
        System.out.println("Running test: testSameAndNotSame");

        String str = "luv2code" ;
            assertSame (demoUtils.getAcademy() , demoUtils.getAcademyDuplicate() , "Objects should refer to same object") ;
            assertNotSame(str, demoUtils.getAcademy() , "Objects should not refer to same object");
    }
    @Test
    @Order(4)
    void testTrueFalse() {
        int gradeOne =10 ;
        int gradeTwo = 5 ;
        assertTrue(demoUtils.isGreater(gradeOne,gradeTwo) , "This should return true");
        assertFalse(demoUtils.isGreater(gradeTwo,gradeOne) ,"This should return false   ");
    }
    @Test
    @Order(5)
    void testArrayEquals() {
        String [] stringArray= {"A" , "B" , "C"};

        assertArrayEquals(stringArray , demoUtils.getFirstThreeLettersOfAlphabet() , "Arrays Should be the same");
    }
    @Test
    @Order(6)
    void testIterableEquals () {
        List<String> theList = List.of("luv" , "2" , "code");
        assertIterableEquals(theList , demoUtils.getAcademyInList() , "Expected List should be same as actual list");
    }
    @Test
    @Order(7)
    void testLinesMatch() {
        List<String> theList = List.of("luv" , "2" , "code");
        assertLinesMatch(theList , demoUtils.getAcademyInList() , "Lines Should Match");
    }
    @Test
    @Order(8)
    void testThrowsAndDoesNotThrows() {
        assertThrows(Exception.class , ()-> {demoUtils.throwException(-1);} , "should throw exception" );
        assertDoesNotThrow(()->{demoUtils.throwException(5);} , "Should not throw exception");
    }
    @Test
    @Order(9)
    void testTimeOut() {
        assertTimeoutPreemptively(Duration.ofSeconds(3) , ()->{demoUtils.checkTimeout();} , "Method should execute in 3 seconds");

    }
}
