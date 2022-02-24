public class Test {

    private Test(){ throw new AssertionError(); }

    // only arguments of T or it's superclass (which are all comparable) are allowed
    static <T extends Comparable<? super T>> void assertBounded(T low, T high, T actual, String error){ //in range of low-high
        if(actual.compareTo(low) < 0 || actual.compareTo(high) > 0){
            throw new AssertionError(error + ": expecting " + actual.getClass().toString().split("\s")[1] + " in range " + low + " to " + high +
                    " (inclusive), but got " + actual);
        }
    }

    static <T> void assertEquals(T expected, T actual, String error){
        if(!actual.equals(expected)){
            throw new AssertionError(error + ": expecting " + expected + " but got " + actual);
        }
    }

}
