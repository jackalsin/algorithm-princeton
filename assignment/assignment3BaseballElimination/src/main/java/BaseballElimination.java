import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jacka
 * @version 1.0 on 1/16/2020
 */
public class BaseballElimination {
  private final List<String> teamNames = new ArrayList<>();
  private final Map<String, Integer> teamToId = new HashMap<>();
  private final List<Integer> wins = new ArrayList<>();
  private final List<Integer> remain = new ArrayList<>();
  private final int totalRemain;
  private final List<Integer> losses = new ArrayList<>();
  private final List<List<Integer>> matches = new ArrayList<>();
  private final List<Integer> inNetworkRemains = new ArrayList<>();

  // create a baseball division from given filename in format specified below
  public BaseballElimination(String filename) {
    // The input format is the number of teams in the division n followed by one line for each team.
    // Each line
    // the team name, the number of wins, the number of losses, the number of remaining games, and the number of
    // remaining games against each team in the division.

    final In reader = new In(filename);
    final int num = Integer.parseInt(reader.readLine());
    int totalRemain = 0;
    for (int i = 0; i < num; ++i) {
      assert reader.hasNextLine();
      final String[] items = reader.readLine().trim().split("\\s+");
      teamToId.put(items[0], teamNames.size());
      teamNames.add(items[0]);
      final int wins = Integer.parseInt(items[1]),
          loss = Integer.parseInt(items[2]),
          remain = Integer.parseInt(items[3]);
      this.wins.add(wins);
      this.losses.add(loss);
      this.remain.add(remain);
      final List<Integer> matches = new ArrayList<>();
      int inNetworkRemains = 0;
      this.matches.add(matches);
      for (int v = 0; v < num; v++) {
        final int match = Integer.parseInt(items[v + 4]);
        matches.add(match);
        inNetworkRemains += match;
      }
      this.inNetworkRemains.add(inNetworkRemains);
      totalRemain += inNetworkRemains;
    }
    this.totalRemain = totalRemain;
  }

  // number of teams
  public int numberOfTeams() {
    return teamNames.size();
  }

  // all teams
  public Iterable<String> teams() {
    return new ArrayList<>(teamNames);
  }

  // number of wins for given team
  public int wins(String team) {
    if (!teamToId.containsKey(team)) throw new IllegalArgumentException();
    return wins.get(teamToId.get(team));
  }

  // number of losses for given team
  public int losses(String team) {
    if (!teamToId.containsKey(team)) throw new IllegalArgumentException();
    return losses.get(teamToId.get(team));
  }

  // number of remaining games for given team
  public int remaining(String team) {
    if (!teamToId.containsKey(team)) throw new IllegalArgumentException();
    return remain.get(teamToId.get(team));
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    if (!teamToId.containsKey(team1) || !teamToId.containsKey(team2)) throw new IllegalArgumentException();
    final int id1 = teamToId.get(team1),
        id2 = teamToId.get(team2);
    return matches.get(id1).get(id2);
  }

  // is given team eliminated?
  public boolean isEliminated(String team) {
    if (!teamToId.containsKey(team)) throw new IllegalArgumentException();
    final int teamId = teamToId.get(team);
    if (!triviallyEliminatedBy(team).isEmpty()) {
      return true;
    }
    final FlowNetwork graph = getFlowNetwork(team);
    final FordFulkerson solution = getSolution(graph);
    return solution.value() < ((totalRemain - (inNetworkRemains.get(teamId) * 2)) >> 1);
  }

  private int getMatchId(final int i, final int j) {
    if (i > j) {
      return getMatchId(j, i);
    }
    return (i + 1) * (teamNames.size() + 2) + j;
  }

  private FlowNetwork getFlowNetwork(final String team) {
    final FlowNetwork graph = new FlowNetwork((teamNames.size() + 2) * (teamNames.size() + 1));
    final int s = teamNames.size() + 1, t = teamNames.size() + 2, teamId = teamToId.get(team);
    for (int i = 0; i < teamNames.size(); ++i) {
      if (i == teamId) continue;
      for (int j = i + 1; j < teamNames.size(); ++j) {
        if (j == teamId) continue;
        final int matchId = getMatchId(i, j);
        graph.addEdge(new FlowEdge(s, matchId, matches.get(i).get(j)));
        graph.addEdge(new FlowEdge(matchId, i, Double.POSITIVE_INFINITY));
        graph.addEdge(new FlowEdge(matchId, j, Double.POSITIVE_INFINITY));
      }
      graph.addEdge(new FlowEdge(i, t, wins.get(teamId) + remain.get(teamId) - wins.get(i)));
    }
    return graph;
  }

  private FordFulkerson getSolution(final FlowNetwork flowNetwork) {
    final int s = teamNames.size() + 1, t = teamNames.size() + 2;
    return new FordFulkerson(flowNetwork, s, t);
  }

  // subset R of teams that eliminates given team;
  // null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    if (!teamToId.containsKey(team)) throw new IllegalArgumentException();
    final List<String> result = triviallyEliminatedBy(team);
    if (!result.isEmpty()) {
      return result;
    }
    final FlowNetwork graph = getFlowNetwork(team);
    final FordFulkerson solution = getSolution(graph);
    final int teamId = teamToId.get(team);
    for (int i = 0; i < teamNames.size(); i++) {
      if (teamId == i || !solution.inCut(i)) continue;
      result.add(teamNames.get(i));
    }
    return result.isEmpty() ? null : result;
  }

  private List<String> triviallyEliminatedBy(final String team) {
    final int teamId = teamToId.get(team);
    final List<String> result = new ArrayList<>();
    for (int i = 0; i < teamNames.size(); i++) {
      if (wins.get(teamId) + remain.get(teamId) - wins.get(i) < 0)
        result.add(teamNames.get(i));
    }
    return result;
  }
}
