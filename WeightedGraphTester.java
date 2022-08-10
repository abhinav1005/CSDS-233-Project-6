
/**
 * Abhinav Khanna axk1312
 */
import java.util.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeightedGraphTester {

    @Test
    public void testAddWeightEdge() {
        WeightedGraph graph = new WeightedGraph(10);
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        // adding weighted edge successfully
        assertEquals(true, graph.addWeightedEdges("A", new String[] { "C" }, new int[] { 3 }));

        // adding weighted edge that already has an edge connection will return false
        assertEquals(false, graph.addWeightedEdges("A", new String[] { "B", "C" }, new int[] { 3, 2 }));

        // adding all successful edges
        assertEquals(true, graph.addWeightedEdges("A", new String[] { "E", "D" }, new int[] { 4, 5 }));
    }

    @Test
    public void testShortestPath() {
        WeightedGraph graph = new WeightedGraph(10);
        graph.addNodes(new String[] { "A", "B", "C", "D" });
        graph.addWeightedEdges("A", new String[] { "B", "D" }, new int[] { 3, 1 });
        graph.addWeightedEdges("B", new String[] { "D", "C" }, new int[] { 2, 11 });

        String[] path1 = graph.shortestPath("A", "C");
        assertArrayEquals(new String[] { "C", "B", "A" }, path1);

    }

    @Test
    public void testSecondShortestPath() {
        WeightedGraph graph = new WeightedGraph(10);
        graph.addNodes(new String[] { "A", "B", "C", "D", "E", "F", "G" });
        graph.addWeightedEdges("A", new String[] { "B", "D" }, new int[] { 2, 1 });
        graph.addWeightedEdges("B", new String[] { "D", "E" }, new int[] { 3, 10 });
        graph.addWeightedEdges("C", new String[] { "A", "F" }, new int[] { 4, 5 });
        graph.addWeightedEdges("D", new String[] { "C", "E", "F", "G" }, new int[] { 2, 2, 8, 4 });
        String[] path = graph.secondShortestPath("A", "C");
        assertArrayEquals(new String[] { "C", "D", "B", "A" }, path);
    }

}