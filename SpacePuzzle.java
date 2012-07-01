/*
 * SpacePuzzle.java
 *
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class will provide more space in the rows of a puzzle created from the
 * GeneratePuzzle Class.
 *
 * @author James R. Aylesworth
 */
public class SpacePuzzle {

    /**
     * the default constructor -- not need as java provides one, but put for
     * good programming practice.
     */
    public SpacePuzzle() {
    }
    ;

    /**
     * This method will place spaces after each character in a puzzle array to
     * make it easier for reading purposes (user friendly).
     *
     * @param aPuzzle       the puzzle to put spaces into
     * @param rows          then number of rows in the puzzle
     * @param cols          the number of columns in a puzzle
     * @param puzzleName    the puzzles name
     * @throws java.io.IOException
     */
    public void putSpace(Character[][] aPuzzle, int rows, int cols, String puzzleName,
            LinkedList<String> theWords) throws IOException {
        String[][] endPuzzle = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                endPuzzle[i][j] = (aPuzzle[i][j] + " ");
            }
        }

        puzzleName += "-web.html";

        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(puzzleName));
            br.write("<HTML>\n<HEAD><TITLE>JRA3345 WordSearch of the Day!</TITLE></HEAD>\n");
            br.write("<BODY>\n");
            br.write("<P align=\"center\"><TT><H1>");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    br.write(aPuzzle[i][j] + " ");
                }
                br.write("<BR>\n");
            }
            br.write("</H1></TT>\n</P>\n<P align=\"left\"><U>Find the following words:</U><BR>");
            br.write("<UL>");
            int wordCounter = theWords.size() - 1;
            while (wordCounter > 0) {
                br.write("<LI>" + theWords.get(wordCounter) + "</LI>");
                wordCounter --;
            }
            br.write("</UL>");
            br.write("</P>");
            br.write("</BODY>\n</HTML>");
            br.close();
        } catch (IOException e) {
            System.err.println("IOException caught in spaced puzzle creation!");
            System.err.println("Stack Trace:");
            e.printStackTrace();
        }
    }
}
