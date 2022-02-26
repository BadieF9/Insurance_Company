
import java.io.Serializable;
import java.util.Scanner;

public class ComprehensiveInsurancePolicy extends InsurancePolicy implements Serializable {

    public static Scanner scanner = new Scanner(System.in);

    protected int driverAge;
    protected int level;

    public ComprehensiveInsurancePolicy(int id, String policyHolderName, Car car, int numberOfClaims, int driverAge, int level, MyDate date) throws PolicyHolderNameException {
        super(id, policyHolderName, car, numberOfClaims, date);
        this.driverAge = driverAge;
        this.level = level;
    }

    public ComprehensiveInsurancePolicy(ComprehensiveInsurancePolicy comprehensiveInsurancePolicy) {
        super(comprehensiveInsurancePolicy);
        this.driverAge = comprehensiveInsurancePolicy.driverAge;
        this.level = comprehensiveInsurancePolicy.level;
    }

    public String toDilimatedString() {
        return "policy,cip," + this.id + "," + this.policyHolderName + "," + this.car.toDilimatedString()
                + ","+ this.numberOfClaims + ","+ this.driverAge + "," + this.level
                + "," + this.expiryDate.toDilimatedString()  ;
    }

    @Override
    public String toString() {
        return super.toString() + ", DriverAge: " + this.driverAge + ", Level: " + this.level;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("DriverAge: " + this.driverAge + ", Level: " + this.level);
    }

    public double calcPayment(int flatRate) {
        if(this.driverAge < 30) {
            return car.getPrice()/50 + numberOfClaims * 200 + flatRate + (30 - this.driverAge) * 50;
        } else {
            return car.getPrice()/50 + numberOfClaims * 200 + flatRate;
        }
    }

    public static InsurancePolicy createPolicy() {
        System.out.println("Please enter the Policy ID:");
        int id = scanner.nextInt();
        System.out.println("Please enter the Policy Holder Name:");
        String holderName = scanner.nextLine();

        System.out.println("Please enter the Number Of Claims:");
        int numberOfClaims = scanner.nextInt();
        System.out.println("Please enter the Driver Age:");
        int driverAge = scanner.nextInt();
        System.out.println("Please enter the Level:");
        int level = scanner.nextInt();
        System.out.println("Please enter the Expiration Date:");
        MyDate date = MyDate.createDate();
        System.out.println("Now please enter the car informations:");
        Car car = Car.createCar();
        try {
            return new ComprehensiveInsurancePolicy(id, holderName, car, numberOfClaims, driverAge, level, date);
        } catch (PolicyHolderNameException e) {
            System.out.println(e);
            return null;
        }
    }
    
    public int getDriverAge() {
        return this.driverAge;
    }
    
    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }

}
