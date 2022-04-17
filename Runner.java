public class Runner {
    public static void main(String[] args){
//        Matrix A = new Matrix(new ArrayList<Double>(Arrays.asList( //enter GE matrix as a flat array
//                -0.3d, 0.1d, 0.3d, 2d,
//                0.2d, -0.4d, 0.4d, 1d,
//                0.1d, 0.3d, -0.7d, -2d
//
//        )));
        while(true){
            MatrixGE A = new MatrixGE(Input.getInstance().prompt());
            System.out.println(A);

            //Test.assertBounded("a", "a", "b", "range error in runner");

            Eliminator e = new Eliminator(A);
            System.out.println(e);
        }
    }
}
