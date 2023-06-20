public class Customer {

    private static int totalCustomerNumber = 1;

    private final String firstName;
    private final String lastName;

    private final int customerNumber;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerNumber = getTotalCustomerNumber();
        setTotalCustomerNumber(getTotalCustomerNumber() + 1);
    }

    public static Customer findOrAddCustomer(String vorname, String nachname) {
        var carSharingSystem = CarSharingSystem.getInstance();
        for (var customer : carSharingSystem.getBookings()) {
            if (customer.getCustomer().getFirstName().equals(vorname) && customer.getCustomer().getLastName().equals(nachname)) {
                return customer.getCustomer();
            }

        }
        return new Customer(vorname, nachname);
    }

    public static int getTotalCustomerNumber() {
        return totalCustomerNumber;
    }

    public static void setTotalCustomerNumber(int totalCustomerNumber) {
        Customer.totalCustomerNumber = totalCustomerNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public String printName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
