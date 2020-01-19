import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jacka
 * @version 1.0 on 1/18/2020
 */
public final class BoggleSolver {
  private static final char BASE = 'A', Q = 'Q', U = 'U';
  private static final int MIN_LEN = 3;
  private final Node root = new Node();

  // Initializes the data structure using the given array of strings as the dictionary.
  // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
  public BoggleSolver(String[] dictionary) {
    for (final String word : dictionary) {
      if (word.length() < MIN_LEN) {
        continue;
      }
      insert(root, word, 0);
    }
  }

  private static void insert(final Node root, final String word, final int i) {
    if (i > word.length()) {
      return;
    }
    if (i == word.length()) {
      root.word = word;
      return;
    }
    final char chr = word.charAt(i);
    final int chrIndex = chr - BASE;
    if (root.next[chrIndex] == null) {
      root.next[chrIndex] = new Node();
    }
    if (chr == Q) {
      if (i + 1 < word.length() && word.charAt(i + 1) == U) {
        insert(root.next[chrIndex], word, i + 2);
      }
    } else {
      insert(root.next[chrIndex], word, i + 1);
    }
  }

  // Returns the set of all valid words in the given Boggle board, as an Iterable.
  public Iterable<String> getAllValidWords(BoggleBoard board) {
    final int rows = board.rows(), cols = board.cols();
    final Set<String> result = new HashSet<>();
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; ++col) {
        search(result, board, new boolean[rows][cols], row, col, root);
      }
    }
    return new ArrayList<>(result);
  }

  private void search(final Set<String> result, final BoggleBoard board, final boolean[][] visited,
                      final int row, final int col, final Node root) {
    if (root == null) {
      return;
    }
    if (root.word != null) {
      result.add(root.word);
    }
    if (row < 0 || row >= board.rows() || col < 0 || col >= board.cols() || visited[row][col]) {
      return;
    }
    visited[row][col] = true;
    final char chr = board.getLetter(row, col);
    final int chrIndex = chr - BASE;
    search(result, board, visited, row - 1, col - 1, root.next[chrIndex]);
    search(result, board, visited, row - 1, col, root.next[chrIndex]);
    search(result, board, visited, row - 1, col + 1, root.next[chrIndex]);
    search(result, board, visited, row, col - 1, root.next[chrIndex]);
    search(result, board, visited, row, col + 1, root.next[chrIndex]);
    search(result, board, visited, row + 1, col - 1, root.next[chrIndex]);
    search(result, board, visited, row + 1, col, root.next[chrIndex]);
    search(result, board, visited, row + 1, col + 1, root.next[chrIndex]);
    visited[row][col] = false;
  }

  // Returns the score of the given word if it is in the dictionary, zero otherwise.
  // (You can assume the word contains only the uppercase letters A through Z.)
  public int scoreOf(String word) {
    final boolean isFound = search(word, root, 0);
    if (isFound) {
      return score(word);
    }
    return 0;
  }

  private static boolean search(final String word, final Node root, final int i) {
    if (root == null) {
      return false;
    }
    if (i == word.length()) {
      return root.word != null;
    }
    final char chr = word.charAt(i);
    final int chrIndex = chr - BASE;
    if (root.next[chrIndex] == null) {
      return false;
    }
    if (chr == Q) {
      if (i + 1 < word.length() && word.charAt(i + 1) == U) {
        return search(word, root.next[chrIndex], i + 2);
      }
      return false;
    } else {
      return search(word, root.next[chrIndex], i + 1);
    }
  }


  /**
   * word length  points
   * 3–4		      1
   * 5		        2
   * 6		        3
   * 7		        5
   * 8+		        11
   */
  private static int score(final String word) {
    final int len = word.length();
    if (len < 3) {
      return 0;
    } else if (len < 5) {
      return 1;
    } else if (len < 6) {
      return 2;
    } else if (len < 7) {
      return 3;
    } else if (len < 8) {
      return 5;
    } else {
      return 11;
    }
  }

  private static final class Node {
    private final Node[] next = new Node[26];
    private String word;
  }
}
