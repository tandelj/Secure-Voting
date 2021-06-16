package Project2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CLA {
    protected Integer numOfVoters;// counters
    protected ArrayList<Voter> voters; //this should be dynamic data structure array not good for this
    protected Integer validationNumber;
    protected ArrayList<Integer> previousNumbers;

   public CLA(){
       numOfVoters = 0;
       voters = new ArrayList<Voter>();//we need to think about this



   }
    protected void votersToCTF(){

    }

    public void addVotersToList(String fn, String ln, String ssn, String county, String state){
       validationNumber = createValidationNumber();//call validation method
       voters.add(new Voter(fn, ln,ssn, county, state, validationNumber));
       numOfVoters++;
    }

    protected Integer createValidationNumber(){
       //validation number needs to random
       //need to store previous validation numbers to avoid duplicates need a an array list of numbers
       //assign number which means just return it.
        previousNumbers = new ArrayList<Integer>();//made this a global variable so we can use it later
        previousNumbers.add(0);
        boolean doesNotExist = false;//will makes sure the validation number doesnt exist
        do {
            Integer validationNumber = (int) (100 * Math.random()) + 1;//create validation number
            for (Integer x : previousNumbers) {//enhanced for loop will traverse the Arraylist to check numbers
                if (validationNumber.equals(x)) {//checks to see if number already exists if it does break out of loop
                    break;
                } else {
                    previousNumbers.add(validationNumber);//Add validation number
                    doesNotExist = true;//make doesNotExist True
                    return validationNumber;//return validationNumber;
                }
            }
        }while(doesNotExist == false);
       return 0;
    }



}
