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

    private static void station(CarSharingSystem carSharingSystem, String input) {
        var station = new Station(input.split(" ")[1]);
        carSharingSystem.stations.add(station);
        System.out.println("OK: added " + input.split(" ")[1] + " identified by " + station.getStationID());

    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var carSharingSystem = getInstance();
        String input = null;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("quit")) {
            if (input.startsWith("station")) {
                station(carSharingSystem, input);
            } else if (input.startsWith("add")) {
                add(input, carSharingSystem);
            } else if (input.startsWith("remove")) {

                remove(input, carSharingSystem);
            } else if (input.startsWith("list-stations")) {
                listStations(carSharingSystem, input);
            } else if (input.startsWith("list-cars")) {
                listCars(carSharingSystem, input);
            } else if (input.startsWith("book")) {
                book(carSharingSystem, input);
            } else if (input.startsWith("check-available")) {
                checkAvailable(carSharingSystem, input);

            } else if (input.startsWith("list-bookings")) {
                listBookings(carSharingSystem);

            } else if (input.startsWith("bill")) {
                bill(carSharingSystem, input);

            }


        }
    }

    private static void listBookings(CarSharingSystem carSharingSystem) {

        var bookings = new ArrayList<>(carSharingSystem.bookings);
        //sort the bookings ascending based on the customer number and than desc based on the booking number
        bookings.sort(Comparator.comparingInt(Booking::getCustomerNumber).thenComparingInt(Booking::getBookingNumber));

        for (var booking : bookings) {

            var costs = booking.getCar().getCategory().getPrice() * booking.getDuration();
            var totalString = String.valueOf(costs);
            if (totalString.contains(".")) {
                while (totalString.split("\\.")[1].length() != 2) {
                    if (totalString.split("\\.")[1].length() > 2) totalString = totalString.substring(0, totalString.length() - 1);
                    else totalString += "0";

                }
            } else {
                totalString += ".00";
            }
            System.out.println(booking.getCustomerNumber() + ";" + booking.getCar().getCarNumber() + ";" + booking.getBookingNumber() + ";" + booking.getDate() + ";" + booking.getTime() + ";" + booking.getDuration() + ";" + totalString);
        }
    }

    private static void checkAvailable(CarSharingSystem carSharingSystem, String input) {
        input = input.replace("check-available ", "");
        var stationsID = Integer.parseInt(input.split(";")[0]);
        var datum = input.split(";")[1];
        var uhrzeit = input.split(";")[2];
        //create a localtime object with the uhrzeit in a 24h format and the format HH:MM
        var time = LocalTime.of(Integer.parseInt(uhrzeit.split(":")[0]), Integer.parseInt(uhrzeit.split(":")[1]));

        var dauer = Integer.parseInt(input.split(";")[3]);

        var a = Integer.parseInt(datum.split("-")[0]);
        var b = Integer.parseInt(datum.split("-")[2]);
        var date = LocalDate.of(a, Integer.parseInt(datum.split("-")[1]), b);
        var station = Station.getStationsByID(stationsID);
        var availableCars = station.getAvailableCars(date, time, dauer);

        //sort the available cars based on their car number ascending
        availableCars.sort(Comparator.comparingInt(Car::getCarNumber));

        for (var car : availableCars) {
            var number = String.valueOf(car.getCarNumber());
            while (number.length() < 3) {
                number = "0" + number;
            }
            var totalString = String.valueOf(car.getCategory().getPrice());
            if (totalString.contains(".")) {
                while (totalString.split("\\.")[1].length() != 2) {
                    totalString += "0";

                }
            } else {
                totalString += ".00";
            }
            System.out.println(number + ";" + car.getCategory().name().toLowerCase() + ";" + totalString);
        }

    }

    private static void book(CarSharingSystem carSharingSystem, String input) {
        input = input.replace("book ", "");
        var fahrzeugID = Integer.parseInt(input.split(";")[0]);
        var vorname = input.split(";")[1];
        var nachname = input.split(";")[2];
        var datum = input.split(";")[3];
        //convert datum into a date with datum beeing the format YYYY-MM-TT
        var a = Integer.parseInt(datum.split("-")[0]);
        var b = Integer.parseInt(datum.split("-")[1]);
        var c = Integer.parseInt(datum.split("-")[2]);
        var date = LocalDate.of(a, b, c);
        var uhrzeit = input.split(";")[4];
        //create a localtime object with the uhrzeit in a 24h format and the format HH:MM
        var time = LocalTime.of(Integer.parseInt(uhrzeit.split(":")[0]), Integer.parseInt(uhrzeit.split(":")[1]));

        var dauer = Integer.parseInt(input.split(";")[5]);

        var car = Car.getCarByCarNumber(fahrzeugID);
        //check if the car is available
        assert car != null;
        if (!car.isAvailable(date, time, dauer)) {
            System.out.println("ERROR: Cannot book due to overlapping booking!");
            return;
        }
        var customer = Customer.findOrAddCustomer(vorname, nachname);

        var booking = new Booking(car, customer, date, time, dauer);
        carSharingSystem.bookings.add(booking);

        assert car != null;
        var costs = car.getCategory().getPrice() * dauer;
        var totalString = String.valueOf(costs);
        if (totalString.contains(".")) {
            while (totalString.split("\\.")[1].length() != 2) {
                if (totalString.split("\\.")[1].length() > 2)
                    totalString = totalString.substring(0, totalString.length() - 1);
                else
                    totalString += "0";

            }
        } else {
            totalString += ".00";
        }

        var x = ";" + customer.getCustomerNumber() + " for " + totalString;
        System.out.println("OK: booked " + booking.getBookingNumber() + x);


    }

    private static void listCars(CarSharingSystem carSharingSystem, String input) {
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
            var l = number + ";" + station.getStationName() + ";" + station.getStationID();
            var b = l + ";" + car.getCategory().name().toLowerCase();
            System.out.println(b);
        }
    }

    private static void listStations(CarSharingSystem carSharingSystem, String input) {
        var stations = new ArrayList<>(carSharingSystem.getStations());
        stations.sort(Comparator.comparingInt(Station::getStationID));
        for (var station : stations) {
            var l = station.getStationID() + ";" + station.getStationName() + ";" + station.getCars().size();
            System.out.println(l);
        }
    }

    private static void remove(String input, CarSharingSystem carSharingSystem) {
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

    }

    private static void add(String input, CarSharingSystem carSharingSystem) {
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
        var l = "OK: added " + number + " to " + station.getStationName();
        var ln = l + " which has " + station.getCars().size() + " cars";
        System.out.println(ln);

    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }


    private static void bill(CarSharingSystem carSharingSystem, String input) {
        input = input.replace("bill ", "");
        var kundennummer = Integer.parseInt(input.split(";")[0]);
        var year = Integer.parseInt(input.split(";")[1]);
        //find all bills with that customer number and year
        var bills = Booking.getBillsByCustomerNumberAndYear(kundennummer, year);
        //sort the bills descending based on the booking number
        bills.sort(Comparator.comparingInt(Booking::getBookingNumber).reversed());

        var total = 0;
        for (var bill : bills) {
            var price = bill.getCar().getCategory().getPrice() * bill.getDuration();
            total += price;
            var totalString = String.valueOf(price);
            if (totalString.contains(".")) {
                while (totalString.split("\\.")[1].length() != 2) {
                    if (totalString.length() > 2)
                        totalString = totalString.substring(0, totalString.length() - 1);
                    else
                        totalString += "0";

                }
            } else {
                totalString += ".00";
            }
            var number = String.valueOf(bill.getCar().getCarNumber());
            while (number.length() < 3) {
                number = "0" + number;
            }
            var l = bill.getCustomer().getCustomerNumber() + ";" + number + ";";
            var a = l + bill.getBookingNumber() + ";" + bill.getDate() + ";" + bill.getTime() + ";";
            var x = a + bill.getDuration() + ";" + totalString;
            System.out.println(x);
        }
        var totalString = String.valueOf(total);
        if (totalString.contains(".")) {
            while (totalString.split("\\.")[1].length() != 2) {
                totalString += "0";

            }
        } else {
            totalString += ".00";
        }

        System.out.println("Sum: " + totalString);
    }
}
