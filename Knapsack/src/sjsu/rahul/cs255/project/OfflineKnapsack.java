package sjsu.rahul.cs255.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* This class gives a dynamic programming solution to the offline(traditional) knapsack problem*/
public class OfflineKnapsack {
	
	private int[][] value;
	private int[][] keep;
	private int W;
	private int n;
	private Item<Integer>[] itemList;
	/*private int wt[];
	private int val[];*/
	
	// Recursive method to calculate the optimum knapsack value	
	public int knapSack(int W, Item[] itemList, int n)
	{
		
		// Base Case
	   if (n == 0 || W == 0)
	       return 0;
	 
	   // If weight of the nth item is more than Knapsack capacity W, then
	   // this item cannot be included in the optimal solution
	   if ((Integer)itemList[n-1].getWeight() > W)
	       return knapSack(W, itemList, n-1);
	 
	   // Return the maximum of two cases: 
	   // (1) nth item included 
	   // (2) not included
	   else return Math.max ((Integer)itemList[n-1].getValue() + knapSack((int)(W-(Integer)itemList[n-1].getWeight()), itemList, n-1),
	                    knapSack(W, itemList, n-1));
	}
	
	/*public int knapSack(int W, int wt[], int itemVal[], int n)
	{
		
		// Base Case
	   if (n == 0 || W == 0)
	       return 0;
	 
	   // If weight of the nth item is more than Knapsack capacity W, then
	   // this item cannot be included in the optimal solution
	   if (wt[n-1] > W)
	       return knapSack(W, wt, itemVal, n-1);
	 
	   // Return the maximum of two cases: 
	   // (1) nth item included 
	   // (2) not included
	   else return Math.max ( itemVal[n-1] + knapSack(W-wt[n-1], wt, itemVal, n-1),
	                    knapSack(W, wt, itemVal, n-1)
	                  );
	}*/
	
	// Method to calculate the value and keep matrices for the dynamic program
	public void knapSack2(int W, Item[] itemList, int n)
	{
		value = new int[n+1][W+1];
		keep = new int[n+1][W+1];
		this.W = W;
		this.n = n;
		this.itemList = itemList;
		/*this.wt = wt;
		this.val = val;*/
	    /*
	     * Set each column in first(zeroth) row to zero
	     */
	    for(int i =0; i < n+1; i++)
	    	for(int j =0; j < W+1; j++)
	    	{	
	    		value[i][j] = 0;
	    		keep[i][j] = 0;
	    	}
        for(int i = 1; i < n+1; i++)
        {
        	for(int w = 0; w < W+1; w++)
        	{
        		//System.out.println(" i --->" + i);
        		//System.out.println(" w --->" + w);
        		if((Integer)itemList[i-1].getWeight() > w)
        		{
        			//System.out.println("In if loop");
        			value[i][w] = value[i-1][w];
        			//System.out.println("Out of if loop");
        		}
        			
        		else
        		{
        			
        			/*System.out.println("In else loop 2");
        			System.out.println("wt - w[i-1] -->" + (w - wt[i-1]) );
        			System.out.println("[i-1] -->" + (i-1) );*/
        			if((Integer)itemList[i-1].getValue()+value[i-1][w-(Integer)itemList[i-1].getWeight()] > value[i-1][w])
        			{
        				//System.out.println("In else - then if loop");
        				value[i][w] =  (Integer)itemList[i-1].getValue()+value[i-1][w-(Integer)itemList[i-1].getWeight()];
        				keep[i][w] = 1;		
        				//System.out.println("Out of else loop");
        			}
        			else
        				value[i][w] =  value[i-1][w];
        		}
        		
        	}
        }
		    
	}
	/*public void knapSack2(int W, int wt[], int val[], int n)
	{
		value = new int[n+1][W+1];
		keep = new int[n+1][W+1];
		this.W = W;
		this.n = n;
		this.wt = wt;
		this.val = val;
	    
	     * Set each column in first(zeroth) row to zero
	     
	    for(int i =0; i < n+1; i++)
	    	for(int j =0; j < W+1; j++)
	    	{	
	    		value[i][j] = 0;
	    		keep[i][j] = 0;
	    	}
        for(int i = 1; i < n+1; i++)
        {
        	for(int w = 0; w < W+1; w++)
        	{
        		System.out.println(" i --->" + i);
        		System.out.println(" w --->" + w);
        		if(wt[i-1] > w)
        		{
        			System.out.println("In if loop");
        			value[i][w] = value[i-1][w];
        			System.out.println("Out of if loop");
        		}
        			
        		else
        		{
        			
        			System.out.println("In else loop 2");
        			System.out.println("wt - w[i-1] -->" + (w - wt[i-1]) );
        			System.out.println("[i-1] -->" + (i-1) );
        			if(val[i-1]+value[i-1][w-wt[i-1]] > value[i-1][w])
        			{
        				System.out.println("In else - then if loop");
        				value[i][w] =  val[i-1]+value[i-1][w-wt[i-1]];
        				keep[i][w] = 1;		
        				System.out.println("Out of else loop");
        			}
        			else
        				value[i][w] =  value[i-1][w];
        		}
        		
        	}
        }
		    
	}*/
	
	// This method gives the optimum knapsack value from the value matrix
	public int getKnapSackValue() {
		return value[n][W];
	}
	
	// This method gives the elements contained in the optimum knapsack solution
	public List<Integer> getKnapSackItems() {
		List<Integer> selItems = new ArrayList<Integer>();
		int i = n;
		int j = W;
		while(i > 0 && j > 0)
		{		
        		if(keep[i][j] == 1)
        		{
        			//System.out.println("i ---->" + i);
            		//System.out.println("j ---->" + j);
        			selItems.add( new Integer(i));
        			j = j - itemList[i-1].getWeight();
        			i--;
        		}
        		else
        			i--; 
        	}
		return selItems;
	}
	public void printValueMatrix()
	{
		for(int i = 0; i < n+1; i++)
		{
			System.out.println("");
			for(int j = 0; j < W+1; j++)
				System.out.print(value[i][j] + " ");
		}
	}
	
	
	/*public static void main(String[] args) {
		OfflineKnapsack offKS = new OfflineKnapsack();
		int val[] = new int[]{60, 10, 120};
	    int wt[] = new int[]{10, 20, 30};
	    int  W = 50;
	    offKS.knapSack2(W, wt, val, val.length);
	    System.out.println(" Final value - KS1 --->" + offKS.knapSack(W, wt, val, val.length));
	    System.out.println(" Final value - KS2 --->" + offKS.getKnapSackValue());
	    System.out.println(" Value matrix ---------------");
	    offKS.printValueMatrix();
	    System.out.println(" Item Selected");
	    Iterator<Integer> i = offKS.getKnapSackItems().iterator();
	    while( i.hasNext())
	    	System.out.println("Item " + i.next());
		
		int wt[] = new int[]{2, 5, 6, 9, 1, 3, 22, 2, 1, 4};
	    int val[] = new int[]{8, 5, 19, 20, 7, 3, 4, 10, 11, 6};
		
	    OnlineSec olSec = new OnlineSec(21,3);
	    for( int i=0; i < val.length; i++)
	    {
	    	System.out.println("Element " + i + " selected: " +olSec.select(wt[i], val[i]));
	    	
	    }
	    olSec.printRef();
	    olSec.printSel();
	    OnlineKnapsack olKS = new OnlineKnapsack(40,21);
	    System.out.println("Knapsack online 1 algo solution");
	    for(int i =0; i < wt.length; i++)
	    {
	    	System.out.println("Element " + i + " : " +olKS.knapSack1(wt[i], val[i]));
	    }
	    System.out.println("Knapsack online 2 algo solution");
	    for(int i =0; i < wt.length; i++)
	    {
	    	System.out.println("Element " + i + " : " +olKS.knapSack2(wt[i], val[i]));
	    }
	    
	    

	}*/

}
