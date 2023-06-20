import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class Car {
    private final int carNumber;
    private final Category category;

    public Car(int carNumber, Category category) {
        if (carNumber < 0 || carNumber > 1000) {
            throw new IllegalArgumentException("Car number must be positive");
        }
        this.carNumber = carNumber;
        this.category = category;
    }

    public static Car getCarByCarNumber(int fahrzeugID) {
        for (var station : CarSharingSystem.getInstance().getStations()) {
            for (var car : station.getCars()) {
                if (car.getCarNumber() == fahrzeugID) {
                    return car;
                }
            }

        }
        return null;
    }

    public int getCarNumber() {
        return carNumber;
    }


    public Category getCategory() {
        return category;
    }

    public boolean isAvailable(LocalDate date, LocalTime uhrzeit, int dauer) {
        var bookings = CarSharingSystem.getInstance().getBookings();
        //get all bookings with the cars id


        var nowTime = LocalDateTime.of(date, uhrzeit);

        for (var booking : bookings) {
            if (booking.getCar().getCarNumber() == this.getCarNumber()) {
                var terminationTime = LocalDateTime.of(booking.getDate(), booking.getTime());
                //check if terminationTime is before nowtime + dauer
                if (terminationTime.isBefore(nowTime.plusHours(dauer))) {
                    return false;
                }
                //check if terminationTime+booking.duration is after nowtime
                if (nowTime.isBefore(terminationTime.plusHours(booking.getDuration()))) {
                    return false;
                }

            }
        }

        return true;

    }
}
