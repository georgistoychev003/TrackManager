package graph;
import model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class TestStationsWithinRectangle {
    private StationsWithinRectangle swr;

    @BeforeEach
    void setUp() {
        // Assuming test_stations.csv and test_tracks.csv are valid test files
        swr = new StationsWithinRectangle("resources/stations.csv", "resources/tracks.csv");
    }

    @Test
    void testFindStationByCode() {
        Station station = swr.findStationByCode("DV");
        assertNotNull(station);
        assertEquals("DV", station.getCode());

        Station invalidStation = swr.findStationByCode("InvalidCode");
        assertNull(invalidStation);
    }


    @Test
    void testCalculateMinimumSpanningTree() {
        //ChatGPT recommended me to use ByteArrayOutPutStream as my previous tests were failing
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        double deventerLat= 52.257499694824;
        double deventerLong= 6.1605553627014;
        double newAmsterdamLat= 52.718612670898;
        double newAmsterdamLong= 6.8486108779907;

        swr.calculateMinimumSpanningTree(deventerLat,deventerLong, newAmsterdamLat,newAmsterdamLong);


        String output = outContent.toString();

        assertTrue(output.contains("Total length of the minimum spanning tree: 86.0"));

        assertTrue(output.contains("Stations within the rectangle:"));

        assertTrue(output.contains("Deventer"));
        assertTrue(output.contains("Holten"));
        assertTrue(output.contains("Rijssen"));
        assertTrue(output.contains("Delden"));
        assertTrue(output.contains("Wierden"));
        assertTrue(output.contains("Dalfsen"));
        assertTrue(output.contains("Heino"));
        assertTrue(output.contains("Meppel"));
        assertTrue(output.contains("Gezondhprk"));
        assertTrue(output.contains("Almelo"));
        assertTrue(output.contains("Nijverdal"));
        assertTrue(output.contains("Ommen"));
        assertTrue(output.contains("Raalte"));
        assertTrue(output.contains("Hengelo"));
        assertTrue(output.contains("de Riet"));
        assertTrue(output.contains("Vriezenvn"));
        assertTrue(output.contains("Mariënberg"));
        assertTrue(output.contains("Borne"));
        assertTrue(output.contains("Hengelo O"));
        assertTrue(output.contains("Daarlervn"));
        assertTrue(output.contains("Hardenberg"));
        assertTrue(output.contains("Vroomshoop"));
        assertTrue(output.contains("Gramsbergn"));
        assertTrue(output.contains("Coevorden"));
        assertTrue(output.contains("Nw A'dam"));


        // Reset the standard output
        System.setOut(System.out);
    }


    @Test
    void testCalculateMinimumSpanningTreeWithInvalidGPSCoordinates() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        swr.calculateMinimumSpanningTree(-999, -999, -999, -999);

        String output = outContent.toString();
        assertTrue(output.contains("No stations available within the given rectangle."));

        System.setOut(System.out);
    }


    @Test
    void testCalculateMinimumSpanningTreeWithValidCoordinatesButNoStationsThere() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Coordinates that don't contain any stations
        swr.calculateMinimumSpanningTree(0.0, 0.0, 1.0, 1.0);

        String output = outContent.toString();
        assertTrue(output.contains("No stations available within the given rectangle."));
        assertFalse(output.contains("Total length of the minimum spanning tree:"));

        System.setOut(System.out);
    }

    @Test
    void testCalculateMinimumSpanningTreeWithSameStationForBothCornersOfTheRectangle() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Station dvStation = swr.findStationByCode("DV");
        assertNotNull(dvStation);
        double dvLat = dvStation.getGeoLat();
        double dvLong = dvStation.getGeoLng();

        swr.calculateMinimumSpanningTree(dvLat, dvLong, dvLat, dvLong);

        String output = outContent.toString();


        assertTrue(output.contains("Stations within the rectangle:"));
        assertTrue(output.contains("Deventer"));
        assertTrue(output.contains("Total length of the minimum spanning tree: 0.0"));

        System.setOut(System.out);
    }
}
