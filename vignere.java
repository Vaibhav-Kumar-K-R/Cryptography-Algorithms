import java.util.*;
public class vignere {
    public static String encrypt(String message,String key){
        message=message.toLowerCase();
        key=key.toLowerCase();
        String cipherResult="";
        for(int i=0;i<message.length();i++){
            char ch=message.charAt(i);
            if(ch==' '){
                cipherResult+=' ';
                continue;
            }
            int P=ch-'a';
            int K=key.charAt(i%key.length())-'a';
            int C=(P+K)%26;
            cipherResult+=(char)(C+97);      
        }
        return cipherResult.toUpperCase();
    }

    public static String decrypt (String cipher,String key){
        cipher=cipher.toUpperCase();
        key=key.toLowerCase();
        String message="";
        for(int i=0;i<cipher.length();i++){
              char ch=cipher.charAt(i);
              if(ch==' '){
                  message+=' ';
                  continue;
              }
              int C=ch-'A';
              int K=key.charAt(i%key.length())-'a';
              int P=(C-K+26)%26;
              message+=(char)(P+97);
        }
        return message;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String message="";
        String key="";
        System.out.println("Enter the message:");
        message=sc.nextLine();
        System.out.println("Enter the key:");
        key=sc.nextLine();
        System.out.println();
        System.out.println("The encrypted message is: "+encrypt(message,key));
        System.out.println();
        System.out.println("The decrypted message is: "+decrypt(encrypt(message,key),key));
        sc.close();
    }
}
