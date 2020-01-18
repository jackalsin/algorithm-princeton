import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jacka
 * @version 1.0 on 1/16/2020
 */
class BaseballEliminationTest {

  @Test
  void testOnlineCase4() {
    final String fileName = "teams4.txt";
    final BaseballElimination solution = new BaseballElimination(fileName);
    assertTrue(solution.isEliminated("Philadelphia"));
    assertFalse(solution.isEliminated("Atlanta"));
    assertFalse(solution.isEliminated("New_York"));
    assertTrue(solution.isEliminated("Montreal"));
    assertNotNull(solution.certificateOfElimination("Philadelphia"));
    assertNull(solution.certificateOfElimination("Atlanta"));
    assertNull(solution.certificateOfElimination("New_York"));
    assertEquals(List.of("Atlanta"), solution.certificateOfElimination("Montreal"));
  }

  @Test
  void testOnlineCase2() {
    final String fileName = "teams4a.txt";
    final BaseballElimination solution = new BaseballElimination(fileName);
    assertTrue(solution.isEliminated("Ghaddafi"));
  }

  @Test
  void testOnlineCase5() {
    final String fileName = "teams5.txt";
    final BaseballElimination solution = new BaseballElimination(fileName);
    assertFalse(solution.isEliminated("New_York"));
    final List<String> actualDetroit = new ArrayList<>();
    solution.certificateOfElimination("Detroit").forEach(actualDetroit::add);

    assertEquals(Set.of("New_York", "Baltimore", "Boston", "Toronto"),
        new HashSet<>(actualDetroit));
    assertTrue(solution.isEliminated("Detroit"));
    assertNull(solution.certificateOfElimination("New_York"));
  }

  @Test
  void testOnlineCase5c() {
    final String fileName = "teams5c.txt";
    final BaseballElimination solution = new BaseballElimination(fileName);
    assertTrue(solution.isEliminated("Philadelphia"));
  }

  @Test
  void testOnlineCase10() {
    final String fileName = "teams10.txt";
    final BaseballElimination solution = new BaseballElimination(fileName);
  }
}