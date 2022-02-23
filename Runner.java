import java.util.ArrayList;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args){
        Matrix A = new Matrix(new ArrayList<Double>(Arrays.asList( //enter GE matrix as a flat array
                1d, -2d, 3d, 1d, 1d,
                2d, -1d, 1d, 3d, 1d,
                4d, 1d, 2d, 5d, 2d,
                6d, -3d, 4d, 3d, -1d
        )));
        System.out.println(A);

        Eliminator e = Eliminator.getInstance(A);
        System.out.println(e);
    }
}
