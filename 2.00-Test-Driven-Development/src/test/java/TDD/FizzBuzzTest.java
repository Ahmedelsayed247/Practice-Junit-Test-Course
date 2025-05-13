package TDD;

import org.example.TDD.FizzBuzz;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvSources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

    // If number divisible by 3, print Fizz
    @Test
    @DisplayName("Divisible by Three")
    @Order(1)
    void TestForDivisibleByTree() {
        String expected = "Fizz";
        assertEquals(expected, FizzBuzz.compute(3), "Should return Fizz");
    }

    // If number divisible by 5, print Buzz
    @Test
    @DisplayName("Divisible by Five")
    @Order(1)
    void TestForDivisibleByFive() {
        String expected = "Buzz";
        assertEquals(expected, FizzBuzz.compute(5), "Should return Buzz");
    }

    // If number divisible by 3 and 5 print FizzBuzz
    @Test
    @DisplayName("Divisible by Tree And Five")
    @Order(1)
    void TestForDivisibleByTreeAndFive() {
        String expected = "FizzBuzz";
        assertEquals(expected, FizzBuzz.compute(15), "Should return FizzBuzz");
    }

    // If number is not divisible by 3 and 5, then print the number
    @Test
    @DisplayName("Not Divisible by Tree And Five")
    @Order(1)
    void TestForNotDivisibleByTreeAndFive() {
        String expected = "2";
        assertEquals(expected, FizzBuzz.compute(2), "Should return 2");
    }

    // Test Loop Over Array
    @Test
    @DisplayName("Test Loop Over Array Without Parameterized")
    @Order(1)
    void TestLoopOverArray() {
        String[][] data = {{"1", "1"},
                {"2", "2"},
                {"3", "Fizz"},
                {"4", "4"},
                {"5", "Buzz"},
                {"6", "Fizz"},
                {"7", "7"}
        };
        for (int i = 0 ; i< data.length; i++) {
            String value = data[i][0] ;
            String expected = data[i][1];

            assertEquals(expected,FizzBuzz.compute(Integer.parseInt(value)) );
        }
    }
    @DisplayName("Testing with CSV data")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, Fizz",
            "4, 4",
            "5, Buzz",
            "6, Fizz",
            "7, 7"
    })
    @Order(6)
    void testCsvData(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }
    @DisplayName("Testing with CSV File")
    @ParameterizedTest(name = "value={0}, expected={1}")
    @CsvFileSource(resources = "/fizzBuzzTest.csv", numLinesToSkip = 0)
    @Order(6)
    void testCsvFile(int value, String expected) {
        assertEquals(expected, FizzBuzz.compute(value));
    }



}
