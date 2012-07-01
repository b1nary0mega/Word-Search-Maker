
import java.awt.Point;
import java.io.*;
import java.util.StringTokenizer;

/*
 * WordSearch.java
 *
 */

/**
 * This program compliments the GeneratePuzzle program and will search for the
 * answers to the puzzle based on 3 separate search algorithms.
 *
 * @author jra3345 - James R. Aylesworth
 */
public class WordSearch {

    static int Rows = 0;
    static int Cols = 0;
    static Character[][] thePuzzle = null;
    static LinkedList<String> wordListNoSpaces = new LinkedList<String>();
    static LinkedList<String> wordListSpaces = new LinkedList<String>();
    static LinkedList<String> SLOWsearchResults = new LinkedList<String>();
    static LinkedList<String> SHORTsearchResults = new LinkedList<String>();
    static LinkedList<String> FASTsearchResults = new LinkedList<String>();
    static int runtime = 0;
    static int comparisons = 0;
    static BufferedWriter out = null;

    /**
     * This method will pick away at the word list and call a search in specific
     * directions trying to find the word.
     *
     */
    public static void preSearchSlow() {
        String finalResult = "";
        comparisons = 0;

        Node word = wordListNoSpaces.getNthNode(0);
        while (word != null) {
            //get the word from the list & then remove it from the list
            String aWord = ((String) word.getData()).toUpperCase();
            wordSearchSlow(finalResult, aWord);
            word = word.getNext();
        }
    }

    /**
     * This method will call the search with the specified dy/dx for the requested
     * search direction.
     *
     * @param finalResult   a linked list of the output and their locations
     * @param aWord         the word that is being searched for
     */
    public static void wordSearchSlow(String finalResult, String aWord) {
        boolean found = false;

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {

                for (int k = -1; k < 2; k++) {
                    for (int b = -1; b < 2; b++) {
                        //skip the scenario for 0,0 changes (no movement)
                        if (k == 0 && b == 0) {
                            continue;
                        }

                        finalResult = getWord(aWord.length(), i, j, k, b);

                        comparisons = comparisons + aWord.length();

                        if (finalResult.contains(aWord)) {
                            SLOWsearchResults.add(finalResult);
                            //return;
                            found = true;
                        }
                    }
                }
            }
        }
        if (found == false) {
            SLOWsearchResults.add(aWord + " [MISSING]");
        }

    }

    /**
     * This method will pick away at the word list and call a search in specific
     * directions trying to find the word.
     *
     */
    public static void preSearchShort() {
        String finalResult = "";
        comparisons = 0;

        Node word = wordListNoSpaces.getNthNode(0);
        while (word != null) {
            //get the word from the list & then remove it from the list
            String aWord = ((String) word.getData()).toUpperCase();
            wordSearchShort(finalResult, aWord);
            word = word.getNext();
        }
    }

    /**
     * This method will call the search with the specified dy/dx for the requested
     * search direction using the short method
     *
     * @param finalResult   a linked list of the output and their locations
     * @param aWord         the word that is being searched for
     */
    public static void wordSearchShort(String finalResult, String aWord) {
        boolean found = true;

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {

                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        //reset the found variable for the next word
                        found = true;

                        //skip the scenario for 0,0 changes (no movement)
                        if (k == 0 && l == 0) {
                            continue;
                        }

                        //get the word for a comparison of the characters
                        finalResult = getWord(aWord.length(), i, j, k, l);

                        //begin comparing the chars for ==, if not then break!
                        for (int m = 0; m < aWord.length(); m++) {
                            if (finalResult.charAt(m) == aWord.charAt(m)) {
                                comparisons++;
                                continue;
                            } else {
                                comparisons++;
                                found = false;
                                break;
                            }

                        }

                        //if you found the word then continue to put it into
                        //the array of found items, AKA result
                        if (found == true) {
                            SHORTsearchResults.add(finalResult);
                            return;
                        } else {
                            continue;
                        }
                    }
                }

            }
        }
        if (found == false) {
            SHORTsearchResults.add(aWord + " [MISSING]");
        }

    }

    /**
     * This method will pick away at the word list and call a search in specific
     * directions trying to find the word.
     *
     */
    public static void preSearchFast() {
        String finalResult = "";
        comparisons = 0;

        Node word = wordListNoSpaces.getNthNode(0);
        while (word != null) {
            //get the word from the list & then remove it from the list
            String aWord = ((String) word.getData()).toUpperCase();
            wordSearchFast(finalResult, aWord);
            word = word.getNext();
        }
    }

    /**
     * This method will call the search with the specified dy/dx for the requested
     * search direction using the fast method
     *
     * @param finalResult   a linked list of the output and their locations
     * @param aWord         the word that is being searched for
     */
    public static void wordSearchFast(String finalResult, String aWord) {
        boolean found = true;

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {

                if (thePuzzle[i][j] == aWord.charAt(0)) {
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            //reset the found variable for the next word
                            found = true;

                            //skip the scenario for 0,0 changes (no movement)
                            if (k == 0 && l == 0) {
                                continue;
                            }

                            //get the word for a comparison of the characters
                            finalResult = getWord(aWord.length(), i, j, k, l);

                            //begin comparing the chars for ==, if not then break!
                            for (int m = 0; m < aWord.length(); m++) {
                                if (finalResult.charAt(m) == aWord.charAt(m)) {
                                    comparisons++;
                                    continue;
                                } else {
                                    found = false;
                                    break;
                                }

                            }

                            //if you found the word then continue to put it into
                            //the array of found items, AKA result
                            if (found == true) {
                                FASTsearchResults.add(finalResult);
                                return;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        if (found == false) {
            FASTsearchResults.add(aWord + " [MISSING]");
        }

    }

    /**
     * This method will return the characters the same length of the word in
     * question of a match. All movement is based on dx and dy which are the
     * changes in the x and y values.
     *
     * @param wordLength    the length of the word we are tyring to match
     * @param startRow      the row we are starting at
     * @param startCol      the col we are starting at
     * @param dx            the change in the x-value
     * @param dy            the change in the y-value
     * @return              the word that we saved
     */
    private static String getWord(int counter, int startRow, int startCol, int dx, int dy) {

        String theWord = "";
        Point start = new Point();
        Point end = new Point();

        //set the start point for this word
        start.setLocation(startRow, startCol);

        while (counter > 0) {

            //get the word = to the char at that position
            theWord += thePuzzle[startRow][startCol];

            counter--;

            //This is the wrapping logic
            if (counter > 0) {
                startRow = ((startRow + dx + Rows) % Rows);
                startCol = ((startCol + dy + Cols) % Cols);
            }
        }
        //store the end of the word
        end.setLocation(startRow, startCol);

        String result = theWord + " [(" + (int) start.getX() + "," + (int) start.getY() + ")" +
                "(" + (int) end.getX() + "," + (int) end.getY() + ")]";

        return result;
    }

    /**
     * This method will take a linkedList and replace the word without spaces
     * with the correctly spaced word.
     * 
     * @param theList   the list to be modified
     * @return          the new correctly spaced list
     */
    private static LinkedList<String> replaceSpace(LinkedList<String> theList) {
        StringTokenizer st = null;
        String badWord = "";
        String location = "";
        String goodWord = "";

        LinkedList<String> CorrectList = new LinkedList<String>();

        for (int i = 0; i < theList.size(); i++) {
            st = new StringTokenizer(theList.get(i), "[");

            badWord = st.nextToken();
            location = "[" + st.nextToken();

            CorrectList.add(wordListSpaces.get(i) + location);
        }

        return CorrectList;
    }

    /**
     * This is the main method. It is responsible for parsing all of the user
     * input passed into the program from the command line and checking the args.
     * 
     * @param args  the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String usageMsg = "Usage: java WordSearch slow|short|fast|all filename";
        String searchType = null;
        String pzName = null;
        String wlName = null;
        String outputName = null;
        boolean goodFile = true;

        BufferedReader pzInput = null;
        BufferedReader wlInput = null;

        while (goodFile == true) {
            try {
                if (args.length != 2) {
                    System.err.println(usageMsg);
                    return;
                }
                searchType = args[0];
                pzName = args[1].concat(".pz");
                wlName = args[1].concat(".wl");

                pzInput = new BufferedReader(new FileReader(pzName));

                while (pzInput.ready()) {

                    Rows = Integer.parseInt(pzInput.readLine());
                    Cols = Integer.parseInt(pzInput.readLine());

                    thePuzzle = new Character[Rows][Cols];

                    for (int i = 0; i < Rows; i++) {
                        String tmpLine = pzInput.readLine();
                        for (int j = 0; j < Cols; j++) {
                            thePuzzle[i][j] = tmpLine.charAt(j);
                        }
                    }
                }
                pzInput.close();


            } catch (IOException e) {
                System.err.println("WordSearch: cannot open " + pzName);
                goodFile = false;
                return;
            } catch (NumberFormatException e) {
                System.err.println("WordSearch: invalid puzzle file");
                goodFile = false;
                return;
            }

            try {
                wlInput = new BufferedReader(new FileReader(wlName));

                while (wlInput.ready() == true) {
                    String tmpString = wlInput.readLine();

                    //add this word to the list without spaces
                    wordListSpaces.add(tmpString);

                    //now remove any whitespaces
                    StringTokenizer tokenizer =
                            new StringTokenizer(tmpString, " ");

                    String tmp = "";
                    while (tokenizer.hasMoreElements()) {
                        tmp += tokenizer.nextElement();
                    }

                    wordListNoSpaces.add(tmp);
                }
                wlInput.close();

                //create a new bufferedWriter to send the data out to .ans file
                //remove the .pz from teh file
                StringTokenizer st = new StringTokenizer(pzName, ".");
                String mainName = st.nextToken();
                out = new BufferedWriter(new FileWriter(mainName + ".ans"));
                outputName = mainName + ".ans";

            } catch (IOException e) {
                System.err.println("WordSearch: cannot open " + wlName);
                goodFile = false;
                return;
            }
            break;
        }

        /*
        start the searching with presearch which will call other search methods
        This is the way that all searching will be done.
         */

        while (goodFile == true) {
            try {
                //print out the puzzle
                for (int i = 0; i < Rows; i++) {
                    for (int j = 0; j < Cols; j++) {
                        out.write(thePuzzle[i][j]);
                    }
                    out.write("\n");
                }

                out.write("\n");

                //now print the word list
                for (int i = 0; i < wordListSpaces.size(); i++) {
                    out.write(wordListSpaces.get(i));
                    out.write("\n");
                }

                out.write("\n");

                //now print out the search findings
                //-----------------------CODE FOR SLOW SEARCH OUTPUT----------------
                if (searchType.equalsIgnoreCase("slow") ||
                        searchType.equalsIgnoreCase("all")) {

                    runtime = (int) System.currentTimeMillis();
                    preSearchSlow();
                    runtime = ((((int) System.currentTimeMillis()) - runtime));
                    //replace the spaces by calling replaceSpace
                    SLOWsearchResults = replaceSpace(SLOWsearchResults);
                    out.write("Slow search: \n");
                    //print out the results of the search
                    int myCounter = 0;
                    //System.out.println(searchResults.size());
                    while (myCounter < SLOWsearchResults.size()) {
                        out.write(SLOWsearchResults.get(myCounter) + "\n");
                        myCounter++;
                    }
                    out.write("Algorithm time: " + runtime + "\n");
                    out.write("Character comparisons: " + comparisons + "\n");
                }
                //------------------ END CODE FOR SLOW SEARCH OUTPUT----------------
                if (searchType.equalsIgnoreCase("all")) {
                    out.write("\n");
                }
                //-----------------------CODE FOR SHORT SEARCH OUTPUT---------------
                if (searchType.equalsIgnoreCase("short") ||
                        searchType.equalsIgnoreCase("all")) {

                    runtime = (int) System.currentTimeMillis();
                    preSearchShort();
                    runtime = ((((int) System.currentTimeMillis()) - runtime));
                    //replace the spaces by calling replaceSpace
                    SHORTsearchResults = replaceSpace(SHORTsearchResults);
                    out.write("Short search: \n");
                    //print out the results of the search
                    int myCounter = 0;
                    //System.out.println(searchResults.size());
                    while (myCounter < SHORTsearchResults.size()) {
                        out.write(SHORTsearchResults.get(myCounter) + "\n");
                        myCounter++;
                    }
                    out.write("Algorithm time: " + runtime + "\n");
                    out.write("Character comparisons: " + comparisons + "\n");
                }
                //------------------ END CODE FOR SHORT SEARCH OUTPUT---------------
                if (searchType.equalsIgnoreCase("all")) {
                    out.write("\n");
                }
                //-----------------------CODE FOR SHORT SEARCH OUTPUT---------------
                if (searchType.equalsIgnoreCase("fast") ||
                        searchType.equalsIgnoreCase("all")) {

                    runtime = (int) System.currentTimeMillis();
                    preSearchFast();
                    runtime = ((((int) System.currentTimeMillis()) - runtime));
                    //replace the spaces by calling replaceSpace
                    FASTsearchResults = replaceSpace(FASTsearchResults);
                    out.write("Fast search: \n");
                    //print out the results of the search
                    int myCounter = 0;
                    //System.out.println(searchResults.size());
                    while (myCounter < FASTsearchResults.size()) {
                        out.write(FASTsearchResults.get(myCounter) + "\n");
                        myCounter++;
                    }
                    out.write("Algorithm time: " + runtime + "\n");
                    out.write("Character comparisons: " + comparisons + "\n");
                }
                //------------------ END CODE FOR SHORT SEARCH OUTPUT---------------

                out.close();

                //Print this out to Standard Output!
                BufferedReader fr = new BufferedReader(new FileReader(outputName));
                while (fr.ready()) {
                    System.out.println(fr.readLine());
                }

                SpacePuzzle html = new SpacePuzzle();

            } catch (IOException e) {
                e.getMessage();
                goodFile = false;
                return;
            }
        }


    }
}
