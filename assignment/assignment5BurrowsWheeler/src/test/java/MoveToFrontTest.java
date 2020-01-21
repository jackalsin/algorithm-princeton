import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jacka
 * @version 1.0 on 1/21/2020
 */
class MoveToFrontTest {
  /* From abra.txt */
  private static String DECODED_INPUT = "ABRACADABRA!";
  /* From abra.txt */
  private static byte[] ENCODED_INPUT = {0x41, 0x42, 0x52,
      0x2, 0x44, 0x1, 0x45, 0x1, 0x4, 0x4, 0x2, 0x26};

  private ByteArrayOutputStream resultOutStream;

  @BeforeEach
  void setUp() {
    // create new output stream as byte array and assign to standard
    resultOutStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(resultOutStream));
  }

  @Test
  void encodeTest() {
    System.setIn(new ByteArrayInputStream(DECODED_INPUT.getBytes()));
    MoveToFront.encode();
    byte[] actual = resultOutStream.toByteArray();
    System.err.println("printing actual = " + Arrays.toString(actual));
    System.err.println("printing encoded = " + Arrays.toString(ENCODED_INPUT));
    for (int i = 0; i < ENCODED_INPUT.length; i++) {
      assertEquals(ENCODED_INPUT[i], actual[i]);
    }
  }

  @Test
  void decodeTest() {
    System.setIn(new ByteArrayInputStream(ENCODED_INPUT));

    MoveToFront.decode();
    String decoded = resultOutStream.toString();

    System.err.println("printing actual = " + decoded);
    System.err.println("printing decoded = " + DECODED_INPUT);

    for (int i = 0; i < DECODED_INPUT.length(); i++) {
      assertEquals(DECODED_INPUT.charAt(i), decoded.charAt(i));
    }
  }

  @AfterEach
  void toFinish() {
    System.setIn(null);
    System.setOut(null);
  }
}
