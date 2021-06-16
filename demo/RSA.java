package demo;
import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.*;

public class RSA{
	  private BigInteger p;
	    private BigInteger q;
	    private BigInteger N;
	    private BigInteger phi;
	    private BigInteger e;
	    private BigInteger d;
	    private int bitlength = 1024;
	    private Random     r;
	    public BigInteger[] publickey = new BigInteger[2];
	    public BigInteger[] privatekey = new BigInteger[2];
	    public void generatekeys(){
	        r = new Random();
	        p = BigInteger.probablePrime(bitlength, r);
	        q = BigInteger.probablePrime(bitlength, r);
	        N = p.multiply(q);
	        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	        e = BigInteger.probablePrime(bitlength / 2, r);
	        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
	        {
	            e.add(BigInteger.ONE);
	        }
	        d = e.modInverse(phi);
	        publickey[0] = e;
	        publickey[1] = N;
	        privatekey[0] = d;
	        privatekey[1] = N;
	    }
	    RSA(){
	    	;
	    }
	    RSA(int bitlength){
	    	this.bitlength = bitlength;
	    }
	    public RSA(BigInteger e, BigInteger d, BigInteger N){
	        this.e = e;
	        this.d = d;
	        this.N = N;
	    }
		public BigInteger[] getpublickey() {
			return publickey;
		}		
		public BigInteger[] getprivatekey() {
			return privatekey;
		}
		public static String bytesToString(byte[] encrypted){
			String test = "";
		    for (byte b : encrypted){
		    	test += Byte.toString(b);
		    }
		        return test;
		}
		// Encrypt message
		public static byte[] encrypt(byte[] message, BigInteger e, BigInteger N){
			return (new BigInteger(message)).modPow(e, N).toByteArray();
		}

		// Decrypt message
		public static byte[] decrypt(byte[] message, BigInteger d, BigInteger N){
		    return (new BigInteger(message)).modPow(d, N).toByteArray();
		}

		public static String encrypt(String message, BigInteger e, BigInteger n) {
        /*
        msgbytes takes the conversion of the String message to a byte array of type charset UTF 16 Big
         */
		//this takes the string message and converts to a byte array named msgbytes
		byte []  msgbytes = message.getBytes();

		byte [] temp = (new BigInteger(msgbytes)).modPow(e,n).toByteArray();//this allows us to add the modPow to the byte array then back to a byte array found online www.sanfoundry.com

//        encrypted = new String(temp);// wrongway
        /*
            The basic encoder keeps things simple and encodes
            the input as is - without any line separation.
            The output is mapped to a set of characters in A-Z
         */
		String encrypted = Base64.getEncoder().encodeToString(temp);
		//System.out.println("encrypted string: " + encrypted);
			return encrypted;
	}

	public static String decrypt(String message, BigInteger d, BigInteger n) {
        /*
            Lets decode the string back to original form
         */
		byte[] msgbytes = Base64.getDecoder().decode(message);
		byte[] temp = (new BigInteger (msgbytes)).modPow(d,n).toByteArray();//found on sanfoundry.com only part

		String finalAnswer = new String(temp);
		//System.out.println("*******you Got This**********");
//        System.out.println("decrypted string: " + finalAnswer);
		return finalAnswer;
	}
}