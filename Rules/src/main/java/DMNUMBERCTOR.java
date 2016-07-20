/**
 * Created by Ferooz on 20/07/16.
 */

/*  RULE : DM_NUMBER_CTOR
 *  Method invokes inefficient Number constructor; use static valueOf instead.
 *
 *	Using new Integer(int) is guaranteed to always result in a new object whereas Integer.valueOf(int)
 *	        allows caching of values to be done by the compiler, class library, or JVM.
 *	Using of cached values avoids object allocation and the code will be faster.
 *	Values between -128 and 127 are guaranteed to have corresponding cached instances
 *	    and using valueOf is approximately 3.5 times faster than using constructor.
 *	        For values outside the constant range the performance of both styles is the same.
 *	The caching happens only if you use Integer.valueOf(int), not if you use new Integer(int).
 *	    The autoboxing used by you uses Integer.valueOf
 *	Returns an Integer instance representing the specified int value.
 *	If a new Integer instance is not required, this method should generally be used in preference to the constructor Integer(int),
 *	    as this method is likely to yield significantly better space and time performance by caching frequently requested values.
 */

public class DMNUMBERCTOR {
    /*	Method invokes inefficient Number constructor; use static valueOf instead (DM_NUMBER_CTOR)*/

    public static void main(String args[]) {
        Integer x = new Integer(0); //This forces it to create a new object which is different to the boxed object.

        Integer x1 = Integer.valueOf(0); //It returns from cache

    }
}

/* Implentation of Integer and Integer.valueOf() in JAVA

    public static Integer valueOf(int i) {
        final int offset = 128;
        if (i >= -128 && i <= 127) { // must cache
            return IntegerCache.cache[i + offset];
        }
        return new Integer(i);
    }

    public Integer(int value) {
        this.value = value;
    }


*/