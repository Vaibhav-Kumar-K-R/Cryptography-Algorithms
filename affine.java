import java.util.*;
class affine{

    public static int gcd(int a,int b){
        if(b==0)
            return a;
        return gcd(b,a%b);
    }
    public static int modinverse(int b ){
        if(gcd(26,b)!=1){
            System.out.println("gcd is not 1 !!");
            return -1;
        }
        int r1=26;
        int r2=b;
        int t1=0;
        int t2=1;
        int  b_inverse=0;
        while(r2>0){
            int q=r1/r2;
           int r=r1-(q*r2);
           r1=r2;
           r2=r;
           
           int t=t1-(q*t2);
           t1=t2;
           t2=t;

           if(r1==1){
            b_inverse=t1;
            break;
           }
        }
        return b_inverse<0?b_inverse+26:b_inverse;
    }
    public static String encrypt(String message,int a,int b){
        message=message.toLowerCase();//message is always given in lower case characters
        String cipherResult="";
        for(int i=0;i<message.length();i++){
            int P=message.charAt(i)-'a';
            int C=(P*a+b)%26;
            cipherResult+=(char)(C+97);      
        }
        return cipherResult.toUpperCase();
    }

    public static String decrypt (String cipher,int a,int b){
        cipher=cipher.toUpperCase();//the cipher is always given in upper case characters
        String message="";
        int a_inverse=modinverse(a);
        if(a_inverse==-1){
            return "Inverse does not exist!!";
        }
        for(int i=0;i<cipher.length();i++){
               int C=cipher.charAt(i)-'A';
               int P=(a_inverse*(C-b)%26);
              P=P<0?P+26:P;
              message+=(char)(97+P);
        }
      return message.toLowerCase();
    }

    public static void main(String args[]){
     Scanner sc=new Scanner(System.in);
     System.out.println("Enter the value of a");
     int a=sc.nextInt();
     System.out.println("Enter the value of b");
     int b=sc.nextInt();
       if(modinverse(a)==-1){
           System.out.println( "Inverse does not exist!!");;
            sc.close();
            return;
        }
     System.out.println("Enter the message:");
     String message=sc.next();
     System.out.println(encrypt(message, a, b));
     System.out.println("Enter the cipher obtained on encryption:");
     String cipher=sc.next();
     System.out.println(decrypt(cipher, a, b));
     sc.close();
      
    }
}