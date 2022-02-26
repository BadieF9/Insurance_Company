import java.io.Serializable;
import java.util.Scanner;

public class ThirdPartyInsurancePolicy extends InsurancePolicy implements Serializable {

    public static Scanner scanner = new Scanner(System.in);

    protected String comments;

    public ThirdPartyInsurancePolicy(int id, String policyHolderName, Car car, int numberOfClaims, String comments, MyDate date) throws PolicyHolderNameException {
        super( id, policyHolderName, car, numberOfClaims, date);
        this.comments = comments;
    }

    public ThirdPartyInsurancePolicy(ThirdPartyInsurancePolicy thirdPartyInsurancePolicy) {
        super(thirdPartyInsurancePolicy);
        this.comments = thirdPartyInsurancePolicy.comments;
    }

    public String toDilimatedString() {
        return "policy,tpip," + this.id + "," + this.policyHolderName + "," + this.car.toDilimatedString() + ","
                + this.numberOfClaims + "," + this.comments + ","  + this.expiryDate.toDilimatedString() ;
    }

    @Override
    public String toString() {
        return super.toString() + ", Comments: " + this.comments;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("Comments: " + this.comments);
    }

    public double calcPayment(int flatRate) {
        return car.getPrice()/100 + numberOfClaims * 200 + flatRate;
    }

    public static InsurancePolicy createPolicy() {
        System.out.println("Please enter the Policy ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the Policy Holder Name:");
        String holderName = scanner.nextLine();
        System.out.println("Please enter the Number Of Claims:");
        int numberOfClaims = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the Comment:");
        String comment = scanner.nextLine();
        System.out.println("Please enter the Expiration Date:");
        MyDate date = MyDate.createDate();
        System.out.println("Now please enter the car informations:");
        Car car = Car.createCar();
        try {
            return new ThirdPartyInsurancePolicy(id, holderName, car, numberOfClaims, comment, date);
        } catch (PolicyHolderNameException e) {
            System.out.println(e);
            return null;
        }
    }
    
    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }

}
