package myjastip.location;

public class Location {
    private String location;
    private double latitude;
    private double longtitude;
    
    public Location(String location, double latitude, double longtitude) {
        this.location = location;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getLocation() {
        return "latitude: " + latitude + ", longtitude: " + longtitude;
    }
}
    
    

    
