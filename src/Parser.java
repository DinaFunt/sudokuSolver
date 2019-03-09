import java.io.*;

public class Parser {

    private String resultFile;
    private String sudoku;
    private int clauseCount;
    private static int varCount = 729;

    public Parser(String sudoku) {
        this.sudoku = sudoku;
        this.clauseCount = 0;
        resultFile = "formula.cnf";
    }

    public String createFormula() {

        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j, k, t, a, b;

//reading sudoku file and creating formula
        try {
            FileReader reader = new FileReader(sudoku);
            int c;
            while((c = reader.read()) != -1){
                if ((char)c != ' ' && c != 10) {
                    a = c - '0';
                    a += i * 9;
                    sb.append(a + " 0\n");
                    clauseCount++;
                }

                i++;
            }
            reader.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }

//1 point of formula
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                for (k = 1; k <= 9; k++) {
                    for (t = 1; t <= 9; t++) {
                        if (t != k) {
                            sb.append((-1) * (i * 81 + j * 9 + k) + " " + (-1) * (i * 81 + j * 9 + t) + " 0\n");
                            clauseCount++;
                        }
                    }
                }
            }
        }

//2 point of f.
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                for (k = 1; k <= 9; k++) {
                    sb.append((i * 81 + j * 9 + k) + " ");
                }
                sb.append(" 0\n");
                clauseCount++;
            }
        }

//3' point of f.
        for (i = 0; i < 9; i++) {
            for (k = 1; k <= 9; k++) {
                for (j = 0; j < 9; j++) {
                    sb.append((i * 81 + j * 9 + k) + " ");
                }
                sb.append(" 0\n");
                clauseCount++;
            }
        }

//4' point of f.
        for (j = 0; j < 9; j++) {
            for (k = 1; k <= 9; k++) {
                for (i = 0; i < 9; i++) {
                    sb.append((i * 81 + j * 9 + k) + " ");
                }
                sb.append(" 0\n");
                clauseCount++;
            }
        }

//3 point of f.
        for (i = 0; i < 9; i++) {
            for (k = 1; k <= 9; k++) {
                for (j = 0; j < 9; j++) {
                    for (t = 0; t < 9; t++) {
                        if (t != j) {
                            sb.append((-1) * (i * 81 + j * 9 + k) + " " + (-1) * (i * 81 + t * 9 + k) + " 0\n");
                            clauseCount++;
                        }
                    }
                }
            }
        }

//4 point of f.
        for (j = 0; j < 9; j++) {
            for (k =1; k <= 9; k++) {
                for (i = 0; i < 9; i++) {
                    for (t = 0; t < 9; t++) {
                        if (t != i) {
                            sb.append((-1) * (i * 81 + j * 9 + k) + " " + (-1) * (t * 81 + j * 9 + k) + " 0\n");
                            clauseCount++;
                        }
                    }
                }
            }
        }

//5 point of f.
        for (a = 0; a < 3; a++) {
            for (b = 0; b < 3; b++) {
                for (i = 0; i < 3; i++) {
                    for (j = 0; j < 3; j++) {
                        for (k = 1; k <= 9; k++) {
                            for (t = 1; t <= 9; t++) {
                                if (t != k) {
                                    sb.append((-1) * ((3 * a + i) * 81 + (3 * b + j) * 9 + k) + " " + (-1) * ((3 * a + i) * 81 + (3 * b + j) * 9 + t) + " 0\n");
                                    clauseCount++;
                                }
                            }
                        }
                    }
                }
            }
        }

//5' point of f.
        for (k = 1; k <= 9; k++) {
            for (a = 0; a < 3; a++) {
                for (b = 0; b < 3; b++) {
                    for (i = 0; i < 3; i++) {
                        for (j = 0; j < 3; j++) {
                            sb.append(((3 * a + i) * 81 + (3 * b + j) * 9 + k) + " ");

                        }
                    }
                    sb.append("0\n");
                    clauseCount++;
                }
            }
        }

        try {
            sb.insert(0, "p " + "cnf " + varCount + " " + clauseCount + "\n");
            FileWriter writer = new FileWriter(resultFile, false);
            writer.write(sb.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultFile ;
    }
}
