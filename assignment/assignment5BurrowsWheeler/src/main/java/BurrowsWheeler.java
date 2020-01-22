import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * @author jacka
 * @version 1.0 on 1/20/2020
 */
public class BurrowsWheeler {
  private static final int LgR = 8, R = 256;

  // apply Burrows-Wheeler transform,
  // reading from standard input and writing to standard output
  public static void transform() {
    final String str = BinaryStdIn.readString();
    final CircularSuffixArray csa = new CircularSuffixArray(str);
    final int len = csa.length();
    // print out the index
    for (int i = 0; i < len; ++i) {
      if (csa.index(i) == 0) {
        BinaryStdOut.write(i);
        break;
      }
    }
    final char[] chars = new char[len];
    for (int i = 0; i < len; i++) {
      final int indexI = csa.index(i);
      if (indexI == 0) {
        chars[i] = str.charAt(str.length() - 1);
      } else {
        chars[i] = str.charAt(indexI - 1);
      }
    }
    for (int i = 0; i < len; ++i) {
      BinaryStdOut.write(chars[i], LgR);
    }
    BinaryStdOut.close();
    BinaryStdIn.close();
  }

  // apply Burrows-Wheeler inverse transform,
  // reading from standard input and writing to standard output
  public static void inverseTransform() {
    final int first = BinaryStdIn.readInt();
    final String input = BinaryStdIn.readString();
    final int len = input.length();
    final char[] t = input.toCharArray();

    final int[] next = getNext(t);
//    System.err.println(Arrays.toString(next));
    for (int i = 0, j = next[first]; i < len; ++i, j = next[j]) {
      BinaryStdOut.write(t[j]);
    }
    BinaryStdIn.close();
    BinaryStdOut.close();
  }

  private static int[] getNext(final char[] t) {
    final int[] counts = new int[R + 1];
    for (char chr : t) {
      counts[chr + 1]++;
    }
    for (int i = 1; i <= R; ++i) {
      counts[i] += counts[i - 1];
    }
//    display(counts);
    final int[] next = new int[t.length];
    for (int i = 0; i < t.length; i++) {
      next[counts[t[i]]++] = i;
    }
    return next;
  }

  private static void display(final int[] counts) {
    for (char chr = 0; chr <= R; chr++) {
      System.err.println("chr = " + chr + ", " + counts[chr]);
    }
  }

  private static final String ERROR_MESSAGE = "Args must be '+' or '-'";
  private static final String PLUS = "+", MINUS = "-";

  // if args[0] is "-", apply Burrows-Wheeler transform
  // if args[0] is "+", apply Burrows-Wheeler inverse transform
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException(ERROR_MESSAGE);
    }
    if (PLUS.equals(args[0])) {
      inverseTransform();
    } else if (MINUS.equals(args[0])) {
      transform();
    }
  }

}
