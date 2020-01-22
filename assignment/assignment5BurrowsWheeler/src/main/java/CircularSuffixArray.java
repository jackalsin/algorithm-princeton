import java.util.Arrays;

/**
 * @author jacka
 * @version 1.0 on 1/21/2020
 */
public final class CircularSuffixArray {
  private final char[] chars;
  private final int[] index;

  // circular suffix array of s
  public CircularSuffixArray(String s) {
    if (s == null) {
      throw new IllegalArgumentException();
    }
    final int len = s.length();
    chars = s.toCharArray();
    final int[] index = new int[len];
    for (int i = 0; i < len; ++i) {
      index[i] = i;
    }
    this.index = Arrays.stream(index).boxed()
        .sorted((start1, start2) -> {
          for (int del = 0; del < len; ++del) {
            final int i = (start1 + del) % len,
                j = (start2 + del) % len,
                cmp = Character.compare(chars[i], chars[j]);
            if (cmp != 0) {
              return cmp;
            }
          }
          return 0;
        }).mapToInt(x -> x).toArray();
  }

  // length of s
  public int length() {
    return chars.length;
  }

  // returns index of ith sorted suffix
  public int index(int i) {
    if (i < 0 || i >= chars.length) {
      throw new IllegalArgumentException();
    }
    return index[i];
  }

  // unit testing (required)
  public static void main(String[] args) {
  }
}
