import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class CarSharingSystem {
    private static CarSharingSystem instance;
    private final ArrayList<Station> stations;
    private final ArrayList<Booking> bookings;


    private CarSharingSystem() {
        stations = new ArrayList<>();
        bookings = new ArrayList<>();
        instance = this;

    }

    public static CarSharingSystem getInstance() {
        if (instance == null)
            instance = new CarSharingSystem();
        return instance;
    }

    // Implementieren Sie die Methoden für die verschiedenen Befehle (station, add, remove, list-stations, usw.)

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var carSharingSystem = getInstance();
        String input = null;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("quit")) {
            if (input.startsWith("station")) {
                var station = new Station(input.split(" ")[1]);
                carSharingSystem.stations.add(station);
                System.out.println("OK: added " + input.split(" ")[1] + " identified by " + station.getStationID());
            } else if (input.startsWith("add")) {
                input = input.replace("add ", "");
                var fahrzeugID = Integer.parseInt(input.split(";")[0]);
                var stationsID = Integer.parseInt(input.split(";")[1]);
                var category = Category.valueOf(input.split(";")[2].toUpperCase());
                var car = new Car(fahrzeugID, category);
                var station = Station.getStationsByID(stationsID);
                station.getCars().add(car);
                var number = String.valueOf(car.getCarNumber());
                while (number.length() < 3) {
                    number = "0" + number;
                }
                System.out.println("OK: added " + number + " to " + station.getStationName() + " which has " + station.getCars().size() + " cars");
            } else if (input.startsWith("remove")) {

                var fahrzeugID = Integer.parseInt(input.split(" ")[1]);
                //find the station with this car and than remove it from the station
                var found = false;
                for (var station : carSharingSystem.getStations()) {
                    for (var car : station.getCars()) {
                        if (car.getCarNumber() == fahrzeugID) {
                            var number = String.valueOf(car.getCarNumber());
                            while (number.length() < 3) {
                                number = "0" + number;
                            }
                            station.getCars().remove(car);
                            System.out.println("OK: removed " + number);
                            found = true;
                            break;
                        }

                    }
                    if (found) {
                        break;
                    }
                }
            } else if (input.startsWith("list-stations")) {
                var stations = new ArrayList<>(carSharingSystem.getStations());
                stations.sort(Comparator.comparingInt(Station::getStationID));
                for (var station : stations) {
                    System.out.println(station.getStationID() + ";" + station.getStationName() + ";" + station.getCars().size());
                }
            } else if (input.startsWith("list-cars")) {
                var stationsNummer = Integer.parseInt(input.split(" ")[1]);
                var station = Station.getStationsByID(stationsNummer);
                var cars = new ArrayList<>(station.getCars());
                cars.sort(Comparator.comparingInt(Car::getCarNumber));

                //sort the cars based on their car number
                for (var car : cars) {

                    //add "0" infront of the car.getCarNumber() until is has 3 digits
                    var number = String.valueOf(car.getCarNumber());
                    while (number.length() < 3) {
                        number = "0" + number;
                    }

                    System.out.println(number + ";" + station.getStationName() + ";" + station.getStationID() + ";" + car.getCategory().name().toLowerCase());
                }
            } else if (input.startsWith("book")) {
                input = input.replace("book ", "");
                var fahrzeugID = Integer.parseInt(input.split(";")[0]);
                var vorname = input.split(";")[1];
                var nachname = input.split(";")[2];
                var datum = input.split(";")[3];
                //convert datum into a date with datum beeing the format YYYY-MM-TT
                var date = LocalDate.of(Integer.parseInt(datum.split("-")[0]), Integer.parseInt(datum.split("-")[1]), Integer.parseInt(datum.split("-")[2]));
                var uhrzeit = input.split(";")[4];
                //create a localtime object with the uhrzeit in a 24h format and the format HH:MM
                var time = LocalTime.of(Integer.parseInt(uhrzeit.split(":")[0]), Integer.parseInt(uhrzeit.split(":")[1]));

                var dauer = Integer.parseInt(input.split(";")[5]);

                var car = Car.getCarByCarNumber(fahrzeugID);
                var customer = Customer.findOrAddCustomer(vorname, nachname);
                //check if the car is available
                if (!car.isAvailable(date, time, dauer)) {
                    continue;
                }

                var booking = new Booking(car, customer, date, time, dauer);
                carSharingSystem.bookings.add(booking);

                assert car != null;
                var costs = car.getCategory().getPrice() * dauer;
                System.out.println("OK: booked " + booking.getBookingNumber() + ";" + customer.getCustomerNumber() + " for " + costs);


            } else if (input.startsWith("check-available")) {
                input = input.replace("check-available ", "");
                var stationsID = Integer.parseInt(input.split(";")[0]);
                var datum = input.split(";")[1];
                var uhrzeit = input.split(";")[2];
                //create a localtime object with the uhrzeit in a 24h format and the format HH:MM
                var time = LocalTime.of(Integer.parseInt(uhrzeit.split(":")[0]), Integer.parseInt(uhrzeit.split(":")[1]));

                var dauer = Integer.parseInt(input.split(";")[3]);

                var date = LocalDate.of(Integer.parseInt(datum.split("-")[0]), Integer.parseInt(datum.split("-")[1]), Integer.parseInt(datum.split("-")[2]));
                var station = Station.getStationsByID(stationsID);
                var availableCars = station.getAvailableCars(date, time, dauer);

                //sort the available cars based on their car number ascending
                availableCars.sort(Comparator.comparingInt(Car::getCarNumber));

                for (var car : availableCars) {
                    var number = String.valueOf(car.getCarNumber());
                    while (number.length() < 3) {
                        number = "0" + number;
                    }
                    var price = String.valueOf(car.getCategory().getPrice());
                    price = price + "0";
                    System.out.println(number + ";" + car.getCategory().name().toLowerCase() + ";" + price);
                }


            } else if (input.startsWith("list-bookings")) {

                var bookings = new ArrayList<>(carSharingSystem.bookings);
                //sort the bookings ascending based on the customer number and than desc based on the booking number
                bookings.sort(Comparator.comparingInt(Booking::getCustomerNumber).thenComparingInt(Booking::getBookingNumber));

            } else if (input.startsWith("bill")) {
                input = input.replace("bill ", "");
                var kundennummer = Integer.parseInt(input.split(";")[0]);
                var year = Integer.parseInt(input.split(";")[1]);
                //find all bills with that customer number and year
                var bills = Booking.getBillsByCustomerNumberAndYear(kundennummer, year);
                //sort the bills ascending based on the booking number
                bills.sort(Comparator.comparingInt(Booking::getBookingNumber));
                var total = 0;
                for (var bill : bills) {
                    var price = bill.getCar().getCategory().getPrice() * bill.getDuration();
                    total += price;
                    System.out.println(bill.getCustomer().getCustomerNumber() + ";" + bill.getCar().getCarNumber() + ";" + bill.getBookingNumber() + ";" + bill.getDate() + ";" + bill.getTime() + ";" + bill.getDuration() + ";" + price);
                }
                var totalString = String.valueOf(total);
                if (totalString.contains(".")) {
                    while (totalString.split(".")[1].length() != 2) {
                        totalString += "0";

                    }
                } else {
                    totalString += ".00";
                }

                System.out.println("Sum: " + totalString);
            }
        }


    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }
}
