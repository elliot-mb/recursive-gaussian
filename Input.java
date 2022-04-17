import org.w3c.dom.DOMImplementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Input {
    static private Input input = null;
    private final String separator = "\s+"; //regex
    private final String number    = "-*[0-9]+(\\.[0-9]+)*"; //regex
    private final BufferedReader reader;

    private Input(){
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static Input getInstance(){ //singleton
        if(input == null){
            input = new Input();
        }
        return input;
    }

    private String safeRead(){ //returns error string on failure
        try{
            return reader.readLine();
        }catch(IOException e){
            return "I/O: " + e;
        }
    }

    private List<Double> safeParseRow(){ //parse a row
        List<Double> row;
        String ln;
        while(true){
            ln = safeRead();
            row = parseRow(ln);
            try {
                Test.assertEquals(ln.split(separator).length, row.size(), "One or more elements of this row have bad formatting");
                return row;
            }catch(AssertionError e) { System.out.println(e); }
        }
    }

    private List<Double> parseRow(String ln){
        String[] elem = ln.split(separator);
        List<Double> row = new ArrayList<Double>();
        Arrays.stream(elem)
                .filter(x -> x.matches(number))
                .map(Double::parseDouble)
                .forEach(row::add);
        return row;
    }

    public ArrayList<Double> prompt(){
        System.out.println("Please enter the first row of a GE matrix (elements as integers and/or decimals separated by spaces)");
        ArrayList<Double> m = new ArrayList<Double>();
        int rows, columns;

        m.addAll(safeParseRow());
        columns = m.size();
        rows = m.size() - 1;

        System.out.println("Entered first row of this " + rows + "x" +
                            columns + " matrix (please enter the rest)\n" + m);
        //.chars().filter(x -> (x >= '0' && x <= '9') || x == '.' || x == ' '))
        //reprints what was entered, so the user can enter more rows below

        for(int i = 1; i < rows; i++){
            List<Double> row = safeParseRow();
            if(row.size() != columns){
                System.out.println("Incorrect number of columns; please enter this row again.");
                i--;
            }else{ m.addAll(row); }
        }
        return m;
    }
}
