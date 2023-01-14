package uci;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws Exception
	{	
	    ArrayList <person> person= new ArrayList<person>();
		 Scanner sc= new Scanner(new File("D:\\5th sem\\DBMS-2\\dataset.csv"));
	        while(sc.hasNext())
	        {
	            String temp=sc.nextLine();
	            String[] s=temp.split(",");
	            person p=new person(Integer.parseInt(s[0]),s[1],Integer.parseInt(s[2]),(s[3]));
	            person.add(p);
	        }
	        Collections.shuffle(person);
		double average,sum=0;
		System.out.println("Enter the Value of k:");
    	Scanner sc1= new Scanner(System.in);
    	int k=sc1.nextInt();
    	train tr= new train();
    	for(int i=0;i<k;i++) {
    		tr.train(i,k,person);
    		sum=sum+tr.test(i,k,person);
    		
    	}
    	average=sum/k;
    	System.out.println(average);
    	  
        
	}

}