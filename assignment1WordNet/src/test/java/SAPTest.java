import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author jacka
 * @version 1.0 on 12/29/2019
 */
class SAPTest {

  @Test
  void testOnlineCase1() {
    final Digraph g = getDigraph("digraph1.txt");
    final SAP sap = new SAP(g);
    assertEquals(0, sap.length(3, 3));
    assertEquals(0, sap.length(5, 5));
    assertEquals(3, sap.ancestor(3, 3));
    assertThrows(IllegalArgumentException.class, () -> sap.length(
        List.of(0, 7, 9, 12, -1), List.of(1, 2, 4, 5, 10)
    ));
    assertThrows(IllegalArgumentException.class, () -> sap.length(
        List.of(0, 7, 9, 12), List.of(1, 2, -1, 4, 5, 10)
    ));
    assertThrows(IllegalArgumentException.class, () -> sap.length(
        Arrays.asList(0, null, 7, 9, 12), List.of(1, 2, 4, 5, 10)
    ));
    assertThrows(IllegalArgumentException.class, () -> sap.length(
        null, List.of(1, 2, 4, 5, 10)
    ));

  }

  @Test
  void testOnlineCase3() {
    final Digraph g = getDigraph("digraph3.txt");
    final SAP sap = new SAP(g);
    assertEquals(10, sap.ancestor(10, 7));
  }

  @Test
  void testOnlineCase4() {
    final Digraph g = getDigraph("digraph4.txt");
    final SAP sap = new SAP(g);
    assertEquals(3, sap.length(4, 1));
  }

  @Test
  void testOnlineCase5() {
    final Digraph g = getDigraph("digraph5.txt");
    final SAP sap = new SAP(g);
    assertEquals(8, sap.length(14, 21));
  }

  @Test
  void testOnlineCase6() {
    final Digraph g = getDigraph("digraph6.txt");
    final SAP sap = new SAP(g);
    assertEquals(0, sap.length(7, 7));
  }

  @Test
  void testOnlineCase25() {
    final Digraph g = getDigraph("digraph25.txt");
    final SAP sap = new SAP(g);
    final Iterable<Integer> a = List.of(13, 23, 24),
        b = List.of(6, 16, 17);
    assertEquals(3, sap.ancestor(a, b));
    assertEquals(4, sap.length(a, b));
  }

  public static Digraph getDigraph(String filePath) {
    In in = new In(filePath);
    return new Digraph(in);
  }
}