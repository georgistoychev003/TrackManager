package model;

import lists.DoublyLinkedList;
import lists.MyList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Station implements Comparable<Station> {

    private final int id;
    private final String name;
    private String code;
    private String uic;
    private String nameShort;
    private String nameMedium;
    private String nameLong;
    private String slug;
    private String country;
    private String type;
    private double geo_lat;
    private double geo_long;

    // Constructor, the assertions are provided by our teacher Frederik Bonte during the first lectures
    public Station(int id, String code, String name, String country,
                   String type, double geo_lat, double geo_long) {
        assert id > 0 : "Please provide a non negative id.";
        this.id = id;
        assert code != null : "Please provide an actual code.";
        this.code = code.trim();
        assert !this.code.isBlank() : "Please provide an actual code.";
        assert name != null : "Please provide an actual name.";
        this.name = name.trim();
        assert !this.name.isBlank() : "Please provide an actual name.";
        assert country != null : "Please provide an actual country.";
        this.country = country.trim().toUpperCase();
        assert !this.country.isBlank() : "Please provide an actual country.";
        assert this.country.length() <= 3 : "Your country code seems too long.";
        assert type != null : "Please provide an actual type";
        this.type = type.trim();
        assert !this.type.isBlank() : "Please provide an actual type";
        assert geo_lat >= -90 && geo_lat <= 90 : "The latitude value" + geo_lat + "is out of range.";
        this.geo_lat = geo_lat;
        assert geo_long >= -180 && geo_long <= 180 : "The longitude value" + geo_long + "is out of range.";
        this.geo_long = geo_long;

    }

    public static MyList<Station> readFromCSV(String filename) {


        MyList<Station> stations = new DoublyLinkedList<Station>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                for (int i = 0; i < line.length; i++) {
                    line[i] = line[i].replace("\"", "").trim(); // Remove double quotes
                }

                // Validate using regex
                if (line[0].matches("[0-9]+") && // id
                        line[1].matches("\\w+(-\\w+)?") && // code
                        line[2].matches("[0-9]+") && // uic
                        line[6].matches("[a-z-]+") &&  // slug
                        line[7].matches("[A-Z]{1,2}") &&  // country
                        line[8].matches("[^,]+") &&  // type
                        line[9].matches("-?\\d+\\.\\d+") &&  // geo_lat
                        line[10].matches("-?\\d+\\.\\d+")) { // geo_long
                    Station station = new Station(Integer.parseInt(line[0]), line[1],
                            line[3], line[7],
                            line[8], Double.parseDouble(line[9]),
                            Double.parseDouble(line[10]));
                    stations.add(station);
                } else {
                    System.out.println("Invalid record: " + String.join(", ", line));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stations;
    }


    /*
    This method converts my custom list into the java ArrayList
     */
    public static ArrayList<Station> convertToList(MyList<Station> customList) {
        ArrayList<Station> list = new ArrayList<>();
        for (Station station : customList) {
            list.add(station);
        }
        return list;
    }


    //i need this only for the segment where i find the stations that fall within a rectangle i use
    // the station class within the minheap and it needs the stations to implement comparable to work
    @Override
    public int compareTo(Station other) {

        return this.code.compareTo(other.code);
    }


    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public double getGeoLat() {
        return geo_lat;
    }

    public double getGeoLng() {
        return geo_long;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", uic='" + uic + '\'' +
                ", nameShort='" + nameShort + '\'' +
                ", nameMedium='" + nameMedium + '\'' +
                ", nameLong='" + nameLong + '\'' +
                ", slug='" + slug + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", geo_lat=" + geo_lat +
                ", geo_long=" + geo_long +
                '}';
    }
}

