
/**
 * Abhinav Khanna axk1312
 */
import static org.junit.Assert.*;
import org.junit.Test;

public class GraphTester {

    @Test
    public void testAddNode() {
        Graph graph = new Graph();

        // testing add node
        // testing adding to empty graph
        assertEquals(true, graph.addNode("a"));

        // adding to mon-empty graph
        assertEquals(true, graph.addNode("b"));
        assertEquals(true, graph.addNode("c"));
        assertEquals(true, graph.addNode("d"));

        // trying to add "a" again
        assertEquals(false, graph.addNode("a"));

        // trying to add using array . this should return true since all are new nodes
        // and are not duplicates
        assertEquals(true, graph.addNodes(new String[] { "g", "h", "i" }));

        // trying to add an array that has already been added should return false;
        assertEquals(false, graph.addNodes(new String[] { "g", "h", "i" }));

        // trying to add an array in which one elemnt is a duplicate
        assertEquals(false, graph.addNodes(new String[] { "z", "x", "y", "a" }));

    }

    @Test
    public void testAddEdge() {
        Graph graph = new Graph();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addNode("d");

        // adding edges should return true
        assertEquals(true, graph.addEdge("a", "b"));
        assertEquals(true, graph.addEdge("b", "c"));

        // adding an edge from a to a node that does not exist will return false
        assertEquals(false, graph.addEdge("a", "g"));

        // trying to add edge between two nodes that does not exist
        assertEquals(false, graph.addEdge("z", "y"));
    }

    @Test
    public void testRemoveNode() {
        Graph graph = new Graph();
        graph.addNode("x");
        graph.addNode("y");
        graph.addNode("z");
        graph.addNode("a");
        graph.addNode("d");
        graph.addNode("c");
        graph.addNode("e");
        // removing a node that exists in the graph
        assertEquals(true, graph.removeNode("x"));

        // removing a node that does not exist in the node
        assertEquals(false, graph.removeNode("b"));

        // trying to remove multiple nodes that exist
        assertEquals(true, graph.removeNodes(new String[] { "y", "z" }));

        // trying to remove multiple nodes that does not exist
        assertEquals(false, graph.removeNodes(new String[] { "x", "y", "z" }));

    }

    @Test
    public void testDFS() {
        Graph graph = new Graph();
        graph.addNodes(new String[] { "A", "B", "C", "D", "E" });
        graph.addEdges("A", new String[] { "B", "C", "D" });
        graph.addEdges("B", new String[] { "C" });

        String[] test1 = graph.DFS("A", "C", "alphabetical");
        assertArrayEquals(test1, new String[] { "A", "B", "C" });

        String[] test2 = graph.DFS("A", "C", "reverse");
        assertArrayEquals(test2, new String[] { "C", "A" });
    }

    @Test
    public void testBFS() {
        Graph graph = new Graph();
        graph.addNodes(new String[] { "A", "B", "C", "D", "E" });
        graph.addEdges("A", new String[] { "B", "C", "D" });
        graph.addEdges("B", new String[] { "C" });
        graph.addEdge("C", "E");
        String[] test1 = graph.BFS("A", "E", "alphabetical");
        assertArrayEquals(test1, new String[] { "A", "C", "E" });

        // targeting and returning the path in reverse order
        String[] test2 = graph.BFS("A", "C", "reverse");
        assertArrayEquals(test2, new String[] { "C", "A" });

        // trying to target a node that does not exist
        String[] test3 = graph.BFS("A", "X", "alphabetical");
        assertArrayEquals(new String[0], test3);
    }

    @Test
    public void testSecondShortestPath() {
        Graph graph = new Graph();
        graph.addNodes(new String[] { "A", "B", "C", "D", "E" });
        graph.addEdges("A", new String[] { "B", "C", "D" });
        graph.addEdges("A", new String[] { "E" });
        graph.addEdge("B", "E");
        // if there is a second shortest path
        String[] test = graph.secondShortestPath("B", "E");
        assertArrayEquals(test, new String[] { "B", "A", "E" });

        // no second shortest path since both are adjacent
        String[] test1 = graph.secondShortestPath("C", "D");
        assertArrayEquals(new String[0], test1);
    }
}