import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author jacka
 * @version 1.0 on 1/21/2020
 */
public class CircularSuffixArrayTest {

  /**
   * i       Original Suffixes           Sorted Suffixes         index[i]
   * --    -----------------------     -----------------------    --------
   * 0    A B R A C A D A B R A !     ! A B R A C A D A B R A    11
   * 1    B R A C A D A B R A ! A     A ! A B R A C A D A B R    10
   * 2    R A C A D A B R A ! A B     A B R A ! A B R A C A D    7
   * 3    A C A D A B R A ! A B R     A B R A C A D A B R A !    0
   * 4    C A D A B R A ! A B R A     A C A D A B R A ! A B R    3
   * 5    A D A B R A ! A B R A C     A D A B R A ! A B R A C    5
   * 6    D A B R A ! A B R A C A     B R A ! A B R A C A D A    8
   * 7    A B R A ! A B R A C A D     B R A C A D A B R A ! A    1
   * 8    B R A ! A B R A C A D A     C A D A B R A ! A B R A    4
   * 9    R A ! A B R A C A D A B     D A B R A ! A B R A C A    6
   * 10    A ! A B R A C A D A B R     R A ! A B R A C A D A B    9
   * 11    ! A B R A C A D A B R A     R A C A D A B R A ! A B    2
   */
  @Test
  void testOnlineCase() {
    final String input = "ABRACADABRA!";
    CircularSuffixArray solution = new CircularSuffixArray(input);
    final int[] expected = {11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2},
        actual = new int[input.length()];
    for (int i = 0; i < input.length(); ++i) {
      actual[i] = solution.index(i);
    }
    assertArrayEquals(expected, actual);
  }
}
