import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jacka
 * @version 1.0 on 1/15/2020
 */
class FordFulkersonTest {

  @ParameterizedTest
  @MethodSource("solutionProvider")
  void testOnlineCase1(Class<FordFulkerson> solutionClass) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException, InstantiationException {
    final FlowNetwork graph = new FlowNetwork(6);
    graph.addEdge(new FlowEdge(0, 4, 10));
    graph.addEdge(new FlowEdge(4, 5, 9));
    graph.addEdge(new FlowEdge(5, 3, 10));
    graph.addEdge(new FlowEdge(0, 1, 5));
    graph.addEdge(new FlowEdge(1, 4, 4));
    graph.addEdge(new FlowEdge(4, 2, 4));
    graph.addEdge(new FlowEdge(5, 2, 15));
    graph.addEdge(new FlowEdge(2, 3, 10));
    graph.addEdge(new FlowEdge(1, 2, 8));
    final FordFulkerson solution = solutionClass.getConstructor(FlowNetwork.class, Integer.TYPE, Integer.TYPE).
        newInstance(graph, 0, 3);
    assertEquals(15, solution.value());
  }

  static Stream<Class> solutionProvider() {
    return Stream.of(
        FordFulkerson.class
    );
  }
}