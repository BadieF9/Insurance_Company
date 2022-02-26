import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class Main {

    public static final double flatRate = 0.1;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws PolicyHolderNameException {
        // Creating company and adding users to it
        InsuranceCompany company = new InsuranceCompany("Sunflower", "badie", "badie123", (int) flatRate);
        Address address = new Address(23, "Aria", "Hor st.", "Shiraz");
        Car car = new Car("Mercedes", CarType.LUX, 2020, 1500);
        User user1 = new User("Badie", 1, "badie", "password1", address);
        InsurancePolicy insurance1 = new ThirdPartyInsurancePolicy( 1,"Sue Doe", car, 54, "This is Sue's insurance", new MyDate(2020, 2, 12));
        InsurancePolicy insurance2 = new ComprehensiveInsurancePolicy(2, "Shawn James",car, 65, 45, 1, new MyDate(1992, 2, 12));
        InsurancePolicy insurance3 = new ThirdPartyInsurancePolicy( 3, "Jack Wilson", car, 87, "This is Jack's insurance", new MyDate(1998, 2, 12));
        InsurancePolicy insurance4 = new ComprehensiveInsurancePolicy(4, "Sarah Mackinson",car, 98, 20, 3, new MyDate(2010, 2, 12));
        InsurancePolicy insurance5 = new ThirdPartyInsurancePolicy( 5, "Mark Twain", car, 155, "This is Jack's insurance", new MyDate(2005, 2, 12));
        InsurancePolicy insurance6 = new ComprehensiveInsurancePolicy(6, "Ben Smith",car, 457, 20, 3, new MyDate(2019, 2, 12));
        InsurancePolicy insurance7 = new ThirdPartyInsurancePolicy( 7, "Jade Roberts", car, 34, "This is Jack's insurance", new MyDate(2001, 2, 12));
        InsurancePolicy insurance8 = new ComprehensiveInsurancePolicy(8, "John Robinson",car, 76, 20, 3, new MyDate(2020, 2, 12));
        user1.addPolicy(insurance1);
        user1.addPolicy(insurance2);
        user1.addPolicy(insurance3);
        user1.addPolicy(insurance4);
        user1.addPolicy(insurance5);
        user1.addPolicy(insurance6);
        user1.addPolicy(insurance7);
        user1.addPolicy(insurance8);

        company.addUser(user1);
        company.addPolicy(1, insurance1);
        company.addPolicy(1, insurance2);
        company.addPolicy(1, insurance3);
        company.addPolicy(1, insurance4);
        company.addPolicy(1, insurance5);
        company.addPolicy(1, insurance6);
        company.addPolicy(1, insurance7);
        company.addPolicy(1, insurance8);

//        ArrayList<InsurancePolicy> policies = new ArrayList<>(company.allPolicies().values().stream().collect(Collectors.toList()));
//        policies.stream()
//                .filter(x->x.getCar().getPrice()<10000)
//                .sorted(Comparator.comparing(InsurancePolicy::getNumberOfClaims))
//                .forEach(System.out::println);
//        System.out.println("-----------------------------------------------");
//        policies.stream()
//                .filter(policy -> policy.getPolicyHolderName().contains("John"))
//                .forEach(System.out::println);
//        System.out.println("-----------------------------------------------");
//        policies.stream()
//                .filter(policy -> policy.calcPayment(1) > 200 && policy.calcPayment(1) <= 500)
//                        .forEach(System.out::println);
////                .findFirst().get().print();
//        System.out.println("-----------------------------------------------");
//        policies.stream()
//                .filter(policy -> policy.calcPayment(1) > 200 && policy.calcPayment(1) <= 500)
//                .sorted(Comparator.comparing(InsurancePolicy::getPolicyID))
//                .forEach(policy -> System.out.println("Name: " + policy.getPolicyHolderName()
//                        + ", ID: " + policy.getPolicyID() + ", Premium: " + policy.calcPayment(1)));
//        System.out.println("-----------------------------------------------");
//        System.out.println(policies.stream()
//                .filter(policy -> policy.calcPayment(1) > 200 && policy.calcPayment(1) <= 500)
//                .map(policy -> policy.calcPayment(1))
//                .reduce(0.0, (x, y) -> x + y));
//        System.out.println("-----------------------------------------------");
//        Predicate<InsurancePolicy> c1= x -> x.getPolicyHolderName().equals("John Smith");
//        ArrayList<InsurancePolicy> policies1= filterPolicies(policies,c1);
//        InsurancePolicy.printPolicies(policies1);
//        System.out.println("-----------------------------------------------");
//        InsurancePolicy.printPolicies(filterPolicies(policies, x->x.getExpiryDate().getYear()==2020));
//        InsurancePolicy.printPolicies(filterPolicies(policies, x->x.getCar().getModel().contains("Toyota")));
//        System.out.println("-----------------------------------------------");
//        filterPolicies(policies, x -> x.getCar().getType() == CarType.LUX).stream()
//                .sorted(Comparator.comparing(Car::getPrice))
//                .forEach(System.out::println);
//        boolean loginSuccess = false;
//        while(!loginSuccess) {
//            boolean success = false;
//            String username = "";
//            String password = "";
//            while(!success) {
//                try {
//                    System.out.println("Please enter your username:");
//                    username = scanner.nextLine();
//                    System.out.println("Please enter your password:");
//                    password = scanner.nextLine();
//                    success = true;
//                } catch (InputMismatchException error) {
//                    System.err.println(error);
//                }
//            }
//
//            if(company.validateAdmin(username, password)) {
//                loginSuccess = true;
//                UI.adminMenu(company);
//            } else {
//                System.out.println("Authentication Failed! \nPlease try again");
//            }
//        }
                    LoginForm frame = new LoginForm(company);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
    }

    public static ArrayList<InsurancePolicy> filterPolicies( ArrayList<InsurancePolicy> policies, Predicate<InsurancePolicy> criteria) {
        return (ArrayList<InsurancePolicy>) policies.stream().filter(criteria).collect(Collectors.toList());
    }
}
