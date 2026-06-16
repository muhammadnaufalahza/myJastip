package myjastip.location;

public class Location {
    private String locationName;
    private double latitude;
    private double longitude;

    public Location(String location, double latitude, double longitude) {
        this.locationName = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return locationName + " - (" + latitude + ", " + longitude + ")";
    }

    public void setCoordinate(double latitude, double longitude) {
        try {
            if ((latitude < -90 || latitude > 90) && (longitude < -180 || longitude > 180)) {
                throw new InvalidCoordinateException("Koordinat tidak valid!");
            }
            if (latitude < -90 || latitude > 90) {
                throw new InvalidCoordinateException("Latitude tidak valid!");
            }
            if (longitude < -180 || longitude > 180) {
                throw new InvalidCoordinateException("Longitude tidak valid!");
            }

            this.latitude = latitude;
            this.longitude = longitude;

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

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
