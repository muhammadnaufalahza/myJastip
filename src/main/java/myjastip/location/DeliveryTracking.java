package myjastip.location;

        
public class DeliveryTracking { 
    private String trackingId;
    private String Status;
    private Location currentLocation;

    public DeliveryTracking(String trackingId, String Status, Location currentLocation) {
        this.trackingId = trackingId;
        this.Status = Status;
        this.currentLocation = currentLocation;
    }
    
    public void UpdateLokasi(Location currentLocation){
    
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
    public void kirimLokasi() {
        System.out.println(currentLocation.getLocation());
    }
}


