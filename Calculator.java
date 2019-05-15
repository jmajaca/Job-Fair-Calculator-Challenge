import java.io.*;
import java.util.*;
import static java.lang.Math.pow;

/**
 * Solution of Calculator Challenge
 * @author Josip Majača
 * Problem was solved using stack. If input was a number it would be pushed on stack, on the other hand if input was a +/-/* it 
 * would pop two numbers from stack and performed operation +/-/*. If input was a 'X' then null would be stored on stack. If operation 
 * was ment to be performed on 'X' (null on stack) it would add/substract sumX and multiply multiX, after that as result on stack would 
 * be pushed null. On the end number Y (from the task) and sumX are subtracted and then devided by multiX giving solution X.
 */
public class Calculator {

    private static LinkedList<String> input = new LinkedList<>();
    private static int sumX;
    private static int multiX;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null && !(line.isEmpty())) input.add(line);
        for(String s : input) {
            sumX = 0;
            multiX = 1;
            Stack<Integer> stack = new Stack<>();
            int end = 0;
            while(s.charAt(end) != ' ') end++;
            for(int i = s.length() - 1; i >= end; i--) {
                Character c = s.charAt(i);
                if(c.equals(' ')) continue;
                if(c.equals('+') || c.equals('-') || c.equals('*')) {
                    Integer first = stack.pop();
                    Integer second = stack.pop();
                    if (first == null) {
                        foundX(second, c);
                        stack.push(null);
                    } else if(second == null) {
                        foundX(first, c);
                        if(c.equals('-')) {
                            multiX = - multiX;
                            sumX = - sumX;
                        }
                        stack.push(null);
                    } else {
                        if(c.equals('+')) {
                            stack.push(first+second);
                        } else if(c.equals('-')) {
                            stack.push(first-second);
                        } else if(c.equals('*')) {
                            stack.push(first*second);
                        }
                    }
                } else {
                    if(c.equals('X')) {
                        stack.push(null);
                        continue;
                    }
                    stack.push(c - '0');
                }
            }
            double result = getNumber(s.substring(0, end));
            result -= sumX;
            result /= multiX;
            if(result % 1 == 0) {
                 System.out.printf((int)result + " ");
             } else {
                 System.out.printf("Err ");
             }
        }
    }
    /**
     * Method calculates multiplier of X and number that is added or subtracted from X
     */
    private static void foundX(int num, Character operation) {
        if(operation == '+') {
            sumX += num;
        } else if(operation == '-') {
            sumX -= num;
        } else if(operation == '*') {
            sumX *= num;
            multiX *= num;
        }
    }
    /**
     * Method calculates integer value of number stored as a String
     */
    private static int getNumber(String num) {
        int res = 0;
        for(int i = num.length()-1; i >= 0; i--) {
            if(num.substring(i,i+1).equals("-")) {
                res = - res;
                break;
            }
            res += Integer.valueOf(num.substring(i,i+1)) * pow(10, num.length() - 1 - i);
        }
        return  res;
    }
}
