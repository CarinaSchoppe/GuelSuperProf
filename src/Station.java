import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Station {
    private static int totalStationID = 1;
    private final String stationName;
    private final Set<Car> cars;
    private final int stationID;


    public Station(String stationName) {
        this.stationName = stationName;
        this.cars = new HashSet<>();
        stationID = getTotalStationID();
        setTotalStationID(getTotalStationID() + 1);
    }

    public static Station getStationsByID(int stationID) {
        var carSharingSystem = CarSharingSystem.getInstance();
        for (var station : carSharingSystem.getStations()) {
            if (station.getStationID() == stationID) {
                return station;
            }
        }
        return null;
    }

    public static int getTotalStationID() {
        return totalStationID;
    }

    public static void setTotalStationID(int totalStationID) {
        Station.totalStationID = totalStationID;
    }

    public int getStationID() {
        return stationID;
    }

    public String getStationName() {
        return stationName;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public ArrayList<Car> getAvailableCars(LocalDate date, LocalTime uhrzeit, int dauer) {
        //check for all cars if they are available
        var availableCars = new ArrayList<Car>();
        for (var car : cars) {
            if (car.isAvailable(date, uhrzeit, dauer)) {
                availableCars.add(car);
            }
        }
        return availableCars;

    }

    // Implementieren Sie die Methoden für das Hinzufügen und Entfernen von Fahrzeugen
}
