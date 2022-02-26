import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.PolicyNode;
import java.util.*;
import java.util.stream.Collectors;

public class User implements Cloneable, Comparable<User>, Comparator<User>, Serializable {

    public static Scanner scanner = new Scanner(System.in);
    public static int idCounter = 0;
    private String name;
    private int userID;
    private String username;
    private String password;
    private Address address;
    private static ObjectOutputStream outputst;
    private static ObjectInputStream inputst;
//    private ArrayList<InsurancePolicy> policies;
    private HashMap<Integer, InsurancePolicy> policies;

    public User(String name, String username, String password, Address address) {
        this.name = name;
        this.userID = User.idCounter++;
        this.username = username;
        this.password = password;
        this.address = address;
        this.policies = new HashMap<Integer, InsurancePolicy>();
    }

    public User(String name, int id, String username, String password, Address address) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.userID = idCounter++;
        this.address = address;
        this.policies = new HashMap<Integer, InsurancePolicy>();
    }

    public User(User user) {
        this.name = user.name;
        this.userID = user.userID;
        this.username = user.username;
        this.password = user.password;
        this.address = new Address(user.address);
        this.policies = new HashMap<Integer, InsurancePolicy>();
        for(int id : user.policies.keySet()) {
            InsurancePolicy policy = user.policies.get(id);
            if(policy instanceof ComprehensiveInsurancePolicy) {
                this.policies.put(policy.id, new ComprehensiveInsurancePolicy((ComprehensiveInsurancePolicy) policy));
            } else if(policy instanceof ThirdPartyInsurancePolicy){
                this.policies.put(policy.id, new ThirdPartyInsurancePolicy((ThirdPartyInsurancePolicy) policy));
            }
        }
    }

    public String toDilimatedString() {
        String dilimated = "user," + this.userID + "," + this.username + "," +this.password + "," +  this.name + "," + this.address.toDilimatedString() + "," + this.policies.size() + ",policies,";
        for (int id : this.policies.keySet()) {
            dilimated = dilimated + this.policies.get(id).toDilimatedString() + ",";
        }
        return dilimated;
    }

    public String toString() {
        String userInfo = "Name: " + this.name + ", UserID: " + this.userID + ", Username: " + this.username + ", Passowrd: "+ this.password + ", Address: " + this.address;
        for (int id : this.policies.keySet()) {
            userInfo += "\n" + policies.get(id);
        }
        return userInfo;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        user.address = this.address.clone();
        for(int id : this.policies.keySet()) {
            user.policies.put(id, this.policies.get(id).clone());
        }
        return user;
    }

    @Override
    public int compareTo(User user) {
        return this.address.compareTo(user.address);
    }

    @Override
    public int compare(User user1, User user2) {
        int flatRate = 1;
        return Double.compare(user1.calcTotalPremiums(flatRate), user2.calcTotalPremiums(flatRate));
    }

    //    public int compareTo1(User user) {
//        final int flatRate = 2;
//        double userTotalPremiums = user.calcTotalPremiums(flatRate);
//        double totalPremiums = this.calcTotalPremiums(flatRate);
//
//        if (totalPremiums > userTotalPremiums) {
//            return 1;
//        } else if(totalPremiums < userTotalPremiums) {
//            return -1;
//        } else {
//            return 0;
//        }
//    }

//    public ArrayList<InsurancePolicy> sortPoliciesByDate() throws CloneNotSupportedException {
//        ArrayList<InsurancePolicy> deepCopyPolicies = deepCopyPolicies();
//        Collections.sort(deepCopyPolicies);
//        return deepCopyPolicies;
//    }

    public ArrayList<InsurancePolicy> sortPoliciesByDate() throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> deepCopyPolicies = new ArrayList<InsurancePolicy>();
        for(int id : this.policies.keySet()) {
            InsurancePolicy policy = this.policies.get(id);
            if(policy instanceof ComprehensiveInsurancePolicy) {
                deepCopyPolicies.add(new ComprehensiveInsurancePolicy((ComprehensiveInsurancePolicy) policy));
            } else {
                deepCopyPolicies.add(new ThirdPartyInsurancePolicy((ThirdPartyInsurancePolicy) policy));
            }
        }
        Collections.sort(deepCopyPolicies);
        return deepCopyPolicies;
    }

    public boolean addPolicy(InsurancePolicy newPolicy) {
//        for (InsurancePolicy policy : this.policies) {
//            if(policy.getId() == newPolicy.getId()) return false;
//        }
//        this.policies.add(newPolicy);
//        return true;
        if(findPolicy(newPolicy.id) != null) return false;
        this.policies.put(newPolicy.id, newPolicy);
        return true;
    }

    public InsurancePolicy findPolicy(int policyID) {
//        for (InsurancePolicy policy : this.policies) {
//            if (policy.getId() == policyID) return policy;
//        }
//        return null;
        return this.policies.get(policyID);
    }

    public void print() {
        System.out.println(this.toString());
    }

//    public String toString() {
//        String userInfo = "Name: " + this.name + ", UserID: " + this.userID + ", Address: " + this.address;
//        for (InsurancePolicy policy : this.policies) {
//            userInfo += "\n" + policy;
//        }
//        return userInfo;
//    }

    public void printPolicies(int flatRate) {
//        InsurancePolicy.printPolicies(this.policies, flatRate);
        this.policies.values().stream().forEach(System.out::println);
    }

    public double calcTotalPremiums(int flatRate) {
        return InsurancePolicy.calcTotalPayments(this.policies, flatRate);
    }

    public double calcTotalPremiums() {
        int flatRate = 1;
        return InsurancePolicy.calcTotalPayments(this.policies, flatRate);
    }

//    public void carPriceRiseAll(double risePercent) {
//        for (InsurancePolicy policy : this.policies) {
//            policy.carPriceRise(risePercent);
//        }
//    }

    public void carPriceRiseAll(double risePercent) {
        for (int id : this.policies.keySet()) {
            policies.get(id).carPriceRise(risePercent);
        }
    }

//    public ArrayList<InsurancePolicy> filterByCarModel(String carModel) {
//        return InsurancePolicy.filterByCarModel(this.policies, carModel);
//    }
    public HashMap<Integer, InsurancePolicy> filterByCarModel(String carModel) {
        return InsurancePolicy.filterByCarModel(this.policies, carModel);
    }

    public boolean createThirdPartyPolicy(String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) {
        try {
            return addPolicy(new ThirdPartyInsurancePolicy(id, policyHolderName, car, numberOfClaims, comments, expiryDate));
        } catch (PolicyHolderNameException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean createComprehensivePolicy(String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) {
        try {
            return addPolicy(new ComprehensiveInsurancePolicy(id, policyHolderName, car, numberOfClaims, driverAge, level, expiryDate));
        } catch (PolicyHolderNameException e) {
            System.out.println(e);
            return false;
        }
    }

//    public ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
//        return InsurancePolicy.filterByExpiryDate(this.policies, date);
//    }
    public HashMap<Integer, InsurancePolicy> filterByExpiryDate(MyDate date) {
        return InsurancePolicy.filterByExpiryDate(this.policies, date);
    }

    public HashMap<String, Integer> getTotalCountPerCarModel() {
        HashMap<String, Integer> carNumbers = new HashMap<String, Integer>();
        for (int id : this.policies.keySet()) {
            String carModel = this.policies.get(id).car.getModel();
            if(carNumbers.get(carModel) == null) carNumbers.put(carModel, 1);
            carNumbers.put(carModel, carNumbers.get(carModel) + 1);
        }
        return carNumbers;
    }

    public HashMap<String,Double> getTotalPremiumPerCarModel() {
        HashMap<String, Double> totalPremiums = new HashMap<String, Double>();
        int flatRate = 1;
        for(int id : this.policies.keySet()) {
            String carModel = this.policies.get(id).car.getModel();
            if(totalPremiums.get(carModel) == null) totalPremiums.put(carModel, this.policies.get(id).calcPayment(flatRate));
            totalPremiums.put(carModel, totalPremiums.get(carModel) + this.policies.get(id).calcPayment(flatRate));
        }
        return totalPremiums;
    }

    public void reportPremiumByCarModel(HashMap<String,Integer> counts, HashMap<String,Double> totals) {
        System.out.println("Car Model              Total Premium Payment    Average Premium Payment");
        for(String carName : counts.keySet()) {
            System.out.println(carName + "                     " + "$" + totals.get(carName) + "                   " + "$" + totals.get(carName) / counts.get(carName));
        }
    }
    public static User createUser() {
        System.out.println("Please enter the user's Name:");
        String name = scanner.nextLine();
        System.out.println("Please enter the user's ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("PLease enter your password");
        String password = scanner.nextLine();
        Address address = Address.createAddress();
        System.out.println(address);
        return new User(name, username, password, address);
    }

    public ArrayList<InsurancePolicy> shallowCopyPolicies() {
        ArrayList<InsurancePolicy> shallowCopy = new ArrayList<InsurancePolicy>();
        for (int id : this.policies.keySet()) {
            shallowCopy.add(this.policies.get(id));
        }
        return shallowCopy;
    }

    public ArrayList<InsurancePolicy> deepCopyPolicies() throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> shallowCopy = new ArrayList<InsurancePolicy>();
        for(int id : this.policies.keySet()) {
            shallowCopy.add(this.policies.get(id).clone());
        }
        return shallowCopy;
    }

    public HashMap<Integer,InsurancePolicy > deepCopyPoliciesHashMap() throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> policiesDeepCopy = new HashMap<Integer, InsurancePolicy>();
        for(int id : this.policies.keySet()) {
            policiesDeepCopy.put(id, policies.get(id).clone());
        }
        return policiesDeepCopy;
    }

    public HashMap<Integer,InsurancePolicy > shallowCopyPoliciesHashMap() {
        HashMap<Integer, InsurancePolicy> policiesShallowCopy = new HashMap<Integer, InsurancePolicy>();
        for(int id : this.policies.keySet()) {
            policiesShallowCopy.put(id, this.policies.get(id));
        }
        return policiesShallowCopy;
    }

    public static ArrayList<User> shallowCopy(ArrayList<User> users) {
//        ArrayList<User> shallowCopy = new ArrayList<User>();
//        for (User user : users) {
//            shallowCopy.add(user);
//        }
//        return shallowCopy;
        return (ArrayList<User>) (users.stream().collect(Collectors.toList()));
    }

    public static ArrayList<User> shallowCopy(HashMap<Integer, User> users) {
        ArrayList<User> shallowCopy = new ArrayList<User>();
        for (int id : users.keySet()) {
            shallowCopy.add(users.get(id));
        }
        return shallowCopy;
    }

    public static ArrayList<User> deepCopy(ArrayList<User> users) {
//        ArrayList<User> shallowCopy = new ArrayList<>();
//        for(User user : users) {
//            shallowCopy.add(user.clone());
//        }
//        return shallowCopy;
        return (ArrayList<User>) (users.stream().map(user -> {
            try {
                return user.clone();
            } catch(CloneNotSupportedException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).collect(Collectors.toList()));
    }

    public static ArrayList<User> deepCopy(HashMap<Integer, User> users) throws CloneNotSupportedException {
        ArrayList<User> shallowCopy = new ArrayList<>();
        for(int id : users.keySet()) {
            shallowCopy.add(users.get(id).clone());
        }
        return shallowCopy;
    }

    public static ArrayList<InsurancePolicy> deepCopyPolicies(ArrayList<InsurancePolicy> policies) throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> policiesDeepCopy = new ArrayList<>();
        for(InsurancePolicy policy : policies) {
            policiesDeepCopy.add(policy.clone());
        }
        return policiesDeepCopy;
    }

    public static HashMap<Integer, InsurancePolicy> deepCopyPolicies(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> policiesDeepCopy = new HashMap<Integer, InsurancePolicy>();
        for(int id : policies.keySet()) {
            policiesDeepCopy.put(id, policies.get(id).clone());
        }
        return policiesDeepCopy;
    }

    public static ArrayList< InsurancePolicy> shallowCopyPolicies(ArrayList<InsurancePolicy> policies) throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> policiesShallowCopy = new ArrayList<>();
        for(InsurancePolicy policy : policies) {
            policiesShallowCopy.add(policy);
        }
        return policiesShallowCopy;
    }

    public static HashMap<Integer, InsurancePolicy> shallowCopyPolicies(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> policiesShallowCopy = new HashMap<Integer, InsurancePolicy>();
        for(int id : policies.keySet()) {
            policiesShallowCopy.put(id, policies.get(id));
        }
        return policiesShallowCopy;
    }

    public static void openInFile(String filename) {
        try {
            inputst = new ObjectInputStream(Files.newInputStream(Paths.get(filename)));
        } catch(IOException error) {
            System.err.println("error in create/open the file ");
            System.exit(1);
        }
    }

    public static void readRecord(HashMap<Integer, User> users) {
        try {
            while(true) {
                User user = (User) inputst.readObject() ;
                users.put(user.userID, user);
            }
        } catch(EOFException error) {
            System.out.println("no more record!");
        } catch (ClassNotFoundException error) {
            System.err.println("error in wrong class in the file ");
        } catch(IOException error) {
            System.err.println("error in add object to the file ");
            System.exit(1);
        }
    }

    public static void closeInFile() {
        try {
            if(inputst !=null)
                inputst.close();
        } catch(IOException error) {
            System.err.println("error in close the file ");
            System.exit(1);
        }
    }

    public static HashMap<Integer, User> load(String fileName) {
        HashMap<Integer, User> users = new HashMap<Integer, User>();
        openInFile(fileName);
        readRecord(users);
        closeInFile();
        return users;
    }

    public static void openOutFile(String fileName) throws IOException{
        outputst = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
    }

    public static void addRecord(HashMap<Integer, User> users) throws IOException{
        for(int id : users.keySet()) {
            outputst.writeObject(users.get(id));
        }
    }

    public static void closeOutFile() throws IOException {
        if(outputst !=null)
            outputst.close();
    }

    public static boolean save(HashMap<Integer, User> user, String fileName) throws IOException {
        try {
            openOutFile(fileName);
            addRecord(user);
            closeOutFile();
            return true;
        } catch (IOException error) {
            System.out.println("Error in saving the file");
            return false;
        }
    }

    public static HashMap<Integer, User> loadTextFile(String fileName) throws IOException {
        HashMap<Integer, User> users = new HashMap<Integer, User>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line = in.readLine();
        while (line != null) {
            line = line.trim();
            String[] field = line.split(",");
            int userID = Integer.parseInt(field[1]);
            String username = field[2];
            String userPassword = field[3];
            String userName = field[4];
            Address address = new Address(Integer.parseInt(field[5]), field[6], field[7], field[8]);
            User user = new User(userName, userID, username, userPassword, address);
            HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
            String[] userPolicies = line.split(",policies,");
            Car car;
            MyDate date;
            InsurancePolicy policy;
            if(userPolicies.length > 1) {
                for(int policyNum = 0 ; policyNum < userPolicies.length ; policyNum++) {
                    String[] policyFields = userPolicies[1].split("policy,")[policyNum].split(",");
                    switch (policyFields[0]) {
                        case "tpip":
                            car = new Car(policyFields[3], CarType.valueOf(policyFields[4]), Integer.parseInt(policyFields[5]), Double.parseDouble(policyFields[6]));
                            date = new MyDate(Integer.parseInt(policyFields[9]), Integer.parseInt(policyFields[10]), Integer.parseInt(policyFields[11]));
                            try {
                                policy = new ThirdPartyInsurancePolicy(Integer.parseInt(policyFields[1]), policyFields[2], car,  Integer.parseInt(policyFields[7]), policyFields[8], date);
                                policies.put(policy.id, policy);
                            } catch (PolicyHolderNameException e) {
                                System.out.println(e);
                            }
                        

                        case "cip":
                            int id = Integer.parseInt(policyFields[1]);
                            String policyHolderName = policyFields[2];
                            car = new Car(policyFields[3], CarType.valueOf(policyFields[4]), Integer.parseInt(policyFields[5]), Double.parseDouble(policyFields[6]));
                            date = new MyDate(Integer.parseInt(policyFields[10]), Integer.parseInt(policyFields[11]), Integer.parseInt(policyFields[12]));
                            try {
                                policy = new ComprehensiveInsurancePolicy(id, policyHolderName, car
                                        ,  Integer.parseInt(policyFields[7]), Integer.parseInt(policyFields[8]), Integer.parseInt(policyFields[9]), date);
                                policies.put(policy.id, policy);
                            } catch (PolicyHolderNameException e) {
                                System.out.println(e);
                            }
                        
                    }
                }
                line = in.readLine();
                user.policies.putAll(policies);
            }
            users.put(user.getUserID(), user);
        }
        in.close();
        return users;
    }

    public static boolean saveTextFile(HashMap<Integer, User> users, String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            for(int id : users.keySet()) {
                out.write(users.get(id).toDilimatedString() + "\n");
            }
            out.close();
            return true;
        } catch (IOException error) {
            System.out.println("Error in saving to a text file!");
            return false;
        }
    }

    public void setCity(String city) {
        this.address.setCity(city);
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Integer, InsurancePolicy> getPolicies() {
        return this.policies;
    }

    public Address getAddress() {
        return address;
    }

    public String getCity() { return this.address.city; }

    public void setAddress(Address address) {
        this.address = address;
    }

}
