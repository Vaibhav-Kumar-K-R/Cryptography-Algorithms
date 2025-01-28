import java.io.*;
import java.math.*;
import java.util.*;

public class RSA {
    private BigInteger p, q, N, phi, e, d;
    private int bitLength = 1024;
    private Random r;

    public RSA() {
        r = new Random();
        p = BigInteger.probablePrime(bitLength, r);
        q = BigInteger.probablePrime(bitLength, r);
        System.out.println("Prime number p is " + p);
        System.out.println("Prime number q is " + q);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitLength / 2, r);

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e = e.add(BigInteger.ONE);
        }

        
        d = e.modInverse(phi);
    
    }

    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plain text: ");
        String testString = sc.nextLine().toLowerCase();

        System.out.println("Encrypting string: " + testString);
        ArrayList<BigInteger> encrypted = rsa.encrypt(testString);

        String decrypted = rsa.decrypt(encrypted);
        System.out.println("Decrypted string: " + decrypted);
    }

    public ArrayList<BigInteger> encrypt(String message) {
        ArrayList<BigInteger> encrypted = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            int charValue;

            if (Character.isLetter(c)) {
                charValue = c - 'a'; 
            } else if (Character.isDigit(c)) {
                charValue = c - '0' + 26; 
            } else if (c == ' ') {
                charValue = 36; 
            } else {
                throw new IllegalArgumentException("Only lowercase letters, digits, and spaces are allowed.");
            }

            BigInteger encryptedChar = fastExponentiation(BigInteger.valueOf(charValue), e, N);
            encrypted.add(encryptedChar);
        }
        return encrypted;
    }

    public String decrypt(ArrayList<BigInteger> encryptedMessage) {
        StringBuilder decrypted = new StringBuilder();
        for (BigInteger encryptedChar : encryptedMessage) {
            BigInteger decryptedCharValue = fastExponentiation(encryptedChar, d, N);
            int charValue = decryptedCharValue.intValue();

            if (charValue >= 0 && charValue <= 25) {
                decrypted.append((char) (charValue + 'a')); 
            } else if (charValue >= 26 && charValue <= 35) {
                decrypted.append((char) (charValue - 26 + '0'));
            } else if (charValue == 36) {
                decrypted.append(' '); 
            } else {
                throw new IllegalArgumentException("Decrypted value out of range.");
            }
        }
        return decrypted.toString();
    }

    private BigInteger fastExponentiation(BigInteger base, BigInteger exponent, BigInteger modulus) {
        BigInteger result = BigInteger.ONE;
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                result = result.multiply(base).mod(modulus);
            }
            base = base.multiply(base).mod(modulus);
            exponent = exponent.divide(BigInteger.TWO);
        }
        return result;
    }
}
