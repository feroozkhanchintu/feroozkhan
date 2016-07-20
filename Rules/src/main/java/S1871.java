/**
 * Created by Ferooz on 20/07/16.
 */

/*
 *  RULE : S1871
 *  Having two cases in the same switch statement
 *
 *  Having two cases in the same switch statement with the same implementation is at best duplicate code,
 *      and at worst a coding error.
 *  If the same logic is truly needed for both cases, then one should fall through to the other.
 *
 */
public class S1871 {
    public static void main(String args[])
    {
        int i = 1;

        switch (i) {
            case 1:
                doSomething();
                break;
            case 2:
                doSomethingDifferent();
                break;
            case 3:  // Noncompliant; duplicates case 1's implementation
                doSomething();
                break;
        }

       // Solution 1
        switch (i) {
            case 1:
            case 3:
                doSomething();
                break;
            case 2:
                doSomethingDifferent();
                break;
        }

        //Solution 2
        switch (i) {
            case 1:
                doSomething();
                break;
            case 2:
                doSomethingDifferent();
                break;
            case 3:
                doThirdThing();
                break;
        }

    }

    private static void doThirdThing() {
    }

    private static void doSomething() {
    }

    private static void doSomethingDifferent() {
    }
}
