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
 * @version 1.0 on 1/22/2020
 */
class BurrowsWheelerTest {
  private static String DECODED_INPUT = "ABRACADABRA!";

  private static byte[] ENCODED_INPUT = {0x0, 0x0, 0x0, 0x3,
      0x41, 0x52, 0x44, 0x21, 0x52, 0x43, 0x41, 0x41, 0x41,
      0x41, 0x42, 0x42};
  private ByteArrayOutputStream resultOutputStream;

  @BeforeEach
  void setUp() throws Exception {

    // create new output stream as byte array and assign to standard
    resultOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(resultOutputStream));
  }

  @AfterEach
  void toFinish() {
    System.setIn(null);
    System.setOut(null);
  }

  @Test
  void encodeTest() throws Exception {
    System.setIn(new ByteArrayInputStream(DECODED_INPUT.getBytes()));
    // create new output stream as byte array and assign to standard
    System.setOut(new PrintStream(resultOutputStream));

    BurrowsWheeler.transform();
    byte[] encoded = resultOutputStream.toByteArray();

    System.err.println("output = \t" + Arrays.toString(encoded));
    System.err.println("reference = " + Arrays.toString(ENCODED_INPUT));
    // 4 = 32/ 8 int is 4 bytes
    for (int i = 0; i < 4; i++) {
      assertEquals(ENCODED_INPUT[i], encoded[i]);
    }
    // letters
    for (int i = 4; i < encoded.length; i++) {
      assertEquals(ENCODED_INPUT[i], encoded[i]);
    }
  }

  @Test
  void decodeTest() throws Exception {
    System.setIn(new ByteArrayInputStream(ENCODED_INPUT));
    resultOutputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(resultOutputStream));

    BurrowsWheeler.inverseTransform();
    String decoded = resultOutputStream.toString();
    System.err.println("Actual = " + decoded);
    // check length and chars
    assertEquals(DECODED_INPUT.length(), decoded.length());
    assertEquals(DECODED_INPUT, decoded);
  }

  private static String DECODED_ZEBRA = "zebra!";

  private static byte[] ENCODED_ZEBRA = {0x0, 0x0, 0x0, 0x3,
      0x41, 0x52, 0x44, 0x21, 0x52, 0x43, 0x41, 0x41, 0x41,
      0x41, 0x42, 0x42};
}
