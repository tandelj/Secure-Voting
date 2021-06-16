package Project2;

import Project2.CLA;

import java.util.Scanner;

public class Test {
    static String fn;
    static String ln;
    static String ssn;
    static String county;
    static String state;

    public static void main(String[] args) {
        CLA cla = new CLA();
        Scanner input = new Scanner(System.in);
        System.out.println("Thanks for visiting the CLA:  Lets get you registered");
        System.out.println("Please Enter Your First Name: ");
        fn = input.nextLine();
        System.out.println("Please Enter your Last Name: ");
        ln = input.nextLine();
        System.out.println("Please enter your SSN: ");
        ssn = input.nextLine();
        System.out.println("Please enter your county: ");
        county = input.nextLine();
        System.out.println("Please enter your state: ");
        state = input.nextLine();

        System.out.println("info: " + fn + " " + ln + " " + ssn + " " + county + " " + state);

        System.out.println(cla.numOfVoters);
        cla.addVotersToList(fn, ln, ssn, county, state);

        System.out.println(cla.numOfVoters);

    }
}


