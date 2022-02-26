import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public abstract class InsurancePolicy implements Cloneable, Comparable<InsurancePolicy>, Serializable {

    protected int id;
    protected String policyHolderName;
    protected Car car;
    protected int numberOfClaims;
    MyDate expiryDate;

    private static ObjectOutputStream outputst;
    private static ObjectInputStream inputst;

    public InsurancePolicy(int id, String policyHolderName, Car car, int numberOfClaims, MyDate date) throws PolicyHolderNameException{
        this.id = id;
        if(policyHolderName.matches("[A-Z][a-zA-Z]+[\\s][A-Z][a-zA-Z]+")) {
            this.policyHolderName = policyHolderName;
        } else {
            throw new PolicyHolderNameException("The Policy Holder name is invalid, The first letter of the first" +
                    " name and last name must be capital and there must be a space between them");
        }
        this.car = car;
        this.numberOfClaims = numberOfClaims;
        this.expiryDate = date;
    }

    public InsurancePolicy(InsurancePolicy policy) {
        this.id = policy.id;
        this.policyHolderName = policy.policyHolderName;
        this.car = new Car(policy.car);
        this.numberOfClaims = policy.numberOfClaims;
        this.expiryDate = new MyDate(policy.expiryDate);
    }

    public InsurancePolicy clone() throws CloneNotSupportedException {
        InsurancePolicy insurancePolicy = (InsurancePolicy) super.clone();
        insurancePolicy.car = new Car(car.clone());
        insurancePolicy.expiryDate = new MyDate(expiryDate.clone());
        return insurancePolicy;
    }

    public String toDilimatedString() {
        return this.id + "," + this.policyHolderName + "," + this.car.toDilimatedString() + ","
                + this.numberOfClaims + "," + this.expiryDate.toDilimatedString();
    }

    public String toString() {
        return "ID: " + this.id + ", PolicyHolderName: " + this.policyHolderName + ", Car: " + car + " Number Of Claims: "
                + this.numberOfClaims + ", Expiry date: " + this.expiryDate;
    }

    @Override
    public int compareTo(InsurancePolicy insurancePolicy) {
        return this.expiryDate.compareTo(insurancePolicy.expiryDate);
    }

    public void print() {
        System.out.print( "ID: " + this.id + ", PolicyHolderName: " + this.policyHolderName + ", NumberOfClaims: "
                + this.numberOfClaims + ", " + ", Expiry date: " + this.expiryDate);
        this.car.print();
    }

    public static void printPolicies(ArrayList<InsurancePolicy> policies) {
        policies.forEach(System.out::println);
    }

    public static void printPolicies(HashMap<Integer, InsurancePolicy> policies) {
        for (int id : policies.keySet()) {
            policies.get(id).print();
        }
    }

    public static void printPolicies(ArrayList<InsurancePolicy> policies, int flatRate) {
        for (InsurancePolicy policy : policies) {
            System.out.println(policy + ", Premium payment: " + policy.calcPayment(flatRate));
        }
    }
    public static void printPolicies(HashMap<Integer, InsurancePolicy> policies, int flatRate) {
        for (int id : policies.keySet()) {
            InsurancePolicy policy = policies.get(id);
            System.out.println(policy + ", Premium payment: " + policy.calcPayment(flatRate));
        }
    }

    public abstract double calcPayment(int flatRate);

    public int getId() {
        return id;
    }

    public static double calcTotalPayments(ArrayList<InsurancePolicy> policies, int flatRate) {
        return policies.stream()
                .map(policy -> policy.calcPayment(flatRate))
                .reduce(0.0, (x, y) -> x + y);
    }

    public static double calcTotalPayments(HashMap<Integer, InsurancePolicy> policies, int flatRate) {
        double totalPayment = 0;
        for(int id : policies.keySet()) {
            totalPayment += policies.get(id).calcPayment(flatRate);
        }

        return totalPayment;
    }

    public void carPriceRise(double risePercent) {
        this.car.priceRise(risePercent);
    }

    public static void carPriceRiseAll(ArrayList< InsurancePolicy > policies, double risePercent) {
//        for(InsurancePolicy policy : policies) {
//            policy.carPriceRise(risePercent);
//        }
        policies.stream().forEach(policy -> policy.carPriceRise(risePercent));
    }

    public static void carPriceRiseAll(HashMap<Integer, InsurancePolicy> policies, double risePercent) {
        for(int id : policies.keySet()) {
            policies.get(id).carPriceRise(risePercent);
        }
    }

    public static ArrayList<InsurancePolicy> filterByCarModel (ArrayList<InsurancePolicy> policies, String carModel) {
        return (ArrayList<InsurancePolicy>) (policies.stream()
                .filter(policy -> policy.getCar().getModel().contains(carModel))
                .collect(Collectors.toList()));
    }

    public static HashMap<Integer, InsurancePolicy> filterByCarModel(HashMap<Integer, InsurancePolicy> policies, String carModel) {
        HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<>();
        for (int id : policies.keySet()) {
            if(policies.get(id).car.getModel().equals(carModel)) {
                filteredPolicies.put(id, policies.get(id));
            }
        }
        return filteredPolicies;
    }

    public static ArrayList<InsurancePolicy> filterByExpiryDate(ArrayList<InsurancePolicy> policies, MyDate date) {
//        ArrayList<InsurancePolicy> filteredPolicies = new ArrayList<>();
//        for(InsurancePolicy policy : policies) {
//            if(!policy.expiryDate.isExpired(date)) filteredPolicies.add(policy);
//        }
//        return filteredPolicies;
        return (ArrayList<InsurancePolicy>) (policies.stream()
                .filter(policy -> policy.expiryDate.isExpired(date))
                .collect(Collectors.toList()));
    }

    public static HashMap<Integer, InsurancePolicy> filterByExpiryDate(HashMap<Integer, InsurancePolicy> policies, MyDate date) {
        HashMap<Integer, InsurancePolicy> filteredPolicies = new HashMap<Integer, InsurancePolicy>();
        for(int id : policies.keySet()) {
            if(!policies.get(id).expiryDate.isExpired(date)) filteredPolicies.put(id, policies.get(id));
        }
        return filteredPolicies;
    }

    public static ArrayList<InsurancePolicy> shallowCopy(ArrayList<InsurancePolicy> policies) {
//        ArrayList<InsurancePolicy> shallowCopy = new ArrayList<>();
//        for(InsurancePolicy insurancePolicy : policies) {
//            shallowCopy.add(insurancePolicy);
//        }
//        return shallowCopy;
        return (ArrayList<InsurancePolicy>) (policies.stream().collect(Collectors.toList()));
    }

    public static ArrayList<InsurancePolicy> shallowCopy(HashMap<Integer, InsurancePolicy> policies) {
        ArrayList<InsurancePolicy> shallowCopy = new ArrayList<>();
        for(int id : policies.keySet()) {
            shallowCopy.add(policies.get(id));
        }
        return shallowCopy;
    }

    public static HashMap<Integer, InsurancePolicy> shallowCopyHashMap(HashMap<Integer, InsurancePolicy> policies) {
        HashMap<Integer, InsurancePolicy> shallowCopy = new HashMap<Integer, InsurancePolicy>();
        for(int id : policies.keySet()) {
            shallowCopy.put(id, policies.get(id));
        }
        return shallowCopy;
    }

    public static ArrayList<InsurancePolicy> deepCopy(ArrayList<InsurancePolicy> policies) {
//        ArrayList<InsurancePolicy> shallowCopy = new ArrayList<>();
//        for(InsurancePolicy insurancePolicy : policies) {
//            shallowCopy.add(insurancePolicy.clone());
//        }
//        return shallowCopy;
        return (ArrayList<InsurancePolicy>) (policies.stream().map(policy -> {
            try {
                return policy.clone();
            } catch (CloneNotSupportedException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }).collect(Collectors.toList()));
    }

    public static ArrayList<InsurancePolicy> deepCopy(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        ArrayList<InsurancePolicy> shallowCopy = new ArrayList<>();
        for(int id : policies.keySet()) {
            shallowCopy.add(policies.get(id).clone());
        }
        return shallowCopy;
    }

    public static HashMap<Integer, InsurancePolicy> deepCopyHashMap(HashMap<Integer, InsurancePolicy> policies) throws CloneNotSupportedException {
        HashMap<Integer, InsurancePolicy> shallowCopy = new HashMap<Integer, InsurancePolicy>();
        for(int id : policies.keySet()) {
            shallowCopy.put(id, policies.get(id).clone());
        }
        return shallowCopy;
    }

    public static void openInFile(String filename) {
        try {
            inputst = new ObjectInputStream(Files.newInputStream(Paths.get(filename)));
        } catch(IOException error) {
            System.err.println("error in create/open the file ");
            System.exit(1);
        }
    }

    public static void readRecord(HashMap<Integer, InsurancePolicy> policies) {
        try {
            while(true) {
                InsurancePolicy policy = (InsurancePolicy) inputst.readObject() ;
                policies.put(policy.id, policy);
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

    public static HashMap<Integer, InsurancePolicy> load(String fileName) {
        HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
        openInFile(fileName);
        readRecord(policies);
        closeInFile();
        return policies;
    }

    public static void openOutFile(String fileName) throws IOException{
        outputst = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)));
    }

    public static void addRecord(HashMap<Integer, InsurancePolicy> policies) throws IOException{
        for(int id : policies.keySet()) {
            outputst.writeObject(policies.get(id));
        }
    }

    public static void closeOutFile() throws IOException {
        if(outputst !=null)
            outputst.close();
    }

    public static boolean save(HashMap<Integer, InsurancePolicy> policies, String fileName) throws IOException {
        try {
            openOutFile(fileName);
            addRecord(policies);
            closeOutFile();
            return true;
        } catch (IOException error) {
            System.out.println("Error in saving the file");
            return false;
        }
    }

    public static HashMap<Integer, InsurancePolicy> loadTextFile(String fileName) throws IOException, PolicyHolderNameException {
        HashMap<Integer, InsurancePolicy> policies = new HashMap<Integer, InsurancePolicy>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line = in.readLine();
        while (line != null) {
            line = line.trim();
            String[] allPolicies = line.split("policy,");
            Car car;
            MyDate date;
            InsurancePolicy policy;
            for (int policyNum = 0 ; policyNum < allPolicies.length ; policyNum++) {
                String[] field = allPolicies[policyNum].split(",");
                switch (field[0]) {
                    case "tpip" :
                        car = new Car(field[3], CarType.valueOf(field[4]), Integer.parseInt(field[5]), Double.parseDouble(field[6]));
                        date = new MyDate(Integer.parseInt(field[9]), Integer.parseInt(field[10]), Integer.parseInt(field[11]));
                        policy = new ThirdPartyInsurancePolicy(Integer.parseInt(field[1]), field[2], car,  Integer.parseInt(field[7]), field[8], date);
                        policies.put(policy.id, policy);
                    

                    case "cip" :
                        int id = Integer.parseInt(field[1]);
                        String policyHolderName = field[2];
                        car = new Car(field[3], CarType.valueOf(field[4]), Integer.parseInt(field[5]), Double.parseDouble(field[6]));
                        date = new MyDate(Integer.parseInt(field[10]), Integer.parseInt(field[11]), Integer.parseInt(field[12]));
                        policy = new ComprehensiveInsurancePolicy(id, policyHolderName, car
                                ,  Integer.parseInt(field[7]), Integer.parseInt(field[8]), Integer.parseInt(field[9]), date);
                        policies.put(policy.id, policy);
                    
                }
            }
            line = in.readLine();
        }
        in.close();
        return policies;
    }

    public static boolean saveTextFile(HashMap<Integer, InsurancePolicy> policies, String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            for(int id : policies.keySet()) {
                out.write (policies.get(id).toDilimatedString() + "\n");
            }
            out.close();
            return true;
        } catch (IOException error) {
            System.out.println("Error in saving to a text file!");
            return false;
        }
    }

    public int getPolicyID() {
        return this.id;
    }
    
    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public Car getCar() {
        return car;
    }
    
    public void setCar(Car car) {
        this.car = car;
    }

    public void setCarModel(String model) {
        this.car.setModel(model);
    }

    public int getNumberOfClaims() {
        return numberOfClaims;
    }

    public void setNumberOfClaims(int numberOfClaims) {
        this.numberOfClaims = numberOfClaims;
    }
    
    public MyDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(MyDate expiryDate) {
        this.expiryDate = expiryDate;
    }

}
