import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.IOException;

public class Solver {
    private int[][] array;
    private int[] solve;
    private String formulaFile;
    private static int varCount = 729;

    public Solver(String file) {
        array = new int[9][9];
        this.solve = new int[varCount];
        this.formulaFile = file;
    }

    private void solve() {
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600);

        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance(formulaFile);

            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable!");

                solve = problem.model();
            } else {
                System.out.println("Unsatisfiable !");
            }
        } catch (ParseFormatException | IOException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }

    }

    private void getArray() {
        solve();
        int col, row, val;

        for (int i = 0; i < varCount; i++) {

            if (solve[i] > 0) {
                row = (i / 81);
                col = (i % 81) / 9;
                val = (i % 9) + 1;
                array[row][col] = val;
            }
        }
    }

    public void print() {
        getArray();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }

}
