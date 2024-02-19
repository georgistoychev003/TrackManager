package model;

import lists.DoublyLinkedList;
import lists.MyList;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static model.Station.convertToList;
import static model.Station.readFromCSV;
import static org.junit.jupiter.api.Assertions.*;


public class TestStation {
    private
    Station deventer;

@Test
public void testConstructorWithEmptyValues() {
    assertThrows(AssertionError.class, () -> new Station(1, "", "", "", "", 0, 0));
}

    @Test
    public void testConstructorWithNullValues() {
        assertThrows(AssertionError.class, () -> new Station(1, null, null, null, null, 0, 0));
    }

    @Test
    public void testConstructorWithLongName() {
        // Create a station with a long name
        Station station = new Station(1, "AMS", "This is a very long station name", "NL", "train", 52.3701, 4.8951);

        // Assert that the name is correct
        assertEquals("This is a very long station name", station.getName());
    }

    @Test
    public void testConstructorWithLongCode() {
        // Create a station with a long code
        Station station = new Station(1, "This is a very long station code", "Amsterdam", "NL", "train", 52.3701, 4.8951);

        // Assert that the code is correct
        assertEquals("This is a very long station code", station.getCode());
    }

    @Test
    public void testConstructorWithInvalidLatitude() {
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", -91, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", 91, 4.8951));
    }

    @Test
    public void testConstructorWithInvalidLongitude() {
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", 52.3701, -181));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", 52.3701, 181));
    }


    @Test
    public void testConstructor() {
        // Valid constructor
        Station station = new Station(1, "AMS", "Amsterdam", "NL", "train", 52.3701, 4.8951);
        assertEquals(1, station.getId());
        assertEquals("AMS", station.getCode());
        assertEquals("Amsterdam", station.getName());
        assertEquals("NL", station.getCountry());
        assertEquals("train", station.getType());
        assertEquals(52.3701, station.getGeoLat());
        assertEquals(4.8951, station.getGeoLng());

        // Invalid constructor
        assertThrows(AssertionError.class, () -> new Station(-1, "AMS", "Amsterdam", "NL", "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, null, "Amsterdam", "NL", "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "", "Amsterdam", "NL", "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", null, "NL", "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "", "NL", "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", null, "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "", "train", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", null, 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "", 52.3701, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", -91, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", 91, 4.8951));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", 52.3701, -181));
        assertThrows(AssertionError.class, () -> new Station(1, "AMS", "Amsterdam", "NL", "train", 52.3701, 181));
    }

    @Test
    public void testReadFromCSVSuccessful()  {
        // Valid CSV file
        MyList<Station> stations = readFromCSV("resources/stations.csv");
        assertEquals(578, stations.length());
        assertNotNull(stations);
    }


    @Test
    public void testGetters() {
        // Valid getters
        Station station = new Station(1, "AMS", "Amsterdam", "NL", "train", 52.3701, 4.8951);
        assertEquals(1, station.getId());
        assertEquals("AMS", station.getCode());
        assertEquals("Amsterdam", station.getName());
        assertEquals("NL", station.getCountry());
        assertEquals("train", station.getType());
        assertEquals(52.3701, station.getGeoLat());
        assertEquals(4.8951, station.getGeoLng());
    }

    @Test
void testConstructorValidInput() {
    Station station = new Station(1, "code", "name", "NL", "type", 50.0, 5.0);
    assertEquals(1, station.getId());
    assertEquals("code", station.getCode());
    assertEquals("name", station.getName());
    assertEquals("NL", station.getCountry());
}

    @Test
    void testConstructorInvalidInput() {
        assertThrows(AssertionError.class, () -> new Station(-1, "code", "name", "NL", "type", 50.0, 5.0));
        assertThrows(AssertionError.class, () -> new Station(1, "  ", "name", "NL", "type", 50.0, 5.0));

    }


    @Test
    void testConvertToList() {
        MyList<Station> myList = new DoublyLinkedList<>();
        Station station1 = new Station(1, "code1", "name1", "NL", "type1", 50.0, 5.0);
        Station station2 = new Station(2, "code2", "name2", "NL", "type2", 51.0, 6.0);
        myList.add(station1);
        myList.add(station2);

        ArrayList<Station> arrayList = convertToList(myList);
        assertEquals(Arrays.asList(station1, station2), arrayList);
    }

    @Test
    public void testCompareTo() {
        Station station1 = new Station(1, "AMS", "Amsterdam Central", "NL", "Main", 52.379, 4.899);
        Station station2 = new Station(2, "RTD", "Rotterdam Central", "NL", "Main", 51.922, 4.479);

        assertTrue(station1.compareTo(station2) < 0); // AMS should come before RTD
        assertTrue(station2.compareTo(station1) > 0); // RTD should come after AMS
        assertEquals(0, station1.compareTo(station1)); // A station should be equal to itself
    }



    @Test
    public void testToString() {
        Station station = new Station(1, "AMS", "Amsterdam Central", "NL", "Main", 52.379, 4.899);
        String expectedString = "Station{" +
                "id=1, name='Amsterdam Central', code='AMS', uic='null', nameShort='null', " +
                "nameMedium='null', nameLong='null', slug='null', country='NL', type='Main', " +
                "geo_lat=52.379, geo_long=4.899}";

        assertEquals(expectedString, station.toString());
    }
}


