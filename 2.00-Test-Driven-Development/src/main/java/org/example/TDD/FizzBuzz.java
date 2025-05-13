package org.example.TDD;

public class FizzBuzz {
    // If number divisible by 3, print Fizz
    // If number divisible by 5, print Buzz
    // If number divisible by 3 and 5 print FizzBuzz
    // If number is not divisible by 3 and 5, then print the number
//    public static String compute (int i ) {
//        if (i%3 == 0 && i %5== 0) {
//            return "FizzBuzz" ;
//        }
//        if (i %3 == 0) {
//            return "Fizz" ;
//        }
//        else if(i%5 == 0) {
//            return "Buzz" ;
//        }
//        return String.valueOf(i);
//    }
    public static String compute (int i ) {
       StringBuilder result = new StringBuilder() ;
       if(i%3 == 0) result.append("Fizz");
       if(i%5 == 0) result.append("Buzz") ;
       if(result.isEmpty())  result.append(i) ;
       return result.toString() ;
    }
}
