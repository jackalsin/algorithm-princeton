import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

/**
 * @author jacka
 * @version 1.0 on 1/20/2020
 */
public final class MoveToFront {
  private static final int R = 8;

  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode() {
    final char[] chars = new char[256];
    for (int i = 0; i < 256; i++) {
      chars[i] = (char) i;
    }
    try {
      while (!BinaryStdIn.isEmpty()) {
        final char chr = BinaryStdIn.readChar();
        final int index = find(chars, chr);
        BinaryStdOut.write(index, R);
      }
    } finally {
      BinaryStdIn.close();
      BinaryStdOut.close();
    }
  }

  private static int find(final char[] chars, final char chr) {
    for (int i = 0; i < chars.length; ++i) {
      if (chr == chars[i]) {
        moveToFront(chars, i);
        return i;
      }
    }
    throw new IllegalStateException("Cannot find " + chr + " in " + Arrays.toString(chars));
  }

  private static void moveToFront(final char[] chars, final int i) {
    final char tmp = chars[i];
    for (int k = i; k >= 1; --k) {
      chars[k] = chars[k - 1];
    }
    chars[0] = tmp;
  }

  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode() {
    final char[] chars = new char[256];
    for (int i = 0; i < 256; i++) {
      chars[i] = (char) i;
    }
    try {
      while (!BinaryStdIn.isEmpty()) {
        final int index = BinaryStdIn.readInt(R);
        final char chr = chars[index];
        moveToFront(chars, index);
        BinaryStdOut.write(chr);
      }
    } finally {
      BinaryStdIn.close();
      BinaryStdOut.close();
    }
  }

  private static final String ERROR_MESSAGE = "Args must be '+' or '-'";
  private static final String PLUS = "+", MINUS = "-";

  // if args[0] is "-", apply move-to-front encoding
  // if args[0] is "+", apply move-to-front decoding
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException(ERROR_MESSAGE);
    }
    if (PLUS.equals(args[0])) {
      decode();
    } else if (MINUS.equals(args[0])) {
      encode();
    }
  }

}
