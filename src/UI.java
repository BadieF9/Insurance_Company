import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    public static double flatRate;
    public static Scanner scanner = new Scanner(System.in);

    public UI(double flatRate) {
        UI.flatRate = flatRate;
    }

    public static void displayMainMenu(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // clear screen
        System.out.println(" 1) Test Code");
        System.out.println(" 2) Create User");
        System.out.println(" 3) Create ThirdParty Policy");
        System.out.println(" 4) Create Comprehensive Policy");
        System.out.println(" 5) Print User Information");
        System.out.println(" 6) Filter by Car Model");
        System.out.println(" 7) Filter by Expiry Dat");
        System.out.println(" 8) Update Address");
        System.out.println(" 9) Report user by car model");
        System.out.println("10) Report all users by city name");
        System.out.println("11) Report all users by car model");
        System.out.println("12) Sort users");
        System.out.println("13) Save company data");
        System.out.println("14) Load company data");
        System.out.println("15) Log Out");
        System.out.println("\n\n Please choose an option from 1 to 9");
    }

    public static void testCode(InsuranceCompany insuranceCompany) throws PolicyHolderNameException {
        MyDate date = new MyDate(2020, 2, 12);
        Address address = new Address(23, "Aria", "Hor st.", "Shiraz");
        Car car = new Car("Mercedes", CarType.LUX, 2020, 15000);
        User user1 = new User("Badie", 1, "badie", "password1", address);
        InsuranceCompany company = new InsuranceCompany("Sunflower", "badie", "badie123", (int) flatRate);

        InsurancePolicy insurance1 = new ThirdPartyInsurancePolicy( 1,"Sue Doe", car, 23, "This is Sue's insurance", new MyDate(2020, 2, 12));
        InsurancePolicy insurance2 = new ComprehensiveInsurancePolicy(2, "Shawn James",car, 12, 45, 1, new MyDate(1992, 2, 12));
        InsurancePolicy insurance3 = new ThirdPartyInsurancePolicy( 3, "Jack Wilson", car, 19, "This is Jack's insurance", new MyDate(1998, 2, 12));
        InsurancePolicy insurance4 = new ComprehensiveInsurancePolicy(4, "Sarah Mackinson",car, 9, 20, 3, new MyDate(2010, 2, 12));
        InsurancePolicy insurance5 = new ThirdPartyInsurancePolicy( 5, "Mark Twain", car, 19, "This is Jack's insurance", new MyDate(2005, 2, 12));
        InsurancePolicy insurance6 = new ComprehensiveInsurancePolicy(6, "Ben Smith",car, 9, 20, 3, new MyDate(2019, 2, 12));
        InsurancePolicy insurance7 = new ThirdPartyInsurancePolicy( 7, "Jade Roberts", car, 19, "This is Jack's insurance", new MyDate(2001, 2, 12));
        InsurancePolicy insurance8 = new ComprehensiveInsurancePolicy(8, "Mamad Mamadian",car, 9, 20, 3, new MyDate(2020, 2, 12));

        user1.addPolicy(insurance1);
        user1.addPolicy(insurance2);
        user1.addPolicy(insurance3);
        user1.addPolicy(insurance4);
        user1.addPolicy(insurance5);
        user1.addPolicy(insurance7);
        user1.addPolicy(insurance8);

        company.addUser(user1);
        company.addPolicy(1, insurance1);
        company.addPolicy(1, insurance2);
        company.addPolicy(1, insurance3);
        company.addPolicy(1, insurance4);
        company.addPolicy(1, insurance5);
        company.addPolicy(1, insurance7);
        company.addPolicy(1, insurance8);

//        ArrayList<InsurancePolicy> shallowCopyPolicies = new ArrayList<>(user1.shallowCopyPolicies());
//        ArrayList<InsurancePolicy> deepCopyPolicies = new ArrayList<>(user1.deepCopyPolicies());

        HashMap<Integer, InsurancePolicy> shallowCopyPolicies = new HashMap<>();
        HashMap<Integer, InsurancePolicy> deepCopyPolicies = new HashMap<>();
        ArrayList<InsurancePolicy> userShallowCopyPolicies = new ArrayList<>(user1.shallowCopyPolicies());
        for(InsurancePolicy userShallowCopyPolicy : userShallowCopyPolicies) {
            shallowCopyPolicies.put(userShallowCopyPolicy.id, userShallowCopyPolicy);
        }
        ArrayList<InsurancePolicy> userDeepCopyPolicies = new ArrayList<>(user1.shallowCopyPolicies());
        for(InsurancePolicy userDeepCopyPolicy : userDeepCopyPolicies) {
            shallowCopyPolicies.put(userDeepCopyPolicy.id, userDeepCopyPolicy);
        }

        System.out.println("User policies before any change:");
        System.out.println(user1);
        System.out.println("\nShallow and Deep copy before any change:");
        printShallowAndDeepCopyInsurancesArrayList(shallowCopyPolicies, deepCopyPolicies);
        System.out.println("\n------------------------------------------------------------------------------");

        user1.setAddress(new Address(23, "Aria", "Hor st.", "New York"));
        InsurancePolicy insurancePolicy9 = new ThirdPartyInsurancePolicy(9, "Davood Davvodian", car, 54, "Hello world", date);
        user1.addPolicy(insurancePolicy9);
        try {
            user1.sortPoliciesByDate();
        } catch (CloneNotSupportedException e) {
            System.err.println(e);
        }

        System.out.println("\nUser policies after changes:");
        System.out.println(user1);
        System.out.println("\nShallow and Deep copy after changes:");
        printShallowAndDeepCopyInsurancesArrayList(shallowCopyPolicies, deepCopyPolicies);


        System.out.println("\n------------------------------------------------------------------------------");

        HashMap<Integer, User> shallowCopyUsers = new HashMap<>(company.shallowCopyUsers(company.getUsers()));
        HashMap<Integer, User> deepCopyUsers = new HashMap<>();
        try {
            deepCopyUsers.putAll(company.deepCopyUsers(company.getUsers()));
        } catch (CloneNotSupportedException e) {
            System.err.println(e);
        }
        System.out.println("Company default users before any change:");
        for (int id : company.getUsers().keySet()) {
            company.getUser(id).print();
        }
        System.out.println("\nCompany users before any change:");
        printShallowAndDeepCopyUsersArrayList(shallowCopyUsers, deepCopyUsers);

        User user2 = new User("Erfan", 2, "erfan", "password1", address);
        company.addUser(user2);
        try {
            company.sortUsers();
        } catch (CloneNotSupportedException e) {
            System.err.println(e);
        }

        System.out.println("\n------------------------------------------------------------------------------");
        System.out.println("\nCompany default users after change:");
        for (int id : company.getUsers().keySet()) {
            company.getUser(id).print();
        }
        System.out.println("\nCompany users after changes:");
        printShallowAndDeepCopyUsersArrayList(shallowCopyUsers, deepCopyUsers);

        System.out.println("\n------------------------------------------------------------------------------");
        System.out.println("\nCompany total premium payments report:");
        company.reportPremiumsPerCity(company.getTotalPremiumPerCity());

        System.out.println("\n------------------------------------------------------------------------------");
        System.out.println("\nUser premium payments report by car model:");
        user1.reportPremiumByCarModel(user1.getTotalCountPerCarModel(), user1.getTotalPremiumPerCarModel());
//        --------------------------------------------------------------------------------------------------------------
//        Lab 6 Test Code
//
//        InsuranceCompany company1 = new InsuranceCompany();
//        InsurancePolicy.save(company.allPolicies(), "savePoliciesUsingSerialize.txt");
//        HashMap<Integer, InsurancePolicy> loadPolicies = InsurancePolicy.load("savePoliciesUsingSerialize.txt");
//        System.out.println("Policies saved and loaded using serialize:");
//        for(int id : loadPolicies.keySet()) {
//            System.out.println(loadPolicies.get(id));
//        }
//        System.out.println("----------------------------------------------------");
//        User.save(company.getUsers(), "saveUsersUsingSerialize.txt");
//        HashMap<Integer, User> loadedUsers = User.load("saveUsersUsingSerialize.txt");
//        System.out.println("Users saved and loaded using serialize:");
//        for(int id : loadedUsers.keySet()) {
//            System.out.println(loadedUsers.get(id));
//        }
//        System.out.println("----------------------------------------------------");
//        company.save("saveCompanyUsingSerialize.txt");
//        company1.getUsers().clear();
//        company1.load("saveCompanyUsingSerialize.txt");
//        System.out.println("Company saved and loaded using serialize:");
//        System.out.println(company1);
//        System.out.println("----------------------------------------------------");
//        InsurancePolicy.saveTextFile(user1.getPolicies(), "savePoliciesUsingToDilimatedMethod.txt");
//        HashMap<Integer, InsurancePolicy> policies = InsurancePolicy.loadTextFile("savePoliciesUsingToDilimatedMethod.txt");
//        System.out.println("Policies saved using toDilimated method:");
//        for(int id : policies.keySet()) {
//            System.out.println(policies.get(id));
//        }
//        System.out.println("----------------------------------------------------");
//        User.saveTextFile(company.getUsers(), "saveUsersUsingToDilimatedMethod.txt");
//        HashMap<Integer, User> users = User.loadTextFile("saveUsersUsingToDilimatedMethod.txt");
//        System.out.println("Users saved using toDilimated method:");
//        for(int id : users.keySet()) {
//            System.out.println(users.get(id));
//        }


        //-----------------------------------------------------------------------------------
        //testing the save and load policies in/from BINARY FILE

        try
        {
            InsurancePolicy.save(company.allPolicies(),"policies.ser");
            HashMap<Integer,InsurancePolicy> policies=InsurancePolicy.load("policies.ser");
            System.out.println("Printing a list of policies loaded from binary file");
            InsurancePolicy.printPolicies(policies);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }

        //-----------------------------------------------------------------------------------
        //testing the save and load policies in/from TEXT FILE

        try
        {
            InsurancePolicy.saveTextFile(company.allPolicies(),"policies.txt");
            HashMap<Integer,InsurancePolicy> policies=InsurancePolicy.loadTextFile("policies.txt");
            System.out.println("Printing a list of policies loaded from Text file");
            InsurancePolicy.printPolicies(policies);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }

        //-----------------------------------------------------------------------------------
        //testing the save and load users in/from BINARY FILE

        try
        {
            User.save(company.getUsers(),"users.ser");
            HashMap<Integer,User> users=User.load("users.ser");
            System.out.println("Printing a list of users loaded from binary file");
            System.out.println(users.values());
        }
        catch(IOException e)
        {
            System.err.println(e);
        }

        //-----------------------------------------------------------------------------------
        //testing the save and load users in/from TEXT FILE

        try
        {
            User.saveTextFile(company.getUsers(),"users.txt");
            HashMap<Integer,User> users=User.loadTextFile("users.txt");
            System.out.println("Printing a list of users loaded from Text file");
            System.out.println(users.values());
        }
        catch(IOException e)
        {
            System.err.println(e);
        }


        //-----------------------------------------------------------------------------------
        //testing the save and load InsuranceCompany in/from BINARY FILE

        try
        {
            company.save("company.ser");
            InsuranceCompany insuranceCompany2=new InsuranceCompany(); // use default constructor
            insuranceCompany2.load("company.ser");
            System.out.println("Printing the insurance company loaded from binary file");
            System.out.println(insuranceCompany2);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }

        //-----------------------------------------------------------------------------------
        //testing the save and load InsuranceCompany in/from Text FILE

        try
        {
            company.saveTextFile("company.txt");
            InsuranceCompany insuranceCompany2=new InsuranceCompany();
            insuranceCompany2.loadTextFile("company.txt");
            System.out.println("Printing the isnurance company loaded from text file");
            System.out.println(insuranceCompany2);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }
    }

    public static void createUser(InsuranceCompany company) {
        if(company.addUser(User.createUser())) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("User with this ID exists!");
        }
    }

    public static void createThirdPartyPolicy(InsuranceCompany company) {
        boolean success = false;
        int userID = 0;
        while(!success) {
            try {
                System.out.println("Please enter the user's id that you want to add the policy for:");
                userID = scanner.nextInt();
                scanner.nextLine();
                if(String.valueOf(userID).length() != 6 || String.valueOf(userID).charAt(0) == '3')
                    throw new PolicyException(userID);
                success = true;
            } catch(InputMismatchException error) {
                System.err.println(error);
            } catch(PolicyException error) {
                System.err.println(error);;
            }
        }
        User user = company.findUser(userID);

        if(user != null) {
            InsurancePolicy policy = ThirdPartyInsurancePolicy.createPolicy();
            if(user.addPolicy(policy)) {
                System.out.println("Policy added successfully!");
            } else {
                System.out.println("Policy with this ID already exists for this user!");
            }
        } else {
            System.out.println("User Not Found!");
        }
    }

    public static void createComprehensivePolicy(InsuranceCompany company) {
        int userID = 0;
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Please enter the user's id that you want to add the policy for:");
                userID = scanner.nextInt();
                scanner.nextLine();
                if(String.valueOf(userID).length() != 6 || String.valueOf(userID).charAt(0) == '3')
                    throw new PolicyException(userID);
                success= true;
            } catch (InputMismatchException error) {
                System.err.println(error);
            } catch (PolicyException error) {
                System.out.println(error);
            }
        }
        User user = company.findUser(userID);

        if(user != null) {
            InsurancePolicy policy = ComprehensiveInsurancePolicy.createPolicy();
            if(user.addPolicy(policy)) {
                System.out.println("Policy added successfully!");
            } else {
                System.out.println("Policy with this ID already exists for this user!");
            }
        } else {
            System.out.println("User Not Found!");
        }
    }

    public static void printUserInformation(InsuranceCompany company) {
        int id = 0;
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Please enter the User's ID that you want the information:");
                id = scanner.nextInt();
                scanner.nextLine();
                success = true;
            } catch (InputMismatchException error) {
                System.err.println(error);
            }
        }
        User user = company.findUser(id);
        if(user != null) {
            user.print();
        } else {
            System.out.println("User Not Found!");
        }
    }

    public static void filterByCarModel(InsuranceCompany company) {
        String carModel = "";
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Please enter the Car Model:");
                carModel = scanner.nextLine();
                success = true;
            } catch (InputMismatchException error) {
                System.err.println(error);
            }
        }
        HashMap<Integer, InsurancePolicy> filteredPolicies = company.filterByCarModel(carModel);
        if(filteredPolicies.size() > 0) {
            for(int id : filteredPolicies.keySet()) {
                System.out.println(filteredPolicies.get(id) + ", Total Payment: " + filteredPolicies.get(id).calcPayment(company.getFlatRate()));
            }
        } else {
            System.out.println("No Policy With This Car Model Found!");
        }
    }

    public static void filterByExpiryDate(InsuranceCompany company) {
        int userID = 0;
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Please enter the User's ID:");
                userID = scanner.nextInt();
                scanner.nextLine();
                success = true;
            } catch (InputMismatchException error) {
                System.err.println(error);
            }
        }
        System.out.println("Please enter the Date you want to filter the policies by:");
        MyDate date = MyDate.createDate();
        HashMap<Integer, InsurancePolicy> filteredPolicies = company.filterByExpiryDate(userID, date);
        if(filteredPolicies.size() > 0) {
            for(int id : filteredPolicies.keySet()) {
                System.out.println(filteredPolicies.get(id) + ", Total Payment: " + filteredPolicies.get(id).calcPayment(company.getFlatRate()));
            }
        } else {
            System.out.println("No Policies With This Car Model Found!");
        }
    }

    public static void updateAddress(InsuranceCompany company) {
        int userID = 0;
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Please enter the User's ID:");
                userID = scanner.nextInt();
                scanner.nextLine();
                success = true;
            } catch (InputMismatchException error) {
                System.err.println(error);
            }
        }
        User user = company.findUser(userID);
        if(user != null) {
            user.setAddress(Address.createAddress());
            System.out.println("User's address updated!");
        } else {
            System.out.println("User Not Found!");
        }
    }

    public static void reportUserTotalPremiumByCarModel(InsuranceCompany company) {
        int userID = 0;
        boolean success = false;
        while(!success) {
            try {
                System.out.println("Please enter the user ID you want to get the report for:");
                userID = scanner.nextInt();
                scanner.nextLine();
                System.out.println("The report for user with the ID " + userID + ":");
                company.findUser(userID).reportPremiumByCarModel(company.getTotalCountPerCarModel(), company.getTotalPremiumPerCity());
                success = true;
            } catch(InputMismatchException e) {
                System.err.println(e);
            } catch (NullPointerException e) {
                System.err.println(e);
            }
        }

    }

    public static void reportAllUsersTotalPremiumByCityName(InsuranceCompany company) {
        System.out.println("Report all users by city name:");
        company.reportPremiumsPerCity(company.getTotalPremiumPerCity());
    }

    public static void reportAllUsersTotalPremiumByCarModel(InsuranceCompany company) {
        System.out.println("Report all users:");
        for (int id : company.getUsers().keySet()) {
            System.out.println("User " + id + ":");
            company.getUsers().get(id).reportPremiumByCarModel(company.getTotalCountPerCarModel(), company.getTotalPremiumPerCity());
        }
    }

    public static void adminMenu(InsuranceCompany company) {
        int option = 0;
        //while(true)
        while(option != 15) {
            boolean success = false;
            while (!success) {
                try {
                    displayMainMenu();
                    option = scanner.nextInt();
                    scanner.nextLine();
                    success = true;
                } catch (InputMismatchException error) {
                    System.err.println(error);
                }
            }
            switch (option) {
                case 1:
                    try {
                        testCode(company);
                    } catch (PolicyHolderNameException e) {
                        System.out.println(e);
                    }
                    break;

                case 2:
                    createUser(company);
                    break;

                case 3:
                    createThirdPartyPolicy(company);
                    break;

                case 4:
                    createComprehensivePolicy(company);
                    break;

                case 5:
                    printUserInformation(company);
                    break;

                case 6:
                    filterByCarModel(company);
                    break;

                case 7:
                    filterByExpiryDate(company);
                    break;

                case 8:
                    updateAddress(company);
                    break;

                case 9:
                    reportUserTotalPremiumByCarModel(company);
                    break;

                case 10:
                    reportAllUsersTotalPremiumByCityName(company);
                    break;

                case 11:
                    reportAllUsersTotalPremiumByCarModel(company);
                    break;

                case 12:
                    try {
                        sortData(company);
                    } catch (CloneNotSupportedException e) {
                        System.out.println(e);
                    }
                    break;

                case 13:
                    try {
                        saveCompany(company);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    break;

                case 14:
                    loadCompany(company);
                    break;

                case 15:
                    break;
                default:
                    System.out.println("Wrong Option!");
            }
        }
    }

    public static void printShallowAndDeepCopyInsurancesArrayList(HashMap<Integer, InsurancePolicy> shallowCopyPolicies
            , HashMap<Integer, InsurancePolicy> deepCopyPolicies) {
        System.out.println("Shallow Copy:");
        for (int id : shallowCopyPolicies.keySet()) {
            System.out.println(shallowCopyPolicies.get(id));
        }
        System.out.println("Deep Copy:");
        for (int id : deepCopyPolicies.keySet()) {
            System.out.println(deepCopyPolicies.get(id));
        }
    }

    public static void printShallowAndDeepCopyUsersArrayList(HashMap<Integer, User> shallowCopyUsers
            , HashMap<Integer, User> deepCopyUsers) {
        System.out.println("Shallow Copy:");
        for (int id : shallowCopyUsers.keySet()) {
            System.out.println(shallowCopyUsers.get(id));
        }
        System.out.println("Deep Copy:");
        for (int id : deepCopyUsers.keySet()) {
            System.out.println(deepCopyUsers.get(id));
        }
    }

    public static void sortData(InsuranceCompany company) throws CloneNotSupportedException {
        company.sortUsers();
        System.out.println("Users have been sorted");
    }

    public static void saveCompany(InsuranceCompany company) throws IOException {
        if(company.save("CompanySavedData.txt")) {
            System.out.println("Company data saved successfully!");
        } else {
            System.out.println("Failed to save the company data!");
        }
    }

    public static void loadCompany(InsuranceCompany company) {
        if(company.load("CompanySavedData.txt")) {
            System.out.println("Company data loaded successfully!");
        } else {
            System.out.println("Failed to load the company data!");
        }
    }
}
