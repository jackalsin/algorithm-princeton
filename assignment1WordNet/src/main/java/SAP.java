import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * @author jacka
 * @version 1.0 on 12/28/2019
 */
public class SAP {
  private final Digraph g;

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    g = new Digraph(G);
  }

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {
    final List<Integer> vList = new ArrayList<>() {{
      add(v);
    }}, wList = new ArrayList<>() {{
      add(w);
    }};
    return length(vList, wList);
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {
    final List<Integer> vList = new ArrayList<>() {{
      add(v);
    }}, wList = new ArrayList<>() {{
      add(w);
    }};
    return ancestor(vList, wList);
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    return getAncestorAndLength(v, w)[1];
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    return getAncestorAndLength(v, w)[0];
  }

  private boolean isInRange(Iterable<Integer> v) {
    for (Integer vv : v) {
      if (vv == null || vv < 0 || vv >= g.V()) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return [ancestor, length]
   */
  private int[] getAncestorAndLength(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null) {
      throw new IllegalArgumentException("v = " + v + " is null.");
    }
    if (w == null) {
      throw new IllegalArgumentException("w = " + w + " is null.");
    }
    if (!isInRange(v)) {
      throw new IllegalArgumentException("v = " + v + " is not in the range [0, " + g.V() + ")");
    }
    if (!isInRange(w)) {
      throw new IllegalArgumentException("w = " + w + " is not in the range [0, " + g.V() + ")");
    }

    Queue<Integer> q1 = new ArrayDeque<>(), q2 = new ArrayDeque<>();
    int[] distances1 = new int[g.V()], distances2 = new int[g.V()];
    Arrays.fill(distances1, -1);
    Arrays.fill(distances2, -1);
    int counter = 0;
    for (int vv : v) {
      q1.add(vv);
      distances1[vv] = 0;
    }
    for (int ww : w) {
      q2.add(ww);
      distances2[ww] = 0;
      if (distances1[ww] == 0) {
        return new int[]{ww, 0};
      }
    }
    boolean hasFoundAncestor = false;
    int minLen = Integer.MAX_VALUE;
    int bestAncestor = -1;
    while (!q1.isEmpty() || !q2.isEmpty() || (counter % 2 != 0 && hasFoundAncestor)) {
      final int steps = counter / 2;
      final int size = q1.size();
      for (int i = 0; i < size; ++i) {
        final int toRemove = q1.remove();
        final Iterable<Integer> children = g.adj(toRemove);
        for (int c : children) {
          if (distances1[c] != -1) continue;
          if (distances2[c] != -1) {
            hasFoundAncestor = true;
            final int curLen = steps + 1 + distances2[c];
            if (curLen < minLen) {
              bestAncestor = c;
              minLen = curLen;
            }
          }
          q1.add(c);
          distances1[c] = steps + 1;
        }
      }
      // swap 1 and 2
      counter++;
      final int[] tmp = distances1;
      distances1 = distances2;
      distances2 = tmp;

      final Queue<Integer> tmpQ = q1;
      q1 = q2;
      q2 = tmpQ;
    }
    // not found
    return hasFoundAncestor ? new int[]{bestAncestor, minLen} : new int[]{-1, -1};
  }
}
