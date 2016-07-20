/**
 * Created by Ferooz on 20/07/16.
 */

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * RULE : S1943
 * Using classes and methods that rely on the default system encoding
 *
 * Using classes and methods that rely on the default system encoding can result in code that works fine in its "home" environment.
 * But that code may break for customers who use different encodings in ways that are extremely difficult to diagnose and nearly,
 *      if not completely, impossible to reproduce when it's time to fix them.
 *
 */

public class S1943 {
 public static void main(String args[]) throws UnsupportedEncodingException {
     //In particular, this line uses the default platform encoding for reading in text from the console:
     Scanner read = new Scanner(System.in);
     
    // To make sure that the code works the same in different environments, we can change to
     Scanner read1 = new Scanner(System.in, "UTF-8");


     String s = "some text here";
     byte[] b = s.getBytes();//Noncompliant Code Example
     byte[] b1 = s.getBytes("UTF-8");//Solution
 }

}
