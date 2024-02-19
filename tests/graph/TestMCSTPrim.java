package graph;

import model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestMCSTPrim {
    private WeightedMatrixGraph<Station> graph;
    private MCSTPrim prim;
    private Station a, b, c, d, e, f, g, h, i;

    @BeforeEach
    void setUp() {

        //this test referenced the example given in the sheets for Prim in week 7 of cds
        graph = new WeightedMatrixGraph<>(false);
        // Initialize stations
        a = new Station(1, "A", "A", "NL", "station", 45.12, 5.12 );
        b = new Station(2, "B", "B", "NL", "station", 35.24, 6.12 );
        c = new Station(3, "C", "C", "NL", "station", 75.62, 5.17 );
        d = new Station(4, "D", "D", "NL", "station", 55.34, 6.27 );
        e = new Station(5, "E", "E", "NL", "station", 35.21, 7.89 );
        f = new Station(6, "F", "F", "NL", "station", 68.31, 4.39 );
        g = new Station(7, "G", "G", "NL", "station", 25.12, 7.21 );
        h = new Station(8, "H", "H", "NL", "station", 33.33, 3.33 );
        i = new Station(9, "I", "I", "NL", "station", 23.93, 6.78 );

        // Add stations to the graph
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        // Add edges to the graph as per the example in the slides
        graph.connect(a, b, 4);
        graph.connect(a, h, 8);
        graph.connect(b, c, 8);
        graph.connect(b, h, 11);
        graph.connect(c, d, 7);
        graph.connect(c, f, 4);
        graph.connect(c, i, 2);
        graph.connect(d, e, 9);
        graph.connect(d, f, 14);
        graph.connect(e, f, 10);
        graph.connect(f, g, 2);
        graph.connect(g, h, 1);
        graph.connect(g, i, 6);
        graph.connect(h, i, 7);


        prim = new MCSTPrim(graph);
    }

    //this test referenced the example given in the sheets for Prim in week 7 of cds
    @Test
    void testPrimAlgorithm() {
        prim.prim(a); // Start the algorithm at station a


        // Verify the parent of each station after Prim's algorithm
        assertNull(prim.getParentMap().get(a)); // a is the start vertex and has no parent
        assertEquals(a, prim.getParentMap().get(b)); // b should have 'a' as its parent
        assertEquals(c, prim.getParentMap().get(d)); // d should have 'c' as its parent
        assertEquals(d, prim.getParentMap().get(e)); // e should have 'd' as its parent
        assertEquals(c, prim.getParentMap().get(f)); // f should have 'c' as its parent
        assertEquals(f, prim.getParentMap().get(g)); // g should have 'f' as its parent
        assertEquals(g, prim.getParentMap().get(h)); // h should have 'g' as its parent
        assertEquals(c, prim.getParentMap().get(i)); // i should have 'c' as its parent
    }

    //this test referenced the example given in the sheets for Prim in week 7 of cds
    @Test
    void testCalculateTotalLengthOfMCSTPrim(){
        prim.prim(a);
        double totalLength = prim.calculateTotalLength();

        // Expected total length of the MST is the sum of the weights of the selected edges
        double expectedLength = 4 + 8 + 7 + 9 + 4 + 2 + 1 + 2; // Based on the example in the slides of week 7
        assertEquals(expectedLength, totalLength);
    }

    @Test
    void testPrimWithEmptyGraph() {
        graph = new WeightedMatrixGraph<>(false);
        prim = new MCSTPrim(graph);
        prim.prim(a);
        assertTrue(prim.getParentMap().isEmpty());
    }

    @Test
    void testPrimWithSingleVertex() {
        graph = new WeightedMatrixGraph<>(false);
        prim = new MCSTPrim(graph);
        graph.addVertex(a);
        prim.prim(a);
        assertNull(prim.getParentMap().get(a));
        assertEquals(0.0, prim.calculateTotalLength());
    }

    @Test
    void testPrimWithDisconnectedGraph() {
        // Add a disconnected vertex 'j'
        Station j = new Station(10, "J", "J", "NL", "station", 0.0, 0.0);
        graph.addVertex(j);
        prim.prim(a);
        assertNull(prim.getParentMap().get(j)); // j should have no parent since it's disconnected
    }
}
