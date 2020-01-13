import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Corner cases.
 * Throw an IllegalArgumentException in the following situations:
 * Any argument to the constructor or an instance method is null
 * The input to the constructor does not correspond to a rooted DAG.
 * Any of the noun arguments in distance() or sap() is not a WordNet noun.
 *
 * @author jacka
 * @version 1.0 on 12/28/2019
 */
public class WordNet {
  private final Map<String, Set<Integer>> wordToIds = new HashMap<>();
  private final Map<Integer, String> idToWord = new HashMap<>();
  private final int v;
  private final SAP sap;

  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms) {
    if (synsets == null) {
      throw new IllegalArgumentException();
    }
    if (hypernyms == null) {
      throw new IllegalArgumentException();
    }
    // init synsets
    In in = new In(synsets);
    int maxId = 0;
    while (in.hasNextLine()) {
      final String line = in.readLine();
      final String[] items = line.split(",");
      final int id = Integer.parseInt(items[0]);
      maxId = id;
      idToWord.put(id, items[1]);
      final String[] words = items[1].split(" ");
      for (final String word : words) {
        wordToIds.computeIfAbsent(word, k -> new HashSet<>()).add(id);
      }
    }
    v = maxId + 1;
    final Digraph g = getDigraph(hypernyms);
    if (new DirectedCycle(g).hasCycle()) {
      throw new IllegalArgumentException("graph has a cycle");
    }
    boolean foundRootAlready = false;
    for (int i = 0; i < v; ++i) {
      if (g.outdegree(i) == 0) {
        if (foundRootAlready) {
          throw new IllegalArgumentException("Found multiple root");
        }
        foundRootAlready = true;
      }
    }
    sap = new SAP(g);
  }

  private Digraph getDigraph(String hypernyms) {
    final In in = new In(hypernyms);
    final Digraph g = new Digraph(v);
    while (in.hasNextLine()) {
      final String line = in.readLine();
      final String[] items = line.split(",");
      final int parent = Integer.parseInt(items[0]);
      for (int i = 1; i < items.length; ++i) {
        g.addEdge(parent, Integer.parseInt(items[i]));
      }
    }
    return g;
  }

  // returns all WordNet nouns
  public Iterable<String> nouns() {
    return wordToIds.keySet();
  }

  // is the word a WordNet noun?
  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException();
    }
    return wordToIds.containsKey(word);
  }

  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("nounA = " + nounA + ", nounB = " + nounB);
    }
    return sap.length(wordToIds.get(nounA), wordToIds.get(nounB));
  }

  // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException("nounA = " + nounA + ", nounB = " + nounB);
    }
    final int ancestorId = sap.ancestor(wordToIds.get(nounA), wordToIds.get(nounB));
    return idToWord.get(ancestorId);
  }

}