public class Main {

    public static void main(String[] args) {

        String sudoku = "test.txt";
        Parser p = new Parser(sudoku);

        Solver res = new Solver(p.createFormula());
        res.print();
    }
}

