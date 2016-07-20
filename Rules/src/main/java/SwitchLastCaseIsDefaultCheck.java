/**
 * Created by Ferooz on 20/07/16.
 */

import static com.sun.tools.javac.util.Assert.error;

/**
 * RULE : SwitchLastCaseIsDefaultCheck
 * Switch statements should end with a "default" clause
 *
 * The requirement for a final default clause is defensive programming.
 * The clause should either take appropriate action, or contain a suitable comment as to why no action is taken.
 */
public class SwitchLastCaseIsDefaultCheck {
    public static void main(String args[])
    {
        int param = 1;
       // Noncompliant Code Example
        switch (param) {  //missing default clause
            case 0:
                doSomething();
                break;
            case 1:
                doSomethingElse();
                break;
        }

        switch (param) {
            default: // default clause should be the last one
                error();
                break;
            case 0:
                doSomething();
                break;
            case 1:
                doSomethingElse();
                break;
        }

        //Compliant Solution
        switch (param) {
            case 0:
                doSomething();
                break;
            case 1:
                doSomethingElse();
                break;
            default:
                error();
                break;
        }
    }

    private static void doSomethingElse() {
    }

    private static void doSomething() {
    }
}
