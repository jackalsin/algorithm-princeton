import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jacka
 * @version 1.0 on 1/18/2020
 */
class BoggleBoardTest {

  @Test
  void testOnlineCase1() {
    final String fileName = "board4x4.txt";
    final BoggleBoard board = new BoggleBoard(fileName);
    final char[][] expected = {
        {'A', 'T', 'E', 'E'},
        {'A', 'P', 'Y', 'O'},
        {'T', 'I', 'N', 'U'},
        {'E', 'D', 'S', 'E'}
    }, actual = new char[4][4];
    for (int i = 0; i < 4; ++i) {
      for (int j = 0; j < 4; ++j) {
        actual[i][j] = board.getLetter(i, j);
      }
    }
    assertTrue(Arrays.deepEquals(expected, actual));
  }

}