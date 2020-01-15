import edu.princeton.cs.algs4.Picture;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author jacka
 * @version 1.0 on 1/1/2020
 */
class SeamCarverTest {
  private final ClassLoader classLoader = getClass().getClassLoader();

  @Test
  void testOnlineCase3x4V() {
    final String fileName = "3x4.png",
        path = classLoader.getResource(fileName).getPath();
    final Picture p = new Picture(path);
    final SeamCarver seamCarver = new SeamCarver(p);
    final Set<List<Integer>> expectedVerticalSeams = Set.of(
        List.of(0, 1, 1, 0),
        List.of(1, 1, 1, 0),
        List.of(2, 1, 1, 0),
        List.of(0, 1, 1, 1),
        List.of(1, 1, 1, 1),
        List.of(2, 1, 1, 1),
        List.of(0, 1, 1, 2),
        List.of(1, 1, 1, 2),
        List.of(2, 1, 1, 2)
    );
    final int[] actualVerticalSeam = seamCarver.findVerticalSeam();
    final List<Integer> actualVerticalSeamList = Arrays.stream(actualVerticalSeam).boxed().collect(Collectors.toList());
    assertTrue(expectedVerticalSeams.contains(actualVerticalSeamList));
  }

  @Test
  void testOnlineCase3x4W() {
    final String fileName = "3x4.png",
        path = classLoader.getResource(fileName).getPath();
    final Picture p = new Picture(path);
    final SeamCarver seamCarver = new SeamCarver(p);
    final Set<List<Integer>> expectedHorizontalSeams = Set.of(
        List.of(1, 2, 1),
        List.of(2, 2, 1),
        List.of(3, 2, 1),
        List.of(1, 2, 2),
        List.of(2, 2, 2),
        List.of(3, 2, 2),
        List.of(1, 2, 3),
        List.of(2, 2, 3),
        List.of(3, 2, 3)
    );
    final int[] actualHorizontalSeam = seamCarver.findHorizontalSeam();
    final List<Integer> actualHorizontalSeamList =
        Arrays.stream(actualHorizontalSeam).boxed().collect(Collectors.toList());
    assertTrue(expectedHorizontalSeams.contains(actualHorizontalSeamList));
  }

  @Test
  void testOnlineCase4x6V() {
    final String fileName = "4x6.png",
        path = classLoader.getResource(fileName).getPath();
    final Picture p = new Picture(path);
    final SeamCarver seamCarver = new SeamCarver(p);
    final Set<List<Integer>> expectedVerticalSeams = Set.of(
        List.of(1, 2, 1, 1, 2, 1),
        List.of(1, 2, 1, 1, 2, 2),
        List.of(1, 2, 1, 1, 2, 3),
        List.of(2, 2, 1, 1, 2, 1),
        List.of(2, 2, 1, 1, 2, 2),
        List.of(2, 2, 1, 1, 2, 3),
        List.of(3, 2, 1, 1, 2, 1),
        List.of(3, 2, 1, 1, 2, 2),
        List.of(3, 2, 1, 1, 2, 3)
    );
    final int[] actualVerticalSeam = seamCarver.findVerticalSeam();
    final List<Integer> actualVerticalSeamList = Arrays.stream(actualVerticalSeam).boxed().collect(Collectors.toList());
    assertTrue(expectedVerticalSeams.contains(actualVerticalSeamList));
  }
}