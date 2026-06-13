package myjastip.location;
import java.util.Random;

public class Location {
    private String locationName;
    private double latitude;
    private double longitude;

    public Location() {
        Random rand = new Random();
        this.latitude = -90 + rand.nextFloat() * 180;
        this.longitude = -180 + rand.nextFloat() * 360;
        this.locationName = "Random Location";
    }
    public Location(String location, double latitude, double longitude) {
        this.locationName = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

    public void setCoordinate(double latitude, double longitude) {
        try {
            if (latitude < -90 || latitude > 90) {
                throw new IllegalArgumentException("Latitude tidak valid");
            }

            if (longitude < -180 || longitude > 180) {
                throw new IllegalArgumentException("Longitude tidak valid");
            }


        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
