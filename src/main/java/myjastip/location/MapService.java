package myjastip.location;

import myjastip.location.Location;

public class MapService {
    private String mapProvider;

    public MapService(String mapProvider) {
        this.mapProvider = mapProvider;
    }

    public void tampilkanPeta(Location lokasi) {
        try {
            if (lokasi == null) {
                throw new Exception("Lokasi tidak tersedia");
            }

            System.out.println("Provider: " + mapProvider);
            System.out.println("Lokasi: " + lokasi.getLocation());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public double hitungJarak(Location lok1, Location lok2) {
        try {
            if (lok1 == null || lok2 == null) {
                throw new Exception("Lokasi tidak boleh kosong");
            }

            double lat = lok1.getLatitude() - lok2.getLatitude();
            double lon = lok1.getLongitude() - lok2.getLongitude();

            return Math.sqrt((lat * lat) + (lon * lon));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }

    public String getRoute(Location lok1, Location lok2) {
        try {
            if (lok1 == null || lok2 == null) {
                throw new Exception("Lokasi tidak valid");
            }

            return "Rute dari "
                    + lok1.getLocation()
                    + " ke "
                    + lok2.getLocation();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    public String getRoute(Location lok1, Location lok2, String transportasi) {
        try {
            if (lok1 == null || lok2 == null) {
                throw new Exception("Lokasi tidak valid");
            }

            return "Rute menggunakan "
                    + transportasi
                    + " dari "
                    + lok1.getLocation()
                    + " ke "
                    + lok2.getLocation();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getMapProvider() {
        return mapProvider;
    }
}