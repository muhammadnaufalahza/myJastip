package myjastip.location;

public class DeliveryTracking {
    private String trackingId;
    private String status;
    private Location currentLocation;

    public DeliveryTracking(String trackingId, String status, Location currentLocation) {
        this.trackingId = trackingId;
        this.status = status;
        this.currentLocation = currentLocation;
    }

    public void updateLokasi(Location lokasi) {
        try {
            if (lokasi == null) {
                throw new IllegalArgumentException("Lokasi tidak tersedia");
            }

            this.currentLocation = lokasi;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public String getStatus() {
        return status;
    }

    public String kirimLokasi() {
        try {
            if (currentLocation == null) {
                throw new NullPointerException("Lokasi belum tersedia");
            }

            return currentLocation.toString();

        } catch (NullPointerException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getTrackingId() {
        return trackingId;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}