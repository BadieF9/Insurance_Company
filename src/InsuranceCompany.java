import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class InsuranceCompany implements Cloneable, Serializable {

    public String name;
//    private ArrayList<User> users;
    private HashMap<Integer, User> users;
    private String adminUsername;
    private String adminPassword;
    private int flatRate;

    private static ObjectOutputStream outputst;
    private static ObjectInputStream inputst;

    public class CompareUserByTotalPayments implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            int flatRate = 1;
            return Double.compare(user1.calcTotalPremiums(flatRate), user2.calcTotalPremiums(flatRate));
        }
    }
    public InsuranceCompany(String name, String adminUsername, String adminPassword, int flatRate) {
        this.name = name;
        this.users = new HashMap<Integer, User>();
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.flatRate = flatRate;
    }

//    public InsuranceCompany(InsuranceCompany insuranceCompany) {
//        this.name = insuranceCompany.name;
//        this.adminUsername = insuranceCompany.adminUsername;
//        this.adminPassword = insuranceCompany.adminPassword;
//        this.flatRate = insuranceCompany.flatRate;
//        this.users = new ArrayList<>();
//        for (User user : insuranceCompany.users) {
//            this.users.add(new User(user));
//        }
//    }

    public InsuranceCompany(InsuranceCompany insuranceCompany) {
        this.name = insuranceCompany.name;
        this.adminUsername = insuranceCompany.adminUsername;
        this.adminPassword = insuranceCompany.adminPassword;
        this.flatRate = insuranceCompany.flatRate;
        this.users = new HashMap<Integer, User>();
        for (int id : insuranceCompany.users.keySet()) {
            this.users.put(id, new User(this.users.get(id)));
        }
    }

    public InsuranceCompany() {
        this.name = "";
        this.users = new HashMap<Integer, User>();
        this.adminUsername = "";
        this.adminPassword = "";
        this.flatRate = 0;
    }

    public String toDilimatedString() {
        String dilimated = this.name + "," + this.adminUsername + "," + this.adminPassword + "," + this.flatRate
                + this.users.size() + ",users,";
        for(int id : this.users.keySet()) {
            dilimated = dilimated + this.users.get(id).toDilimatedString();
        }
        return dilimated;
    }

    public String toString() {
        String user = "Insurance company name: " + this.name + ", Admin username: " + this.adminUsername + ", Admin password: " + this.adminPassword + ", Flat rate: " + this.flatRate + "\n";
        for (int id : this.users.keySet()) {
            user += this.users.get(id);
        }
        return user;
    }

//    public InsuranceCompany clone() throws CloneNotSupportedException {
//        InsuranceCompany insuranceCompany = (InsuranceCompany) super.clone();
//        for (User user : this.users) {
//            insuranceCompany.users.add(new User(user.clone()));
//        }
//        return insuranceCompany;
//    }

    public InsuranceCompany clone() throws CloneNotSupportedException {
        InsuranceCompany insuranceCompany = (InsuranceCompany) super.clone();
        for (int id : this.users.keySet()) {
            insuranceCompany.users.put(id, new User(this.users.get(id).clone()));
        }
        return insuranceCompany;
    }

    public boolean validateAdmin(String username, String password) {
        return this.adminUsername.equals(username) && this.adminPassword.equals(password);
    }

    public boolean addUser(User user) {
//        User userFound = findUser(user.getUserID());
//        if(userFound == null) {
//            this.users.add(user);
//            return true;
//        }
//        return false;
        if(findUser(user.getUserID()) != null) return false;
        this.users.put(user.getUserID(), user);
        return true;
    }

    public boolean addUser(String name, int userID, String username, String password, Address address) {
        return addUser(new User(name, userID, username, password, address));
    }

    public User findUser(int userID) {
//        for (User user : this.users) {
//            if(user.getUserID() == userID) return user;
//        }
//        return null;
        return this.users.get(userID);
    }

    public boolean addPolicy(int userID, InsurancePolicy policy) {
        User userFound = findUser(userID);
        if(userFound != null)  return userFound.addPolicy(policy);
        return false;
    }

    public InsurancePolicy findPolicy(int userID ,int policyID) {
        User userFound = findUser(userID);
        if(userFound != null) {
            return findUser(userID).findPolicy(policyID);
        }
        return null;
    }

    public void printPolicies(int userID) {
        User userFound = findUser(userID);
        if(userFound != null) {
            findUser(userID).print();
        } else {
            System.out.println("User not found!");
        }
    }

//    public void print() {
//        for(User user : this.users) {
//            user.print();
//            user.printPolicies(this.flatRate);
//        }
//    }

    public void print() {
        for(int id: this.users.keySet()) {
            User user = this.users.get(id);
            user.print();
            user.printPolicies(this.flatRate);
        }
    }

//    public String toString() {
//        String user = "Insurance company name: " + this.name + ", Admin username: " + this.adminUsername + ", Admin password: " + this.adminPassword + ", Flat rate: " + this.flatRate + "\n";
//        for (User adminUser : this.users) {
//            user += adminUser;
//        }
//        return user;
//    }


    public boolean createThirdPartyPolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, String comments) {
        User userFound = findUser(userID);
        if(userFound != null) {
            return findUser(userID).createThirdPartyPolicy(policyHolderName, id, car, numberOfClaims, expiryDate, comments);
        }
        return false;
    }

    public boolean createComprehensivePolicy(int userID, String policyHolderName, int id, Car car, int numberOfClaims, MyDate expiryDate, int driverAge, int level) {
        User userFound = findUser(userID);
        if(userFound != null) {
            return findUser(userID).createComprehensivePolicy(policyHolderName, id, car, numberOfClaims, expiryDate, driverAge, level);
        }
        return false;
    }

    public double calcTotalPayments(int userID) {
        return findUser(userID).calcTotalPremiums(this.flatRate);
    }

//    public double calcTotalPayments() {
//        double total = 0;
//        for (User user : this.users) {
//            total += user.calcTotalPremiums(this.flatRate);
//        }
//        return total;
//    }

    public double calcTotalPayments() {
//        double total = 0;
//        for (int id : this.users.keySet()) {
//            total += this.users.get(id).calcTotalPremiums(this.flatRate);
//        }
//        return total;
        return this.users.values().stream()
                .map(user -> user.calcTotalPremiums(this.flatRate))
                .reduce(0.0, (x, y) -> x + y);

    }

    public void carPriceRise(double risePercent) {
//        User userFound = findUser(userID);
//        if(userFound != null) {
//            userFound.carPriceRiseAll(risePercent);
//        }
        this.users.values().forEach(user -> user.carPriceRiseAll(risePercent));
    }

//    public ArrayList<InsurancePolicy> allPolicies() {
//        ArrayList<InsurancePolicy> policies = new ArrayList<>();
//        for (User user : this.users) {
//            policies.addAll(user.getPolicies());
//        }
//        return policies;
//    }

    public HashMap<Integer, InsurancePolicy> allPolicies() {
        HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
        for (int id : this.users.keySet()) {
            policies.putAll(this.users.get(id).getPolicies());
        }
        return policies;
    }

//    public ArrayList<InsurancePolicy> filterByCarModel(int userID, String carModel) {
//        User userFound = findUser(userID);
//        if(userFound != null) {
//            return userFound.filterByCarModel(carModel);
//        }
//        return null;
//    }

    public HashMap<Integer, InsurancePolicy> filterByCarModel(int userID, String carModel) {
        User userFound = findUser(userID);
        if(userFound != null) {
            return userFound.filterByCarModel(carModel);
        }
        return null;
    }

//    public ArrayList<InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
//        User userFound = findUser(userID);
//        if(userFound != null) {
//            return userFound.filterByExpiryDate(date);
//        }
//        return null;
//    }

    public HashMap<Integer, InsurancePolicy> filterByExpiryDate(int userID, MyDate date) {
        User userFound = findUser(userID);
        if(userFound != null) {
            return userFound.filterByExpiryDate(date);
        }
        return null;
    }

//    public ArrayList<InsurancePolicy> filterByCarModel(String carModel) {
////        ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<>();
////        for(User user : this.users) {
////            filteredPolicies.addAll(user.filterByCarModel(carModel));
////        }
////        return filteredPolicies;
//        return (ArrayList<InsurancePolicy>) (this.users.values().stream()
//                .map(user -> user.filterByCarModel(carModel)).collect(Collectors.toList()));
//    }

    public HashMap<Integer, InsurancePolicy> filterByCarModel(String carModel) {
        HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
        for(int id : this.users.keySet()) {
            filteredPolicies.putAll(this.users.get(id).filterByCarModel(carModel));
        }
        return filteredPolicies;
    }

//    public ArrayList<InsurancePolicy> filterByExpiryDate(MyDate date) {
//        ArrayList<InsurancePolicy> policies = new ArrayList<>();
//        for(User user : this.users) {
//            ArrayList<InsurancePolicy> filteredPolicy = user.filterByExpiryDate(date);
//            if(filteredPolicy != null) {
//                policies.addAll(filteredPolicy);
//            }
//        }
//        return policies;
//    }

    public HashMap<Integer, InsurancePolicy> filterByExpiryDate(MyDate date) {
        HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
        for(int id : this.users.keySet()) {
            HashMap<Integer, InsurancePolicy> filteredPolicy = this.users.get(id).filterByExpiryDate(date);
            if(filteredPolicy != null) {
                policies.putAll(filteredPolicy);
            }
        }
        return policies;
    }

    public HashMap<String, Double> getTotalPremiumPerCity() {
//        HashMap<String, Double> totalPremium = new HashMap<String, Double>();
//        for(int id : this.users.keySet()) {
//            String city = this.users.get(id).getAddress().city;
//            if(totalPremium.get(city) == null)
//                totalPremium.put(city, this.users.get(id).calcTotalPremiums(this.flatRate));
//            else
//                totalPremium.put(city, totalPremium.get(id) + this.users.get(id).calcTotalPremiums(this.flatRate));
//        }
//        return totalPremium;
        return (HashMap<String, Double>) this.users.values().stream().collect(Collectors.groupingBy(User::getCity, Collectors.summingDouble(User::calcTotalPremiums)));
    }

    public HashMap<String, Integer> getTotalCountPerCarModel() {
        HashMap<String, Integer> totalCarCount = new HashMap<String, Integer>();
        for(int userId : this.users.keySet()) {
            HashMap<String, Integer> userCarCount = this.users.get(userId).getTotalCountPerCarModel();
            for (String carName : userCarCount.keySet()) {
                if(totalCarCount.get(carName) == null)
                    totalCarCount.put(carName, userCarCount.get(carName));
                else
                    totalCarCount.put(carName, totalCarCount.get(carName) + userCarCount.get(carName));
            }
        }
        return totalCarCount;
    }

    public HashMap<String,Double> getTotalPremiumPerCarModel () {
        HashMap<String, Double> totalCarPremium = new HashMap<String, Double>();
        for(int userId : this.users.keySet()) {
            HashMap<String, Double> userCarPremium = this.users.get(userId).getTotalPremiumPerCarModel();
            for (String carName : userCarPremium.keySet()) {
                if(totalCarPremium.get(carName) == null)
                    totalCarPremium.put(carName, userCarPremium.get(carName));
                else
                    totalCarPremium.put(carName, totalCarPremium.get(carName) + userCarPremium.get(carName));
            }
        }
        return totalCarPremium;
    }

    public void reportPremiumsPerCity(HashMap<String,Double> totals) {
        System.out.println("City Name         Total Premium Payments");
        for(String city : totals.keySet()) {
            String spaces = "";
            System.out.println(city + "                $" + totals.get(city));
        }
    }

    public void openInFile(String filename) throws IOException, EOFException, ClassNotFoundException {
        inputst = new ObjectInputStream(Files.newInputStream(Paths.get(filename)));
    }

    public void readRecord() throws IOException, ClassNotFoundException {
        this.users.clear();
        while(true) {
            User user = (User) inputst.readObject() ;
            this.users.put(user.getUserID(), user);
        }
    }

    public void closeInFile() throws IOException{
        if(inputst !=null)
            inputst.close();
    }

    public boolean load(String fileName) {
        try {
            openInFile(fileName);
            readRecord();
            closeInFile();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    public void openOutFile(String fileName) throws IOException{
        outputst = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
    }

    public void addRecord() throws IOException{
        for(int id : this.users.keySet()) {
            outputst.writeObject(this.users.get(id));
        }
    }

    public void closeOutFile() throws IOException {
        if(outputst !=null)
            outputst.close();
    }

    public boolean save(String fileName) throws IOException {
        try {
            openOutFile(fileName);
            addRecord();
            closeOutFile();
            return true;
        } catch (IOException error) {
            System.out.println("Error in saving the file");
            return false;
        }
    }

    public boolean loadTextFile(String fileName) {
        try {
            HashMap<Integer, User> users = new HashMap<Integer, User>();
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String line = in.readLine();
            while (line != null) {
                line = line.trim();
                String[] field = line.split(",");
                this.name = field[0];
                this.adminUsername = field[1];
                this.adminPassword = field[2];
                this.flatRate = Integer.parseInt(field[3]);
                String[] savedUsers = line.split("users,")[1].split("user,");
                for (int userNum = 1; userNum < savedUsers.length; userNum++) {
                    String[] userFields = savedUsers[userNum].split(",policies,")[0].split(",");
                    Address address = new Address(Integer.parseInt(userFields[2]), userFields[3], userFields[4], userFields[5]);
                    User user = new User(userFields[3], Integer.parseInt(userFields[0]),userFields[1], userFields[2], address);
                    String[] policies = savedUsers[userNum].split(",policies,")[1].split("policy,");
                    HashMap<Integer, InsurancePolicy> userPolicies = new HashMap<Integer, InsurancePolicy>();
                    Car car;
                    MyDate date;
                    InsurancePolicy policy;
                    for (int policyNum = 0; policyNum < policies.length; policyNum++) {
                        String[] policyFields = policies[policyNum].split(",");
                        switch (policyFields[0]) {
                            case "tpip":
                                car = new Car(policyFields[3], CarType.valueOf(policyFields[4]), Integer.parseInt(policyFields[5]), Double.parseDouble(policyFields[6]));
                                date = new MyDate(Integer.parseInt(policyFields[9]), Integer.parseInt(policyFields[10]), Integer.parseInt(policyFields[11]));
                                policy = new ThirdPartyInsurancePolicy(Integer.parseInt(policyFields[1]), policyFields[2], car, Integer.parseInt(policyFields[7]), policyFields[8], date);
                                userPolicies.put(policy.id, policy);
                                break;
                            

                            case "cip":
                                int id = Integer.parseInt(policyFields[1]);
                                String policyHolderName = policyFields[2];
                                car = new Car(policyFields[3], CarType.valueOf(policyFields[4]), Integer.parseInt(policyFields[5]), Double.parseDouble(policyFields[6]));
                                date = new MyDate(Integer.parseInt(policyFields[10]), Integer.parseInt(policyFields[11]), Integer.parseInt(policyFields[12]));
                                policy = new ComprehensiveInsurancePolicy(id, policyHolderName, car
                                        , Integer.parseInt(policyFields[7]), Integer.parseInt(policyFields[8]), Integer.parseInt(policyFields[9]), date);
                                userPolicies.put(policy.id, policy);
                            
                        }
                    }
                    user.getPolicies().putAll(userPolicies);
                    this.users.put(user.getUserID(), user);
                }
                line = in.readLine();
            }
            in.close();
            return true;
        } catch (IOException error) {
            System.out.println("Error in reading the company data from the text file!");
            return false;
        } catch (PolicyHolderNameException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean saveTextFile(String fileName) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        out.write(this.toDilimatedString() + "\n");
        out.close();
        return true;
    }

    public ArrayList<User> deepCopyUsers(ArrayList<User> users) throws CloneNotSupportedException {
        ArrayList<User> usersDeepCopy = new ArrayList<User>();
        for(int id : this.users.keySet()) {
            usersDeepCopy.add(new User(this.users.get(id)));
        }
        return usersDeepCopy;
    }

    public HashMap<Integer, User> deepCopyUsers(HashMap<Integer, User> users) throws CloneNotSupportedException {
        HashMap<Integer, User> usersDeepCopy = new HashMap<Integer, User>();
        for(int id : this.users.keySet()) {
            usersDeepCopy.put(id, new User(users.get(id)));
        }
        return usersDeepCopy;
    }

    public ArrayList<User> shallowCopyUsers(ArrayList<User> users) {
        ArrayList<User> usersShallowCopy = new ArrayList<User>();
        for(int id : this.users.keySet()) {
            usersShallowCopy.add(this.users.get(id));
        }
        return usersShallowCopy;
    }

    public HashMap<Integer, User> shallowCopyUsers(HashMap<Integer, User> users) {
        HashMap<Integer, User> usersShallowCopy = new HashMap<Integer, User>();
        for(int id : this.users.keySet()) {
            usersShallowCopy.put(id, this.users.get(id));
        }
        return usersShallowCopy;
    }

//    public ArrayList<User> sortUsers() throws CloneNotSupportedException {
//        ArrayList<User> usersDeepCopy = deepCopyUsers();
//        Collections.sort(usersDeepCopy);
//        return usersDeepCopy;
//    }

    public ArrayList<User> sortUsers() throws CloneNotSupportedException {
        ArrayList<User> usersDeepCopy = new ArrayList<User>();
        for (int id : this.users.keySet()) {
            usersDeepCopy.add(new User(this.users.get(id)));
        }
        Collections.sort(usersDeepCopy);
        return usersDeepCopy;
    }

    public ArrayList<User> sortUsersByPremium() {
        CompareUserByTotalPayments userCompare = new CompareUserByTotalPayments();
        ArrayList<User> usersList = new ArrayList<User>();
        for(int id : this.users.keySet()) {
            usersList.add(this.users.get(id));
        }
        Collections.sort(usersList, userCompare);
        return usersList;
    }

    /*    public String toDilimatedString() {
        String dilimated = this.name + "," + this.adminUsername + "," + this.adminPassword + "," + this.flatRate
                + this.users.size() + ",";
        for(int id : this.users.keySet()) {
            dilimated = dilimated + this.users.get(id).toDilimatedString();
        }
        return dilimated;
    }*/

    public HashMap<String, ArrayList<User>> getUsersPerCity() {
        HashMap<String, Double> cities = getTotalPremiumPerCity();
        HashMap<String, ArrayList<User>> usersPerCity = new HashMap<String, ArrayList<User>>();
        for(String city : cities.keySet()) {
            ArrayList<User> users = new ArrayList<User>();
            for(int id : this.users.keySet()) {
                if(city.equals(this.users.get(id).getAddress().city)) {
                    users.add(this.users.get(id));
                }
            }
            usersPerCity.put(city, users);
        }
        return usersPerCity;
    }

    HashMap <String, ArrayList<InsurancePolicy>> filterPoliciesByExpiryDate (MyDate expiryDate) {
        HashMap<String, ArrayList<InsurancePolicy>> filteredPolicies = new HashMap<String, ArrayList<InsurancePolicy>>();
        for(int id : this.users.keySet()) {
            HashMap<Integer, InsurancePolicy> userPolicies= this.users.get(id).filterByExpiryDate(expiryDate);
            ArrayList<InsurancePolicy> policyList = new ArrayList<InsurancePolicy>();
            for(int policyID : userPolicies.keySet()) {
                policyList.add(userPolicies.get(id));
            }
            filteredPolicies.put(this.users.get(id).getName(), policyList);
        }
        return filteredPolicies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlatRate() {
        return flatRate;
    }

    public void setFlatRate(int flatRate) {
        this.flatRate = flatRate;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public User getUser(int id) {
        return this.users.get(id);
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String password) {
        this.adminPassword = password;
    }

}
