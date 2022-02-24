import java.util.ArrayList;

public class Matrix {
    private final int rows, columns;
    private ArrayList<ArrayList<Double>> A;

    Matrix(ArrayList<Double> flat){
        this.rows = (int) Math.floor(Math.sqrt(flat.size()));
        this.columns = rows + 1; // x rows of x+1 columns
        this.A = new ArrayList<ArrayList<Double>>();
        pack(flat);
    }

    private void pack(ArrayList<Double> f){ //finds the size and makes array 2d robustly
        int missing, upperBound, lowerBound;
        missing = (rows * columns) - f.size();
        if(missing > 0){ System.out.println("Warning, not a full Gaussian Elimination matrix. " + missing + " coefficients undefined." ); }
        if(missing < 0){ System.out.println("Warning, overdefined Guassian Elimination matrix. " + missing * -1 + " extra coefficients."); }
        for(int i = 0; i < rows; i++){
            lowerBound = Math.min(i * (columns), f.size() - 1);
            upperBound = Math.min(lowerBound + columns, f.size());
            ArrayList<Double> row = new ArrayList<Double>(f.subList(lowerBound, upperBound));
            while(row.size() < columns) { row.add(0d); } //fills out empty coefficients with 0s
            A.add(row); //one row at a time
        }
    }

    public String toString(){
        String s = rows + "x" + columns + " Matrix";
        for (ArrayList<Double> row : A){
            s += "\n" + row;
        }
        return s;
    }

    //getters
    public int getRows(){ return rows; }
    public int getColumns(){ return columns; }
    public ArrayList<Double> getRow(int i){ return A.get(i); }
    public ArrayList<Double> copyRow(int i){ return (ArrayList<Double>) A.get(i).clone(); }
    public Double getElem(int i, int j){ return A.get(i).get(j); } //row, column (zero indexed)
    public ArrayList<ArrayList<Double>> getMatrix() { return A; }

    //setters
    public void setRow(int i, ArrayList<Double> row){ A.set(i, row); }

}
