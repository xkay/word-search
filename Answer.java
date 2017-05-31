/**
 * Write your solution in this file.
 *
 * You can build, execute, and test your answer by either pressing 'Try Answer' in the side panel,
 * or by running the following commands on the command line:
 *
 * To build your answer:
 *            gradle build
 * To run the previously built executable:
 *            gradle run -q -P "appArgs='<test_case_path>'"
 * For example:
 *            gradle run -q -P "appArgs='tests/input_1.json'"
 *
 * You can organize your code as you wish (eg, use auxiliary files) as long as `TestAnswer`
 * produces the expected output.
 */
import java.util.*;
import java.io.*;


public class Answer {

  private static String[] boggle_board;
  private static String list_path;
  private static String[][] twoDimensBoard; // boggle board 2d
  private static List<String> wordsList; // word bank

    /**
     *
     * @param boggle_board Representation of a boggle board.
     * @param list_path Path to a .txt file containing the list of valid words to consider
     * @return List of valid words found in the given boggle board.
     */
    public static String[] find_words(String[] boggle_board, String list_path) {
      Answer.boggle_board = boggle_board;
      Answer.list_path = list_path;

      List<String> result = new ArrayList<>();
      wordsList = new ArrayList<>();

      boardTo2D();
      populateWordsList();

      for (String word : Answer.wordsList) {
        if (wordFound(word)) {
          result.add(word);
        }
      }

      return result.toArray(new String[0]);
    }

    /**
     *
     * @param word A word to be found in twoDimensBoard.
     * @return Boolean represents if word found.
     */
    public static boolean wordFound(String word) {
      // DFS
      // String[] splitWord = word.split("");

      boolean isFound = traverse2DArray(Answer.twoDimensBoard,word);
      return isFound;
    }

    public static boolean traverse2DArray(String[][] board, String word) {

      for (int i = 0; i < Answer.twoDimensBoard.length; i++) {
        for (int j = 0; j < (Answer.twoDimensBoard[0]).length; j++) {
          if (Answer.twoDimensBoard[i][j].charAt(0) == word.charAt(0)) {
            boolean isFound = search(board, word,i,j,0);
            if (isFound)
              return true;
          }
        }
      }

      return false;
    }

    public static boolean search(String[][]board, String word, int i, int j, int index){
        if (index == word.length()) {
            return true;
        }

        if (i >= board.length || i < 0 || j >= board[i].length || j < 0 || board[i][j].charAt(0) != word.charAt(index)) {
            return false;
        }

        if (search(board, word, i-1, j, index+1) ||
           search(board, word, i+1, j, index+1) ||
           search(board, word, i, j-1, index+1) ||
           search(board, word, i, j+1, index+1) ||
           search(board, word, i, j, index+1)) {
            return true;
        }

        return false;
    }

    // returns 2D version of grid
    public static void boardTo2D() {
      twoDimensBoard = new String[Answer.boggle_board.length][Answer.boggle_board[0].length()];
      for (int j = 0; j < Answer.boggle_board.length; j++) {
        String wordString = Answer.boggle_board[j];
        String[] splitedWord = wordString.split("");
        for (int i = 0; i < splitedWord.length; i++) {
          Answer.twoDimensBoard[i][j] = splitedWord[i];
        }
      }
    }

    public static void populateWordsList() {

      File file = new File(Answer.list_path);

      try {
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
          String word = sc.nextLine();
          System.out.println(word);
          Answer.wordsList.add(word);
        }
        sc.close();

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }


    }

}
