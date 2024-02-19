package graph;

import lists.DoublyLinkedList;
import model.Station;
import model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Station.readFromCSV;
import static org.junit.jupiter.api.Assertions.*;

class TestAStar {

    private AStarPathFinding astar;
    DoublyLinkedList<Station> stations = (DoublyLinkedList<Station>) readFromCSV("resources/stations.csv");
    ArrayList<Track> tracks = (ArrayList<Track>) Track.readFromCSV("resources/tracks.csv");

    ArrayList<Station> staionList = stations.toArrayList();


    @BeforeEach
    void setUp() {
        astar = new AStarPathFinding(tracks, staionList);
    }

    @Test
    void testFindPathSuccessfully() {
        List<Station> path = astar.findPath("dv", "na");
        assertNotNull(path);

    }

    @Test
    void testFindPathNoPathExists() {
        // "abcd" is a non-existent code
        List<Station> path = astar.findPath("dv", "abcd");
        assertNull(path);
    }

    @Test
    void testFindPathNonExistentStations() {
        List<Station> path = astar.findPath("xyz", "abcd");
        assertNull(path);
    }

    @Test
    void testCalculateTotalPathWeight() {
        List<Station> path = astar.findPath("dv", "na");
        assertNotNull(path);
        double weight = astar.calculateTotalPathWeight(path);
        assertTrue(weight > 0);
        assertTrue(weight < 91);
    }

    @Test
    void testResetState() {
        // Perform a path finding operation
        astar.findPath("dv", "na");
        // Reset state
        astar.resetState();
        // Attempt to find another path to show that i resetted the state successfully
        List<Station> path = astar.findPath("dv", "apd");
        assertNotNull(path);
    }

    @Test
    void testHeuristicFunction() {
        // Compare heuristic calculation with expected value
        Station current = new Station(1, "AMS", "Amsterdam", "NL", "megastation", 52.3702, 4.8952);
        Station goal = new Station(2, "RTD", "Rotterdam", "NL", "megastation", 51.9225, 4.4792);

        double expectedHeuristic = 0.611; // The expected straight-line distance
        double actualHeuristic = astar.heuristic(current, goal);
        assertEquals(expectedHeuristic, actualHeuristic, 0.001); // Allowing for some floating-point error
    }

    @Test
    void testGetCode() {
        Station station = new Station(1, "AMS", "Amsterdam", "NL", "megastation", 52.3702, 4.8952);
        AStarPathFinding.Node node = new AStarPathFinding.Node(station);

        String code = node.getCode();

        // Verify the code matches
        assertEquals("AMS", code);
    }


}
