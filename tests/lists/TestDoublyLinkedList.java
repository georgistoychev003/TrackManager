package lists;

import model.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestDoublyLinkedList {
    private DoublyLinkedList<Station> list;

    @BeforeEach
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    @Test
    public void testAddSingleElementToListShouldIncreaseSize() {

        Station denHaag = new Station(1, "GVC", "Den Haag C", "NL", "megastation", 52.080276489258, 4.3249998092651);


        list.add(denHaag);


        assertEquals(1, list.length());
        assertEquals(denHaag, list.get(0));
    }

    @Test
    public void testAddMultipleElementsToListShouldIncreaseSize() {
        // Arrange
        Station station1 = new Station(1, "DV", "Deventer", "NL", "station", 52.2575, 6.1606);
        Station station2 = new Station(2, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705);
        Station station3 = new Station(3, "GVC", "Den Haag C", "NL", "megastation", 52.080276489258, 4.3249998092651);


        list.add(station1);
        list.add(station2);
        list.add(station3);


        assertEquals(3, list.length());
        assertEquals(station1, list.get(0));
        assertEquals(station2, list.get(1));
        assertEquals(station3, list.get(2));
    }

    @Test
    public void testRemoveElementFromListShouldDecreaseSize() {

        Station station1 = new Station(1, "GVC", "Den Haag C", "NL", "megastation", 52.080276489258, 4.3249998092651);
        list.add(station1);
        list.remove(station1);
        assertTrue(list.isEmpty());
    }


    @Test
    public void testListShouldMaintainInsertionOrder() {

        Station station1 = new Station(1, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705);
        Station station2 = new Station(2, "GVC", "Den Haag C", "NL", "megastation", 52.080276489258, 4.3249998092651);

        list.add(station1);
        list.add(station2);

        assertEquals(station1, list.get(0));
        assertEquals(station2, list.get(1));
    }

    @Test
    public void testClearShouldEmptyTheList() {

        Station station1 = new Station(1, "GVC", "Den Haag C", "NL", "megastation", 52.080276489258, 4.3249998092651);
        list.add(station1);
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void testSearchByName() {
        Station station1 = new Station(1, "DV", "Deventer", "NL", "station", 52.2575, 6.1606);
        Station station2 = new Station(2, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705);
        list.add(station1);
        list.add(station2);

        Station foundStation = list.searchByName("Deventer");
        assertNotNull(foundStation);
        assertEquals("DV", foundStation.getCode());

        Station notFoundStation = list.searchByName("NonExistent");
        assertNull(notFoundStation);
    }

    @Test
    void testContainsAll() {
        Station station1 = new Station(1, "DV", "Deventer", "NL", "station", 52.2575, 6.1606);
        Station station2 = new Station(2, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705);
        list.add(station1);
        list.add(station2);

        DoublyLinkedList<Station> otherList = new DoublyLinkedList<>();
        otherList.add(station1);
        otherList.add(station2);

        assertTrue(list.containsAll(otherList));
    }

    @Test
    void testContainsSome() {
        Station station1 = new Station(1, "DV", "Deventer", "NL", "station", 52.2575, 6.1606);
        Station station2 = new Station(2, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705);
        list.add(station1);

        DoublyLinkedList<Station> otherList = new DoublyLinkedList<>();
        otherList.add(station1);
        otherList.add(station2);

        assertTrue(list.containsSome(otherList));
    }

    @Test
    void testContains() {
        Station station1 = new Station(1, "DV", "Deventer", "NL", "station", 52.2575, 6.1606);
        list.add(station1);

        assertTrue(list.contains(station1));
        assertFalse(list.contains(new Station(2, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705)));
    }


    @Test
    void testElementDisconnection() {
        // Adding elements to the list
        Station station1 = new Station(1, "DV", "Deventer", "NL", "type1", 52.2575, 6.1606);
        Station station2 = new Station(2, "AMF", "Amersfoort", "NL", "type2", 52.1539, 5.3705);
        list.add(station1);
        list.add(station2);

        // Removing an element should disconnect it from the list
        assertTrue(list.remove(station1));

        // The list should no longer contain the removed element
        assertFalse(list.contains(station1));
        // The size of the list should be decremented
        assertEquals(1, list.length());
    }

    @Test
    void testClearList() {
        list.add(new Station(1, "DV", "Deventer", "NL", "type1", 52.2575, 6.1606));
        list.add(new Station(2, "AMF", "Amersfoort", "NL", "type2", 52.1539, 5.3705));

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.length());
    }


    @Test
    void testContainsSomeWithNoMatchingElements() {
        // Adding some elements to the main list
        list.add(new Station(1, "DV", "Deventer", "NL", "type1", 52.2575, 6.1606));
        list.add(new Station(2, "AMF", "Amersfoort", "NL", "type2", 52.1539, 5.3705));

        // Creating another list with elements not present in the main list
        DoublyLinkedList<Station> otherList = new DoublyLinkedList<>();
        otherList.add(new Station(3, "ABCD", "ABC", "NL", "station", 55.0, 5.0));
        otherList.add(new Station(4, "ABCDE", "ABCD", "NL", "station", 60.0, 6.0));

        // The main list should not contain any element from otherList
        assertFalse(list.containsSome(otherList));
    }

    @Test
    void testToString() {
        Station station1 = new Station(1, "DV", "Deventer", "NL", "station", 52.2575, 6.1606);
        Station station2 = new Station(2, "AMF", "Amersfoort", "NL", "station", 52.1539, 5.3705);

        list.add(station1);
        list.add(station2);

        String expected = "DoublyLinkedList {" + station1.toString() + ", " + station2.toString() + "}";
        assertEquals(expected, list.toString());
    }
}


