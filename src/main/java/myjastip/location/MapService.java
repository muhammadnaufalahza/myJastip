package myjastip.location;

public class MapService {
    private String mapProvider;

    public MapService(String mapProvider) {
        this.mapProvider = mapProvider;
    }

    public void showMap(Location location) {
        try {
            if (location == null) {
                throw new Exception("Lokasi tidak tersedia");
            }

            System.out.println("Provider: " + mapProvider);
            System.out.println("Lokasi: " + location.getLocationName());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public double calculateDistance(Location loc1, Location loc2) {
        try {
            if (loc1 == null || loc2 == null) {
                throw new Exception("Lokasi tidak boleh kosong");
            }

            double lat = loc1.getLatitude() - loc2.getLatitude();
            double lon = loc1.getLongitude() - loc2.getLongitude();

            return Math.sqrt((lat * lat) + (lon * lon));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public String getRoute(Location loc1, Location loc2) {
        try {
            if (loc1 == null || loc2 == null) {
                throw new Exception("Lokasi tidak valid");
            }

            return "Rute dari "
                    + loc1.getLocationName()
                    + " ke "
                    + loc2.getLocationName();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    public String getRoute(Location loc1, Location loc2, String transportasi) {
        try {
            if (loc1 == null || loc2 == null) {
                throw new Exception("Lokasi tidak valid");
            }

            return "Rute menggunakan "
                    + transportasi
                    + " dari "
                    + loc1.getLocationName()
                    + " ke "
                    + loc2.getLocationName();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getMapProvider() {
        return mapProvider;
    }
}