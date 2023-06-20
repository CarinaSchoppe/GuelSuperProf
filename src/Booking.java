import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

class Booking {

    private static int totalBookingNumber = 1;
    private final Car car;
    private final Customer customer;
    private final int bookingNumber;
    private final LocalDate date;

    //time object with the format hh:mm 24h format
    private final LocalTime time;
    private final int duration;


    public Booking(Car car, Customer customer, LocalDate date, LocalTime time, int duration) {
        this.car = car;
        this.customer = customer;
        this.bookingNumber = getTotalBookingNumber();
        setTotalBookingNumber(getTotalBookingNumber() + 1);
        this.date = date;
        this.time = time;
        this.duration = duration;
    }


    public static int getTotalBookingNumber() {
        return totalBookingNumber;
    }

    public static void setTotalBookingNumber(int totalBookingNumber) {
        Booking.totalBookingNumber = totalBookingNumber;
    }

    public static ArrayList<Booking> getBillsByCustomerNumberAndYear(int customerID, int year) {
        var carSharingSystem = CarSharingSystem.getInstance();
        var bills = new ArrayList<Booking>();
        for (var booking : carSharingSystem.getBookings()) {
            if (booking.getCustomerNumber() == customerID && booking.getDate().getYear() == year) {
                bills.add(booking);
            }
        }
        return bills;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public int getCustomerNumber() {
        return customer.getCustomerNumber();
    }
}
