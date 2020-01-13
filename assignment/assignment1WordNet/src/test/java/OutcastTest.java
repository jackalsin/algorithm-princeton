import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jacka
 * @version 1.0 on 12/29/2019
 */
class OutcastTest {
  @Test
  void testOnlineCase5() {
    WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
    Outcast outcast = new Outcast(wordnet);
    final In in = new In("outcast5.txt");
    final String[] nouns = in.readAllStrings();
    assertEquals("table", outcast.outcast(nouns));
  }

  @Test
  void testOnlineCase8() {
    WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
    Outcast outcast = new Outcast(wordnet);
    final In in = new In("outcast8.txt");
    final String[] nouns = in.readAllStrings();
    assertEquals("bed", outcast.outcast(nouns));
  }

  @Test
  void testOnlineCase11() {
    WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
    Outcast outcast = new Outcast(wordnet);
    final In in = new In("outcast11.txt");
    final String[] nouns = in.readAllStrings();
    assertEquals("potato", outcast.outcast(nouns));
  }

}