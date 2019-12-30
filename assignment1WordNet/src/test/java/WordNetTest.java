import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jacka
 * @version 1.0 on 12/28/2019
 */
class WordNetTest {

  @Test
  void testOnlineCase1() {
    final WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
    assertEquals("entity", wordNet.sap("gambol", "oral_contraceptive"));
    assertEquals(15, wordNet.distance("gambol", "oral_contraceptive"));
  }

}