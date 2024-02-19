package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTrack {

    private Track track;

    @BeforeEach
    void setUp() {
        track = new Track("ac", "ashd", 2, 2, 30);
    }

    @Test
    void givenValidTrackDataShouldCreateTrackObject() {
        assertEquals("ac", track.getCode());
        assertEquals("ashd", track.getNextCode());
        assertEquals(2, track.getDistanceFrom());
        assertEquals(2, track.getDistanceTo());
        assertEquals(30, track.getTypeOfTrack());
    }

    @Test
    void givenCSVFileWithMultipleTracksShouldReadTracks() {
        List<Track> tracks = Track.readFromCSV("resources/tracks.csv");
        assertEquals(1438, tracks.size());
    }

    @Test
    void givenValidTrackDataToStringShouldReturnFormattedString() {
        String expected = "Track{code='ac', nextCode='ashd', distanceFrom=2, distanceTo=2, typeOfTrack=30}";
        assertEquals(expected, track.toString());
    }


    @Test
    void givenTwoDifferentTracksShouldNotBeEqual() {
        Track differentTrack = new Track("ac", "different", 2, 2, 30);
        assertNotEquals(track, differentTrack);
    }
}



