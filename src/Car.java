import java.io.Serializable;
import java.util.Scanner;

public class Car implements Cloneable, Serializable {

    public static Scanner scanner = new Scanner(System.in);

    private String model;
    private CarType type;
    private int manufacturingYear;
    private double price;

    public Car(String model, CarType type, int manufacturingYear, double price) {
       this.model = model;
        this.type = type;
        this.manufacturingYear = manufacturingYear;
        this.price = price;
    }

    public Car(Car car) {
        this.model = car.model;
        this.type = car.type;
        this.manufacturingYear = car.manufacturingYear;
        this.price = car.price;
    }

    public String toDilimatedString() {
        return this.model + "," + this.type + "," + this.manufacturingYear + "," + this.price;
    }

    public String toString() {
        return "Model: " + this.model + " Type: " + this.type + " ManufacturingYear: "
                + this.manufacturingYear + " Price: " + this.price;
    }

    @Override
    public Car clone() throws CloneNotSupportedException {
        return (Car) super.clone();
    }

    public void print() {
        System.out.println("Model: " + this.model + " Type: " + type + " ManufacturingYear: "
                + this.manufacturingYear + " Price: " + this.price);
    }

    public static Car createCar() {
        System.out.println("Please enter the Car Model:");
        String carModel = scanner.nextLine();
        System.out.println("Please enter the Manufacturing Year:");
        int manufacturingYear = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the Car Price:");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please select the Car Type:");
        boolean rightChoice = false;
        CarType carType = CarType.SED;
        while(!rightChoice) {
            System.out.println("1) SUV \n2) SED \n3) LUX \n4) HATCH");
            int carTypeNum = scanner.nextInt();
            scanner.nextLine();
            switch (carTypeNum) {
                case 1:
                    rightChoice = true;
                    carType = CarType.SUV;
                    break;

                case 2:
                    rightChoice = true;
                    carType = CarType.SED;
                    break;

                case 3:
                    rightChoice = true;
                    carType = CarType.LUX;
                    break;

                case 4:
                    rightChoice = true;
                    carType = CarType.HATCH;
                    break;

                default:
                    System.out.println("Wrong Choice!");
            }
        }
        return new Car(carModel, carType, manufacturingYear, price);
    }

    public void priceRise(double risePercent) {
        this.price = this.price * (1 + risePercent);
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getType() {
        return type;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }





}