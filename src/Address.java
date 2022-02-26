import java.io.Serializable;
import java.util.Scanner;

public class Address implements Cloneable, Comparable<Address>, Serializable {

    public static Scanner scanner = new Scanner(System.in);

    protected int streetNum;
    protected String street;
    protected String suburb;
    protected String city;

    public Address(int streetNum, String street, String suburb, String city) {
        this.streetNum = streetNum;
        this.street = street;
        this.suburb = suburb;
        this.city = city;
    }

    public Address(Address address) {
        this.streetNum = address.streetNum;
        this.street = address.street;
        this.suburb = address.suburb;
        this.city = address.city;
    }

    public String toDilimatedString() {
        return this.streetNum + "," + this.street + "," + this.suburb + "," + this.city;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

    @Override
    public int compareTo(Address address) {
        for (int i = 0; i < Math.min(this.city.length(), address.city.length()); i++) {
            int city1 = (int) this.city.charAt(i);
            int city2 = (int) address.city.charAt(i);

            if (city1 > city2) {
                return 1;
            } else if(city1 < city2) {
                return -1;
            }
        }
        return 0;
    }

    public String toString() {
        return "City: " + this.city + ", Street: " + this.street + ", Street Number: " + this.streetNum + ", Suburb: "
                + this.suburb + ", ";
    }

    public static Address createAddress() {
        System.out.println("Please enter the new Street number:");
        int streetNum = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the new Street:");
        String street = scanner.nextLine();
        System.out.println("Please enter the new Suburb:");
        String suburb = scanner.nextLine();
        System.out.println("Please enter the new City:");
        String city = scanner.nextLine();

        return new Address(streetNum, street, suburb, city);
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
