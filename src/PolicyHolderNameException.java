public class PolicyHolderNameException extends Exception {
    String text;
    public PolicyHolderNameException(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return "PolicyHolderNameException Occured: " + this.text;
    }
}
