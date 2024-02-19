package model;

import model.Station;
import model.StationSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestStationSearch {

    private ArrayList<Station> stations;

    @BeforeEach
    void setUp() {

        stations = new ArrayList<>();
        stations.add(new Station(266, "HT", "Den Bosch", "NL", "knooppuntIntercitystation", 51.69048,5.29362 ));
        stations.add(new Station(269, "HTO", "DN Bosch O", "NL", "stoptreinstation", 51.700553894043, 5.3183331489563));
        stations.add(new Station(3227, "HDE", "t Harde", "NL", "stoptreinstation", 52.4091682, 5.893611));


        StationSearch.sortStationsByName(stations);
    }

    @Test
    void testBinarySearchByNameFound() {
        Station result = StationSearch.binarySearchByName(stations, "Den Bosch");
        assertNotNull(result);
        assertEquals("Den Bosch", result.getName());
    }

    @Test
    void testBinarySearchByNameNotFound() {
        Station result = StationSearch.binarySearchByName(stations, "Non Existent");
        assertNull(result);
    }

    @Test
    void testSortStationsByName() {
        // Intentionally shuffling the stations
        stations.clear();
        stations.add(new Station(3227, "HDE", "t Harde", "NL", "stoptreinstation", 52.4091682, 5.893611));
        stations.add(new Station(269, "HTO", "DN Bosch O", "NL", "stoptreinstation", 51.700553894043, 5.3183331489563));
        stations.add(new Station(266, "HT", "Den Bosch", "NL", "knooppuntIntercitystation", 51.69048,5.29362 ));

        StationSearch.sortStationsByName(stations);

        // Checking if the list is correctly sorted
        assertEquals("DN Bosch O", stations.get(0).getName());
        assertEquals("Den Bosch", stations.get(1).getName());
        assertEquals("t Harde", stations.get(2).getName());

    }

    @Test
    void testBinarySearchByNameEmptyList() {
        stations.clear();

        Station result = StationSearch.binarySearchByName(stations, "Den Bosch");
        assertNull(result);
    }



    @Test
    void testBinarySearchByNameCaseInsensitiveShouldAcceptUpperCaseAndLowwerCase() {
        Station result = StationSearch.binarySearchByName(stations, "den BOSCH");
        assertNotNull(result);
        assertEquals("Den Bosch", result.getName());
    }

    @Test
    void testBinarySearchByLongestName() {
        String longestName = "A Very Very Long Station Name That Is Unusual";
        stations.add(new Station(123321, "BIGCODE", longestName, "NL", "stoptreinstation", 52.183, 5.0032));
        StationSearch.sortStationsByName(stations);

        Station result = StationSearch.binarySearchByName(stations, longestName);
        assertNotNull(result);
        assertEquals(longestName, result.getName());
    }

    @Test
    void testLinearSearchByNameFound() {
        Station result = StationSearch.linearSearchByName(stations, "Den Bosch");
        assertNotNull(result);
        assertEquals("Den Bosch", result.getName());
    }

    @Test
    void testLinearSearchByNameNotFound() {
        Station result = StationSearch.linearSearchByName(stations, "Non Existent");
        assertNull(result);
    }

    @Test
    void testLinearSearchByNameCaseInsensitiveShouldAcceptUpperCaseAndLowwerCase() {
        Station result = StationSearch.linearSearchByName(stations, "dEN bOSCH");
        assertNotNull(result);
        assertEquals("Den Bosch", result.getName());
    }

    @Test
    void testLinearSearchByNameEmptyList() {
        ArrayList<Station> emptyList = new ArrayList<>();
        Station result = StationSearch.linearSearchByName(emptyList, "Den Bosch");
        assertNull(result);
    }
}
