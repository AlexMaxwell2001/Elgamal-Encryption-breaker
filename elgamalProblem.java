import java.util.Scanner; 
public class elgamalProblem
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        String s1= sc.nextLine();
        String s2 = sc.nextLine();
        sc.close();
        String ar1 [] = s1.split(" ");
        String ar2[] = s2.split(" ");
        Long p = Long.parseLong(ar1[0]);
        Long g= Long.parseLong(ar1[1]);
        Long gxmodp = Long.parseLong(ar1[2]);
        Long gymodp = Long.parseLong(ar2[0]);
        Long mgymodp = Long.parseLong(ar2[1]);
        int x = findPrivateKey(p,g,gxmodp);
        System.out.println("Private Key: " + x);
        if(x>0)
        {
        	 long message = findMessage(p,gymodp, mgymodp , x);
        	 System.out.println("Message: " + message);
        }
        else
        {
         	System.out.println("Private Key Not Found!!!");
        }
    }
    public static int findPrivateKey(long p , long g , long gxmodp)
    {
        for(int x = 2; x < p; x++) 
        {
            if(modPow(g,x,p)== gxmodp)
            {
            	return x;
            }
        }
        return -1;
    }
    public static long findMessage(long p ,long c1 , long c2, int x)
    {
        long temp = 0 ; 
        long message = 0 ;
        temp = modPow(c1,(p-1-x),p);
        message = modMult(temp,c2,p); 
        return message;
    }
    public static long modPow(long number, long power, long modulus)
    {
    	//raises a number to a power with the given modulus
    	//when raising a number to a power, the number quickly becomes too large to handle
    	//you need to multiply numbers in such a way that the result is consistently moduloed to keep it in the range
    	//however you want the algorithm to work quickly - having a multiplication loop would result in an O(n) algorithm!
    	//the trick is to use recursion - keep breaking the problem down into smaller pieces and use the modMult method to join them back together
    	 if(power==0) 
    	  {
    		 return 1;
    	 }
    	 else if (power%2==0) 
    	 {
	    	 long halfpower=modPow(number, power/2, modulus);
	    	 return modMult(halfpower,halfpower,modulus);
	    }
    	else
    	{
	    	 long halfpower=modPow(number, power/2, modulus);
	    	 long firstbit = modMult(halfpower,halfpower,modulus);
	    	 return modMult(firstbit,number,modulus);
	    }
	 }
    public static long modMult(long first, long second, long modulus)
    {
    	//multiplies the first number by the second number with the given modulus
    	//a long can have a maximum of 19 digits. Therefore, if you're multiplying two ten digits numbers the usual way, things will go wrong
    	//you need to multiply numbers in such a way that the result is consistently moduloed to keep it in the range
    	//however you want the algorithm to work quickly - having an addition loop would result in an O(n) algorithm!
    	//the trick is to use recursion - keep breaking down the multiplication into smaller pieces and mod each of the pieces individually
    	 if(second==0) {
    		 return 0;
    	 }
    	 else if (second%2==0) 
    	 {
	    	 long half=modMult(first, second/2, modulus);
	    	 return (half+half)%modulus;
	     }
    	 else
    	 {
	    	 long half=modMult(first, second/2, modulus);
	    	 return (half+half+first)%modulus;
    	 }
    }

}
