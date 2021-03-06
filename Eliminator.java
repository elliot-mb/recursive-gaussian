import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Eliminator {
    static Eliminator e;
    private MatrixGE m; //input matrix
    private ArrayList<Double> solution;

    public Eliminator(MatrixGE m){
        this.m = m;
        this.solution = eliminate(m);
    }

    //helpers
    private MatrixGE swapTop(MatrixGE m){ //if we have a zero at the 1st row 1st column
        int i = 0;
        while(i < m.getRows() && m.getElem(i, 0) == 0){ i++; }
        if (i == 0) return m;
        if (i == m.getRows()) return next(m); //this column is already all zero (skips to the next recursion)
        ArrayList<Double> top = m.copyRow(0), swap = m.copyRow(i);
        m.setRow(0, swap); m.setRow(i, top); //swaps the top with the first non-zero starting row
        return m;
    }

    private MatrixGE next(MatrixGE m){ //finds the next gaussian/recursive submatrix (bottom right corner)
        Test.assertBounded(1, Integer.MAX_VALUE, m.getRows(), "Eliminator.next can't go any deeper");
        ArrayList<Double> flat = new ArrayList<Double>();
        for(int i = 1; i < m.getRows(); i++){ //copy the matrix except for the first column and row
            flat.addAll(m.getRow(i).subList(1, m.getColumns()));
        }
        return new MatrixGE(flat);
    }

    private Double backSub(MatrixGE m, Integer row, ArrayList<Double> unknowns){
        List<Double> coeffs; Double divisor, sum = 0d; int offset = row == 0 ? 0 : 1;
        //offset is zero when we are on the top row (one with the most coefficients)
        divisor = m.getElem(row, offset);
        coeffs = m.getRow(row).subList(offset, m.getColumns() - 1);
        for(int i = 1; i < coeffs.size(); i++){ //starts on the second coefficient
            //unknowns start one later than our coefficients (the first coefficient is of the unknown we're finding)
            sum -= coeffs.get(i) * unknowns.get(unknowns.size() - i);
        } //zipwith
        sum += m.getElem(row, m.getColumns() - 1);
        return sum / divisor;
    }

    // recursive solution that eliminates the first column bar the topmost element
    // then works out what the missing coefficient is on the top
    private ArrayList<Double> eliminate(MatrixGE m0){
        Double fact, firstValue;
        ArrayList<Double> unknowns = new ArrayList<Double>(), newRow;
        MatrixGE m1, m2;
        m1 = swapTop(m0); //matrix with non-zero top-left element
        try{
            firstValue = m1.getElem(0, 0);
        } catch (AssertionError e){
            System.out.println(e);
            return new ArrayList<Double>();
        }
        for(int i = 1; i < m1.getRows(); i++){ //one row at a time starting with the second
            newRow = new ArrayList<Double>();
            fact = m1.getElem(i, 0) / firstValue; //starts by dividing n, n + i by the elimination coeff
            for(int j = 0; j < m1.getColumns(); j++){
                newRow.add(m1.getElem(i, j) - (m1.getElem(0, j) * fact)); //subtract from the ith row, 0th row elements times factor
            }
            m1.setRow(i, newRow); //set the ith row
        }
        //System.out.println(m1);
        m2 = next(m1);
        if(m2.getColumns() >= 3) { //get sub-matrix to recurse on (we only need min of three columns)
            unknowns = eliminate(m2); //unknowns we've already worked out
        }else{ //must back-substitute twice on the smallest level
            unknowns.add(backSub(m1, m1.getRows() - 1, unknowns)); //does it on the last row for smallest matrix
        }
        unknowns.add(backSub(m1, 0, unknowns));

        return unknowns;
    }

    public String toString(){
        String out = "";
        DecimalFormat df = new DecimalFormat(); df.setMaximumFractionDigits(6);
        Collections.reverse(solution); //the recursive solution is backwards
        if(solution.size() > 0) { out += "Solutions \n"; }
        for(int i = 0; i < m.getRows(); i++){
            out += "x" + i + ": " + df.format(solution.get(i)) + "\n";
        }
        return out;
    }
}
