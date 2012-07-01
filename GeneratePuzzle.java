/*
 * GeneratePuzzle.java
 */

/**
 * This program will generate a word-search puzzle based on words either
 * provided from System.in or a command-line argument named file.  The program
 * will then create 2 files ( .pz & .wl ) corresponding to the puzzle file and
 * the word-list file.
 *
 * @author jra3345 : James R. Aylesworth
 */
import java.io.*;
import java.util.*;

public class GeneratePuzzle {

    /**
     * A Character grid -- the puzzle itself
     */
    Character[][] thePuzzle = null;
    /**
     * the minimum number of a capital alpha-char in the ascii table
     */
    final int MINNUM = 65;
    /**
     * the maximum number of a capital alpha-char in the ascii table
     */
    final int MAXNUM = 90;
    /**
     * a random generator used to fill in the puzzle
     */
    private Random random = new Random();
    private int rows = 0;
    private int cols = 0;

    /**
     * The puzzle constructor
     * @param   row     the number of rows in this puzzle
     * @param   col     the number of cols in this puzzle
     */
    public GeneratePuzzle(int row, int col) {
        thePuzzle = new Character[row][col];
        this.rows = row;
        this.cols = col;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                thePuzzle[i][j] = '-';
            }

        }
    }

    /**
     * This method will return the puzzle object. Utilized for the creation
     * of the spaced .html file called from a static method (main).
     * @return  the puzzle
     */
    public Character[][] getPuzzle(){
        return thePuzzle;
    }

    /**
     * get the number of rows for this puzzle
     * @param aPuzzle   a puzzle referance
     * @return          the number of rows the puzzle has
     */
    private int getRows(GeneratePuzzle aPuzzle) {
        return this.rows;
    }

    /**
     * get the number of cols for this puzzle
     * @param aPuzzle   a puzzle referance
     * @return          the number of columns the puzzle has
     */
    private int getCols(GeneratePuzzle aPuzzle) {
        return this.cols;
    }

    /**
     * Returns the Character located and the provided position
     * @param x     the row of in the array of the position in question
     * @param y     the col of in the array of the position in question
     * @return      returns the Character in the specificed
     */
    private char getCharAt(int x, int y) {
        char result = this.thePuzzle[x][y];
        return result;
    }

    /**
     * a method to try placing the words into the puzzle. This method will try
     * to place each word 'N' times (N = rows * cols)
     */
    private void addWords(LinkedList wordList) {
        while (wordList.size() != 0) {
            //put the first word from the list into a string
            String aWord = (String) wordList.get(0);
            //remove the word from the list
            wordList.remove(0);
            int numTries = 0;

            while (numTries < rows * cols) {
                //Choose a random location on the puzzle to start placement of word
                int startRow = random.nextInt(rows);
                int startCol = random.nextInt(cols);

                if (this.move(aWord, startRow, startCol, -1, 0)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, -1, 1)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, 0, 1)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, 1, 1)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, 1, 0)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, 1, -1)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, 0, -1)) {
                    break;
                }
                if (this.move(aWord, startRow, startCol, -1, -1)) {
                    break;
                }

                if (++numTries == rows * cols) {
                    System.err.println(aWord);
                }
            }
        }
    }

    /**
     * a method to fill the puzzles null spaces with random chars
     */
    private void fillPuzzle() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (thePuzzle[i][j] == '-') {

                    //randomly generate a capital char (65-90) for fill
                    Character aChar;
                    do {
                        aChar = (char) (random.nextInt(MAXNUM) + MINNUM);
                    } while ((int) aChar < 65 || (int) aChar > 90);

                    thePuzzle[i][j] = aChar;
                }
            }
        }
    }

    /**
     * This method will check to see if a word can fit into a random location
     * @param theWord   the word to place
     * @param startRow  the starting row
     * @param startCol  the starting col
     * @param dx        the change in the x direction
     * @param dy        the change in the y direction
     * @return          boolean value that says whether or not it was placed
     */
    private boolean move(String theWord, int startRow, int startCol, int dx, int dy) {
        boolean result = true;
        //the initial char in question for placement
        int p = 0;
        int r = startRow;
        int c = startCol;

        while (p < theWord.length()) {
            if (this.thePuzzle[startRow][startCol] == '-' || this.thePuzzle[startRow][startCol] == theWord.charAt(p)) {
                //This is the wrapping logic
                startRow = ((startRow + dx + getRows(this)) % getRows(this));
                startCol = ((startCol + dy + getCols(this)) % getCols(this));

                if (r == startRow && c == startCol) {
                    result = false;
                    break;
                }

                p++;
            } else {
                result = false;
                break;
            }
        }

        if (result == true) {
            place(theWord, r, c, dx, dy);
        }
        return result;
    }

    /**
     * This method will place a word the location just tried by move
     * @param theWord   the word to place
     * @param startRow  the starting row
     * @param startCol  the starting col
     * @param dx        the change in the x direction
     * @param dy        the change in the y direction
     */
    private void place(String theWord, int startRow, int startCol, int dx, int dy) {
        //the initial char in question for placement
        int p = 0;

        while (p < theWord.length()) {

            //********COMMENTED OUT DUE TO HANGING ISSUES**************
            thePuzzle[startRow][startCol] = (char) theWord.charAt(p);

            //This is the wrapping logic
            startRow = ((startRow + dx + getRows(this)) % getRows(this));
            startCol = ((startCol + dy + getCols(this)) % getCols(this));
            p++;
        }
    }

    /**
     * a method to print the puzzle out to a .pz file
     * @param   puzzleName      the name of this puzzle (args[0]) from the
     *                          command line.
     */
    private void printPuzzle(String puzzleName) {
        BufferedWriter out1 = null;
        BufferedWriter out2 = null;
        try {
            out1 = new BufferedWriter(new FileWriter(puzzleName + ".pz"));
            out1.write(getRows(this) + "\n");
            out1.write(getCols(this) + "\n");
            out1.close();

            //now add the array to the file created above
            out2 = new BufferedWriter(new FileWriter(puzzleName + ".pz", true));
            for (int i = 0; i < getRows(this); i++) {
                for (int j = 0; j < getCols(this); j++) {
                    out2.write(thePuzzle[i][j]);
                }
                out2.write("\n");
            }
            out2.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * The main method of the program, which is responsible for the vast majority
     * of error checking and variable assignment.
     *
     * @param   args                    the command line arguments
     * @throws java.io.IOException      used for the readers & writers
     */
    public static void main(String[] args) throws IOException, Exception {

        LinkedList<String> myListSpaces = new LinkedList<String>();
        LinkedList<String> myListNOSpaces = new LinkedList<String>();

        String usageMSG = "Usage: java GeneratePuzzle puzzle-name number-rows number-columns [wordfile-name]";
        String puzzleName = "";
        String wordfile = null;
        String tmpString = null;

        Scanner input = null;

        try {

            if (args[0] == null) {
                throw new FileNotFoundException();
            }
            else {
                puzzleName = args[0];
            }

            // Check the # cmd line args and create an appropriate scanner
            if (args.length == 4) {
                input = new Scanner(new BufferedReader(
                        new FileReader(args[3])));
            } else {
                input = new Scanner(System.in);
            }

            //Parse the cmd line args for the row/col sizes to integers
            if (Integer.parseInt(args[1]) <= 0 || Integer.parseInt(args[2]) <= 0) {
                throw new NumberFormatException();
            }

            final int ROWS = Integer.parseInt(args[1]);
            final int COLS = Integer.parseInt(args[2]);

            //If a word-file was not given, put/take user input for the words
            if (args.length == 3) {
                while (input.hasNext()) {
                    tmpString = input.nextLine();
                    myListSpaces.add(tmpString.toUpperCase());

                    //now remove any whitespaces
                    StringTokenizer tokenizer =
                            new StringTokenizer(tmpString,
                            " `1234567890-=~!@#$%^&*()_+[]{}|;':,./<>?");

                    String tmp = "";
                    while (tokenizer.hasMoreElements()) {
                        tmp += tokenizer.nextElement();
                    }

                    if (tmp.length() <= (ROWS * COLS)) {
                        myListNOSpaces.add(tmp.toUpperCase());
                    } else {
                        System.err.println(tmp);
                    }
                }
            } //if a word-file was given, read it in and add it to list
            else if (args.length == 4) {
                wordfile = args[3];

                BufferedReader in = new BufferedReader(new FileReader(wordfile));
                String tmpStr;
                while (in.ready() == true) {
                    tmpString = in.readLine();
                    myListSpaces.add(tmpString.toUpperCase());

                    //now remove any whitespaces
                    StringTokenizer tokenizer =
                            new StringTokenizer(tmpString,
                            " `1234567890-=~!@#$%^&*()_+[]{}|;':,./<>?");

                    String tmp = "";
                    while (tokenizer.hasMoreElements()) {
                        tmp += tokenizer.nextElement();
                    }

                    if (tmp.length() <= (ROWS * COLS)) {
                        myListNOSpaces.add(tmp.toUpperCase());
                    } else {
                        System.err.println(tmp);
                    }
                }
                in.close();
            }

            //Create a file to write the words to based on args[0]
            BufferedWriter out1 = new BufferedWriter(
                    new FileWriter(puzzleName + ".wl"));
            out1.close();

            //now add all the words to the .wl file created above
            for (int i = 0; i < myListSpaces.size(); i++) {
                String string = myListSpaces.get(i);
                BufferedWriter out2 = new BufferedWriter(
                        new FileWriter(puzzleName + ".wl", true));
                out2.write(string + "\n");
                out2.close();
            }

            GeneratePuzzle aPuzzle = new GeneratePuzzle(ROWS, COLS);
            aPuzzle.addWords(myListNOSpaces);           //place words in puzzle
            aPuzzle.fillPuzzle();                       //fill the puzzle
            aPuzzle.printPuzzle(puzzleName);            //print the puzzle

            //create a new spacedPuzzle by calling SpacePuzzle
            SpacePuzzle html = new SpacePuzzle();
            html.putSpace(aPuzzle.getPuzzle(), COLS, COLS, puzzleName, myListSpaces);


        } catch (NumberFormatException e) {
            System.err.println(usageMSG);
        } catch (FileNotFoundException e) {
            System.err.println(usageMSG);
        } catch (Exception e) {
            System.err.println(usageMSG);
        }
    }
}
