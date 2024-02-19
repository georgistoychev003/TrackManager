package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Track {

    private String code;
    private String nextCode;
    private int distanceFrom;
    private int distanceTo;
    private int typeOfTrack;

    // Constructor
    public Track(String code, String nextCode, int distanceFrom, int distanceTo, int typeOfTrack) {
        this.code = code;
        this.nextCode = nextCode;
        this.distanceFrom = distanceFrom;
        this.distanceTo = distanceTo;
        this.typeOfTrack = typeOfTrack;
    }




    public static List<Track> readFromCSV(String filename) {
        List<Track> tracks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");

                // Regular expression validation
                boolean isValid = line[0].matches("^[a-zA-Z0-9]+$") && // Code
                        line[1].matches("^[a-zA-Z0-9]+$") && // NextCode
                        line[2].matches("^\\d+$") && // DistanceFrom
                        line[3].matches("^\\d+$") && // DistanceTo
                        line[4].matches("^\\d+$");  // TypeOfTrack

                if (isValid) {
                    Track track = new Track(line[0], line[1],
                            Integer.parseInt(line[2]),
                            Integer.parseInt(line[3]),
                            Integer.parseInt(line[4]));
                    tracks.add(track);
                } else {
                    System.out.println("Invalid track record: " + String.join(", ", line));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    public String getCode() {
        return code;
    }

    public String getNextCode() {
        return nextCode;
    }

    public int getDistanceFrom() {
        return distanceFrom;
    }

    public int getDistanceTo() {
        return distanceTo;
    }

    public int getTypeOfTrack() {
        return typeOfTrack;
    }

    @Override
    public String toString() {
        return "Track{" +
                "code='" + code + '\'' +
                ", nextCode='" + nextCode + '\'' +
                ", distanceFrom=" + distanceFrom +
                ", distanceTo=" + distanceTo +
                ", typeOfTrack=" + typeOfTrack +
                '}';
    }
}
