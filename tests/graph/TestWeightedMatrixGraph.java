package graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TestWeightedMatrixGraph {

    private WeightedMatrixGraph<String> undirectedGraph;
    private WeightedMatrixGraph<String> directedGraph;
    private WeightedMatrixGraph<Integer> directedGraphTestFromSlides;



    @BeforeEach
    void setUp() {
        //this test is inspired and  references the slides of week 5 where the graph theory is at
        directedGraphTestFromSlides = new WeightedMatrixGraph<>(true, 1, 2, 3, 4, 5, 6);
        directedGraphTestFromSlides.connect(1, 2, 1.0);
        directedGraphTestFromSlides.connect(2, 4, -1.0);
        directedGraphTestFromSlides.connect(4, 5, 3.0);
        directedGraphTestFromSlides.connect(5, 6, 19.0);
        directedGraphTestFromSlides.connect(1, 3, 12.0);
        directedGraphTestFromSlides.connect(3, 4, 8.0);

        undirectedGraph = new WeightedMatrixGraph<>(false, "A", "B", "C", "D");
        directedGraph = new WeightedMatrixGraph<>(true, "A", "B", "C", "D");

    }





    //this test is inspired and references the slides of week 5 where the graph theory is at
    @Test
    void testDirectedGraphConnections() {
        assertTrue(directedGraphTestFromSlides.isDirected());
        assertEquals(1.0, directedGraphTestFromSlides.getWeight(1, 2));
        assertEquals(-1.0, directedGraphTestFromSlides.getWeight(2, 4));
        assertEquals(3.0, directedGraphTestFromSlides.getWeight(4, 5));
        assertEquals(19.0, directedGraphTestFromSlides.getWeight(5, 6));
        assertEquals(12.0, directedGraphTestFromSlides.getWeight(1, 3));
        assertEquals(8.0, directedGraphTestFromSlides.getWeight(3, 4));
        assertNull(directedGraphTestFromSlides.getWeight(4, 2)); // There is no edge from 4 to 2
        assertNull(directedGraphTestFromSlides.getWeight(2, 3)); // There is no edge from 2 to 3
    }

    //this test is inspired and references the slides of week 5 where the graph theory is at
    @Test
    void testAddVertex() {
        int initialSize = directedGraphTestFromSlides.getVertices().size();
        directedGraphTestFromSlides.addVertex(7);
        assertEquals(initialSize + 1, directedGraphTestFromSlides.getVertices().size());
        assertTrue(directedGraphTestFromSlides.contains(7));
    }


    //this test is inspired and references the slides of week 5 where the graph theory is at
    @Test
    void testGetIndex() {
        assertEquals(0, directedGraphTestFromSlides.getIndex(1));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            directedGraphTestFromSlides.getIndex(100);
        });
        assertEquals("Vertex not present in the graph.", exception.getMessage());
    }

    //this test is inspired and references the slides of week 5 where the graph theory is at
    @Test
    void testBreadthFirstTraversal() {
        List<Integer> bfs = directedGraphTestFromSlides.breadthFirst(1);
        List<Integer> expectedOrder = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertEquals(expectedOrder, bfs);
    }

    //this test is inspired and references the slides of week 5 where the graph theory is at
    @Test
    void testDepthFirstTraversal() {
        List<Integer> dfs = directedGraphTestFromSlides.depthFirst(1);
        List<Integer> expectedOrder = Arrays.asList(1, 3, 4, 5, 6, 2);
        assertEquals(expectedOrder, dfs);
    }

    //this test is inspired and references the slides of week 5 where the graph theory is at
    @Test
    void testIllegalConnectionsShouldThrowAnException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            directedGraphTestFromSlides.connect(1, 100, 1.0); // 100 is not a valid vertex
        });
        assertTrue(exception.getMessage().contains("Vertex not present in the graph"));
    }

    //this test is inspired and references slides of week 5 where the graph theory is at
    @Test
    void testAddExistingVertexShouldThrowAnException() {
        // Attempt to add an existing vertex
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            directedGraphTestFromSlides.addVertex(3); // Vertex 3 already exists in the graph so this should trigger the exception
        });

        // Verify that the message of the exception is as expected
        String expectedMessage = "Vertex already present in the graph.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //this test is inspired and references slides of week 5 where the graph theory is at
    @Test
    void testToWebGraphViz() {

        String expectedGraphViz = "digraph G {\n" +
                "\t1 -> 2[label=\"1.0\"];\n" +
                "\t1 -> 3[label=\"12.0\"];\n" +
                "\t2 -> 4[label=\"-1.0\"];\n" +
                "\t3 -> 4[label=\"8.0\"];\n" +
                "\t4 -> 5[label=\"3.0\"];\n" +
                "\t5 -> 6[label=\"19.0\"];\n" +
                "}";


        String actualGraphViz = directedGraphTestFromSlides.toWebGraph();
        assertEquals(expectedGraphViz, actualGraphViz);
    }


    /*Some additional tests of 2 different graphs (undirected and a directed one)*/

    @Test
    void testCheckIfVerticesAreDirected() {
        assertFalse(undirectedGraph.isDirected());
        assertTrue(directedGraph.isDirected());
    }

    @Test
    void testConnectVerteces() {
        undirectedGraph.connect("A", "B", 1.0);
        assertTrue(undirectedGraph.areConnected("A", "B"));
        assertTrue(undirectedGraph.areConnected("B", "A"));

        directedGraph.connect("A", "B", 2.0);
        assertTrue(directedGraph.areConnected("A", "B"));
        assertFalse(directedGraph.areConnected("B", "A"));
    }

    @Test
    void testGetConnectedNeighbours() {
        undirectedGraph.connect("A", "B", 1.0);
        undirectedGraph.connect("A", "C", 2.0);

        Iterable<String> neighbours = undirectedGraph.getConnectedNeighbours("A");
        assertTrue(((Collection<String>) neighbours).containsAll(Arrays.asList("B", "C")));
    }


    @Test
    void testGetVertices() {
        List<String> vertices = undirectedGraph.getVertices();
        assertNotNull(vertices);
        assertEquals(Arrays.asList("A", "B", "C", "D"), vertices);
    }

    @Test
    void testContains() {

        assertTrue(directedGraph.contains("A"));
        assertFalse(directedGraph.contains("Z"));
    }


    @Test
    void testGetVertexCount() {
        assertEquals(4, ((WeightedMatrixGraph<String>) directedGraph).getVertexCount()); // I had to cast to access protected method
    }


    @Test
    void testToWebGraph() {
        directedGraph.connect("A", "B", 2.0);
        directedGraph.connect("A", "C", 3.0);
        String expectedOutput = "digraph G {\n\tA -> B[label=\"2.0\"];\n\tA -> C[label=\"3.0\"];\n}";
        assertEquals(expectedOutput, directedGraph.toWebGraph());
    }

    @Test
    void testToString() {
        WeightedMatrixGraph<String> testGraph = new WeightedMatrixGraph<>(false, "A", "B", "C");
        testGraph.connect("A", "B", 1.0);
        testGraph.connect("B", "C", 2.0);

        String expected = "WeightedMatrixGraph {\n" +
                "  directed = false\n" +
                "  vertices = [A, B, C]\n" +
                "  connections = [\n" +
                "    A -> B [weight=1.0]\n" +
                "    B -> A [weight=1.0]\n" + // as the graph is undirected
                "    B -> C [weight=2.0]\n" +
                "    C -> B [weight=2.0]\n" + // as the graph is undirected
                "  ]\n}";


        assertEquals(expected, testGraph.toString());
    }



}
