package uci;

import java.util.Scanner;

public class main {
	public static void main(String[] args) throws Exception
	{
		System.out.println("Enter the Value of k:");
    	Scanner sc= new Scanner(System.in);
    	int k=sc.nextInt();
    	train tr= new train();
    	for(int i=0;i<k;i++) {
    		tr.train(i,k);
    		tr.test(i,k);
    	}
    	  
        
	}

}