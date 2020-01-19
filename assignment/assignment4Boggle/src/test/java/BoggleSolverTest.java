import com.google.common.collect.Lists;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jacka
 * @version 1.0 on 1/19/2020
 */
class BoggleSolverTest {
  @Test
  void testOnlineCase1() {
    final String dictFileName = "dictionary-algs4.txt",
        boardFileName = "board4x4.txt";
    In in = new In(dictFileName);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(boardFileName);
    final List<String> actualWords = Lists.newArrayList(solver.getAllValidWords(board));
    Collections.sort(actualWords);
    final int actualScore = actualWords.stream().mapToInt(solver::scoreOf).sum(),
        expectedScore = 33;
    assertEquals(expectedScore, actualScore);
  }

  @Test
  void testOnlineCaseAlgs4BoardQ() {
    final String dictFileName = "dictionary-algs4.txt",
        boardFileName = "board-q.txt";
    In in = new In(dictFileName);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(boardFileName);
    final List<String> actualWords = Lists.newArrayList(solver.getAllValidWords(board));
    Collections.sort(actualWords);
    assertTrue(actualWords.contains("REQUIRE"));
  }

  @Test
  void testOnlineCaseDictionary_zingarelli2005() {
    final String dictFileName = "dictionary-zingarelli2005.txt",
        boardFileName = "board-q.txt";
    In in = new In(dictFileName);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(boardFileName);
    final List<String> actualWords = Lists.newArrayList(solver.getAllValidWords(board));
    Collections.sort(actualWords);
  }

  @Test
  void testOnlineCaseDictionary_yawl() {
    final String dictFileName = "dictionary-yawl.txt",
        boardFileName = "board-qwerty.txt";
    In in = new In(dictFileName);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard(boardFileName);
    final List<String> actualWords = Lists.newArrayList(solver.getAllValidWords(board));
    Collections.sort(actualWords);
    assertFalse(actualWords.contains("TRANQS"));
    assertEquals(22, actualWords.size());
  }

  @Test
  void testOnlineCaseScore_OPAQUE() {
    final String dictFileName = "dictionary-algs4.txt";
    In in = new In(dictFileName);
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    final int actualScore = solver.scoreOf("QUITE");
    assertEquals(2, actualScore);
  }
}