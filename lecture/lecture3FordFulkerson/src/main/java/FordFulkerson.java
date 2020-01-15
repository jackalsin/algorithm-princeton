import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A solution to apply FordFulkerson solution to calculate the max flow for a graph G.
 *
 * @author jacka
 * @version 1.0 on 1/15/2020
 */
public final class FordFulkerson {
  private boolean[] marked;   // true if s->v path in residual network
  private FlowEdge[] edgeTo;  // last edge on s->v path
  private double value = 0;   // value of flow

  public FordFulkerson(FlowNetwork G, int s, int t) {
    while (hasAugmentingPath(G, s, t)) {
      double toAdd = Double.POSITIVE_INFINITY;
      for (int v = t; v != s; v = edgeTo[v].other(v)) {
        toAdd = Math.min(toAdd, edgeTo[v].residualCapacityTo(v));
      }
      for (int v = t; v != s; v = edgeTo[v].other(v)) {
        edgeTo[v].addResidualFlowTo(v, toAdd);
      }
      value += toAdd;
    }
  }

  private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
    edgeTo = new FlowEdge[G.V()];
    marked = new boolean[G.V()];
    final Queue<Integer> q = new ArrayDeque<>();
    q.add(s);
    marked[s] = true;
    while (!q.isEmpty()) {
      final int toRemove = q.remove();
      for (final FlowEdge e : G.adj(toRemove)) {
        final int next = e.other(toRemove);
        if (!marked[next] && e.residualCapacityTo(next) > 0) {
          marked[next] = true;
          edgeTo[next] = e;
          q.add(next);
        }
      }
    }
    return marked[t];
  }

  public double value() {
    return value;
  }

  /**
   * is v reachable from s in residual network?
   */
  public boolean inCut(int v) {
    return marked[v];
  }
}
