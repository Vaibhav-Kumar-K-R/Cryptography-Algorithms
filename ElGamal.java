import java.io.*;
import java.math.*;
import java.util.*;

public class ElGamal {
    private BigInteger p, g, e1, e2, d;
    private Random r;

    public ElGamal(int bitLength) {
        r = new Random();
        p = BigInteger.probablePrime(bitLength, r);
        g = new BigInteger("2");
        d = new BigInteger(bitLength - 2, r);
        e1 = g.modPow(d, p);
    }

    public static void main(String[] args) throws IOException {
        ElGamal elGamal = new ElGamal(1024);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plain text: ");
        String testString = sc.nextLine().toLowerCase();

        ArrayList<BigInteger[]> encrypted = elGamal.encrypt(testString);
        System.out.println("Encrypted ciphertext:");
        for (BigInteger[] pair : encrypted) {
            System.out.print( pair[0] +""+ pair[1]);
        }

        String decrypted = elGamal.decrypt(encrypted);
        System.out.println("Decrypted string: " + decrypted);
    }

    public ArrayList<BigInteger[]> encrypt(String message) {
        ArrayList<BigInteger[]> encrypted = new ArrayList<>();
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

            BigInteger r = new BigInteger(p.bitLength() - 1, this.r); 
            BigInteger C1 = g.modPow(r, p);
            BigInteger C2 = BigInteger.valueOf(charValue).multiply(e1.modPow(r, p)).mod(p);

            encrypted.add(new BigInteger[] { C1, C2 });
        }
        return encrypted;
    }

    public String decrypt(ArrayList<BigInteger[]> encryptedMessage) {
        StringBuilder decrypted = new StringBuilder();
        for (BigInteger[] encryptedPair : encryptedMessage) {
            BigInteger C1 = encryptedPair[0];
            BigInteger C2 = encryptedPair[1];

            BigInteger s = C1.modPow(d, p); 
            BigInteger sInverse = s.modInverse(p); 
            BigInteger decryptedCharValue = C2.multiply(sInverse).mod(p); 

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
}
