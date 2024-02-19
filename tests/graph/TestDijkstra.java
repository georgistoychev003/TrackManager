package graph;

import model.Station;
import model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TestDijkstra {
    //the tests are taken and refer to the example given in the slides of week 7 cds
    private WeightedMatrixGraph<Station> graph;
    private Station a, b, c, d, e, f, g;

    @BeforeEach
    void setUp() {
        graph = new WeightedMatrixGraph<>(false);
        // Create stations
        a = new Station(1, "A", "A", "NL", "station", 45.12, 5.12 );
        b = new Station(2, "B", "B", "NL", "station", 35.24, 6.12 );
        c = new Station(3, "C", "C", "NL", "station", 75.62, 5.17 );
        d = new Station(4, "D", "D", "NL", "station", 55.34, 6.27 );
        e = new Station(5, "E", "E", "NL", "station", 35.21, 7.89 );
        f = new Station(6, "F", "F", "NL", "station", 68.31, 4.39 );
        g = new Station(7, "G", "G", "NL", "station", 25.12, 7.21 );
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);


        // Add edges to the graph according same weight as the one  shown in the slides of week 7 cds
        graph.connect(a, b, 1);
        graph.connect(a, c, 4);
        graph.connect(b, e, 10);
        graph.connect(b, d, 3);
        graph.connect(b, c, 2);
        graph.connect(e, d, 5);
        graph.connect(e, g, 2);
        graph.connect(e, f, 7);
        graph.connect(f, g, 5);
        graph.connect(g, d, 1);
        graph.connect(g, c, 3);
        graph.connect(c, d, 6);

    }
    //the tests are taken and refer to the example given in the slides of week 7 cds
    @Test
    void testFindShortestPathSuccessfully() {
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, f);
        assertNotNull(path, "Path should not be null when a path exists.");
        assertFalse(path.isEmpty(), "Path should not be empty when a path exists.");
        List<Station> expectedPath = List.of(a, b, d, g, f); // Ensure these stations match the ones in your graph
        assertEquals(expectedPath, path);
    }
    //the tests are taken and refer to the example given in the slides of week 7 cds
    @Test
    void testFindShortestPathNoPathExists() {
        Station z = new Station(8, "Z", "Z", "NL", "station", 28.33, 7.39);
        graph.addVertex(z);
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, z);
        assertTrue(path == null || path.isEmpty());
    }
    //the tests are taken and refer to the example given in the slides of week 7 cds
    @Test
    void testFindShortestPathWithEmptyGraph() {
        graph = new WeightedMatrixGraph<>(false);
        graph.addVertex(a);
        graph.addVertex(f);
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, f);
        assertTrue( path.isEmpty(), "Expected an empty path for an empty graph.");
    }


    //the tests are taken and refer to the example given in the slides of week 7 cds
    @Test
    void testFindShortestPathWhenOneNodeIsNotConnectedShouldNotFindAPath() {
        Station h = new Station(9, "H", "H", "NL", "station", 10.33, 8.39);
        graph.addVertex(h); // H is isolated and i purposefully do not connect it to any vertex
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, h);
        assertTrue(path.isEmpty(), "Path should be empty when target is not connected.");
    }

    @Test
    void testFindShortestPathInReverseOrder() {
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, f, a);
        List<Station> expectedPath = List.of(f, g, d, b, a);
        assertEquals(expectedPath, path);
    }

    @Test
    void testFindShortestPathWithSingleNode() {
        graph = new WeightedMatrixGraph<>(false);
        graph.addVertex(a);
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, a);
        assertEquals(Collections.singletonList(a), path, "Path with a single node graph should return the node itself.");
    }

    @Test
    void testFindShortestPathWithNoEdges() {
        graph = new WeightedMatrixGraph<>(false);
        graph.addVertex(a);
        graph.addVertex(b);
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, b);
        assertTrue(path.isEmpty(), "Path should be empty when there are no edges.");
    }

    @Test
    void testFindShortestPathWithDirectConnection() {
        List<Station> path = DijkstraAlgorithm.findShortestPath(graph, a, b);
        assertEquals(List.of(a, b), path);
    }




}
