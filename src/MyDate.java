import java.io.Serializable;
import java.util.Scanner;

public class MyDate implements Cloneable, Comparable<MyDate>, Serializable {

    public static Scanner scanner = new Scanner(System.in);

    protected int year;
    protected int month;
    protected int day;

    public MyDate(int year, int month, int day) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public MyDate(MyDate myDate) {
        this.year = myDate.year;
        this.month = myDate.month;
        this.day = myDate.day;
    }

    public String toDilimatedString() {
        return this.year + "," + this.month + "," + this.day;
    }

    @Override
    public String toString() {
        return "year: " + year + ", month: " + month +", day: " + day;
    }

    @Override
    public MyDate clone() throws CloneNotSupportedException {
        return (MyDate) super.clone();
    }

    @Override
    public int compareTo(MyDate myDate) {
        if(this.year > myDate.year) {
            return 1;
        } else if(this.year < myDate.year) {
            return -1;
        } else {
            if(this.month > myDate.month) {
                return 1;
            } else if(this.month < myDate.month) {
                return -1;
            } else {
                if(this.day > myDate.day) {
                    return 1;
                } else if(this.day < myDate.day) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    public Boolean isExpired(MyDate expiryDate) {
        if(this.year < expiryDate.year) {
            return true;
        } if(this.year < expiryDate.year && this.month < expiryDate.month) {
            return true;
        } if(this.year < expiryDate.year && this.month < expiryDate.month && this.day < expiryDate.day) {
            return true;
        }
        return false;
    }

    public static MyDate createDate() {
        System.out.println("Please enter the Year:");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the Month:");
        int month = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Please enter the Day:");
        int day = scanner.nextInt();
        scanner.nextLine();
        return new MyDate(year, month, day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
