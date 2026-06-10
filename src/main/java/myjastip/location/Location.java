package myjastip.location;

public class Location {
    private String location;
    private double latitude;
    private double longitude;

    public Location(String location, double latitude, double longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLocation() {
        return location + " (" + latitude + ", " + longitude + ")";
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
