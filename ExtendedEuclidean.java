import java.util.*;
public class ExtendedEuclidean {
    public static int gcd(int a,int b){
        if(b==0)
            return a;
        return gcd(b,a%b);
    }
    
    public static int extendedEuclidean(int a,int n){
        if(gcd(n,a)!=1){
            System.out.println("gcd of "+a+" and "+n+" is not 1 !!");
            return -1;
        }
        int r1=n;
        int r2=a;
        int t1=0;
        int t2=1;

        
        while(r2>0){

           int q=r1/r2;
           int r=r1-(q*r2);
           r1=r2;
           r2=r;
           
           int t=t1-(q*t2);
           t1=t2;
           t2=t;

           if(r1==1){
           return  t1<0?t1+n:t1;
           }

        }
        return -1;
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the value of a:");
        int a=sc.nextInt();
        System.out.println("Enter the value of n:");
        int n=sc.nextInt();
        int result=extendedEuclidean(a,n);
        if(result!=-1)
            System.out.println("The inverse of "+a+" wrt mod "+n+" is "+result+" i.e., "+a+"*"+result+" mod "+n+" = 1 mod "+n);
            
            else
        System.out.println("Inverse does not exist!!");
        sc.close();
    }
}
