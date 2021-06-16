package Project2;

import java.math.BigInteger;

public class Voter {
    private String firstName;
    private String lastName;
    private String SSN;
    private String county;
    private String state;
    private Integer validationNum;

    public Voter(String fn, String ln, String ssn, String county, String state, Integer valNum){
        this.firstName = fn;
        this.lastName = ln;
        this.SSN = ssn;
        this.county = county;
        this.state = state;
        this.validationNum = valNum;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSSN() {
        return SSN;
    }

    public String getCounty() {
        return county;
    }

    public String getState() {
        return state;
    }

    public Integer getValidationNum(){
        return validationNum;
    }
}
