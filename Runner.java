import java.util.ArrayList;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args){
        Matrix A = new Matrix(new ArrayList<Double>(Arrays.asList( //enter GE matrix as a flat array
            1d
        )));
        System.out.println(A);

        //Test.assertBounded("a", "a", "b", "range error in runner");

        Eliminator e = Eliminator.getInstance(A);
        System.out.println(e);
    }
}
