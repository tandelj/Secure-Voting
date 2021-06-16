package demo;
import java.math.BigInteger;
import java.util.*;
import demo.RSA.*;

public class TestRSA {
	 public static void main(String[] args) throws Exception
	    {
	        Scanner in = new Scanner(System.in);
	        //System.out.println("Enter BitLength: ");
	        //int bitlen = Integer.parseInt(in.nextLine());
	        RSA rsa = new RSA();
	        rsa.generatekeys();
	       //DataInputStream in = new DataInputStream(System.in);
	    
	        String teststring;
	        System.out.println("Enter the Message: ");
	        teststring = in.nextLine();
	        System.out.println("Encrypting String: " + teststring);
	        System.out.println("String in Bytes: " + rsa.bytesToString(teststring.getBytes()));
	        BigInteger[] publickey = new BigInteger[2];
	        BigInteger[] privatekey = new BigInteger[2];
	        publickey = rsa.getpublickey();
	        privatekey = rsa.getprivatekey();
	        
	        // encrypt
	        byte[] encrypted = rsa.encrypt(teststring.getBytes(), publickey[0], publickey[1]);
	        // decrypt
	        byte[] decrypted = rsa.decrypt(encrypted, privatekey[0], privatekey[1]);
	        System.out.println("Decrypting Bytes: " + rsa.bytesToString(decrypted));
	        System.out.println("Decrypted String: " + new String(decrypted));
	        
	        System.out.println("Public Key: \n" + publickey[0]);
	        System.out.println("Private Key: \n" + privatekey[0]);
	        System.out.println("N: \n" + publickey[1]);

	    } 
} 
