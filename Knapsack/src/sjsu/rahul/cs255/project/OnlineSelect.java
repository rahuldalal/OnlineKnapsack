package sjsu.rahul.cs255.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sjsu.dalal.cs146.project1.part2.Quicksort;



public class OnlineSelect {
	
	private int n; // No of items
	private int t; // Sampling period
	private int k; // No. of candidates to be hired
	private ArrayList<Item<Double>> ref; // Reference set of candidates
	private ArrayList<Integer> sel; // Maintains a list of the indices of selected items
	private int selCounter; // counter keeping track of no. of candidates selected
	private int current; // counter keeping track of the current item (no.)
	private Item<Double>[] totalItems; // Array of the total items
	private double totSampleValue;
	private double totSampleWeight;
	private double avgSampleDensity;
	private double thresholdDensity;
	private int W;
	private int[] selArray;
	private int ksWt;
	
	
	public OnlineSelect(int n, int t, int W)
	{
		
		this.n = n;
		this.t = t;
		this.current = 0;
		this.ref = new ArrayList(t);
		this.sel = new ArrayList<Integer>();
		this.selCounter = 0;
		this.totalItems = new Item[n];
		this.W = W;
		this.ksWt = 0;
		//System.out.println("t --->" + t);
	}
	public ArrayList<Item<Double>> getRef() {
		return ref;
	}
	
	public ArrayList<Integer> getSel() {
		return sel;
	}
	/*
	public boolean createReference(int w, int v) 
	{
		wt[current] = w;
		val[current] = v;
		boolean refAdd = false;
		if(current <= t)
		{
			ref[current] = current;
			refAdd = true;
			if(current == t)
				QS.qs1(ref,0,ref.length-1);
		}
		else if(current > t)
		{
			if(v > val[ref[0]])
			{
				ref[0] = current;
				refAdd = true;
				QS.qs1(ref,0,ref.length-1);
				
			}
				
		}
		current++;
		return refAdd;
		
	}*/
	public boolean select(double weight, double value)
	{
		//System.out.println("In select function");
		//System.out.println("Current Value --->" + current);
		totalItems[current] = new Item<Double>(weight, value);
		boolean selected = false;
		double reScWt = weight/W; // Rescale the weight to run the k-online hiring algorithm
		double density = (double)value/weight; 
		if(current <= t-1 )
		{
			totSampleValue += value;
			totSampleWeight += weight;
			if(current == t-1 )
			{
				avgSampleDensity = (totSampleValue)/(totSampleWeight);
				thresholdDensity = Math.pow(avgSampleDensity, .5);
			}
		}
		else if(current > t- 1 )
		{
//			System.out.println("Element no. -->" + current);
//			System.out.println("Density -->" + density);
//			System.out.println("Threshold density -->" + thresholdDensity);
//			System.out.println("Rescaled weight -->" + reScWt);
//			System.out.println(" 3^-4 -->" +  Math.pow(3,-4));
//			System.out.println(" Double compare value" +  Double.compare(reScWt, Math.pow(3,-4)));
			//System.out.println(" Sub compare value" +  (reScWt - Math.pow(3,-4)));
//			
			if( density > thresholdDensity );
			{
				if(((reScWt - Math.pow(3,-4)) < 0.01))
				{
					if((ksWt + weight) <= W)
					{
						//System.out.println("True");
						//System.out.println(" in if loop ****Sub compare value" +  (reScWt - Math.pow(3,-4)));
						//System.out.println("Element selected");
						sel.add(current);
						ksWt += weight;
						//System.out.println("KS weight -->" + ksWt);
						//System.out.println("W -->" + W);
					}
					
				}
				
				
				
			}		
		}
		current++;
		return selected;		
	}

	public void printRef()
	{
		System.out.println(" Reference array");
		Iterator<Item<Double>> i = ref.iterator();
		while(i.hasNext())
			System.out.print(" " + i.next().toString() );
		System.out.print("\n ");
		
	}
	public void printSel()
	{
		System.out.println("Selected array indices");
		Iterator<Integer> i = sel.iterator();
		while(i.hasNext())
			System.out.print(" " + i.next()+" ," );
		System.out.print("\n ");
	}
	
	public int getCurrent() {
		return current;
	}
	public Item<Double>[] getTotalItems() {
		return totalItems;
	}
	public static int[] convertIntegers(List<Integer> integers)
	{
		if(integers != null && integers.size() > 0)
		{
		    int[] ret = new int[integers.size()];
		   // System.out.println("Size of selected list -->" + integers.size());
		    Iterator<Integer> iterator = integers.iterator();
		   
		    for (int i = 0; i < ret.length; i++)
		    {
		    		int index = iterator.next();
		    		//System.out.println("Not null");
		    		//System.out.println("Iterator value -->" + index);
		    		ret[i] = index;
		    		//System.out.println(" Set ret"); 		
		    }
		    return ret;
		}
		else
			return null;
	}
	
	public int[] getSelArray() {
		
		selArray = convertIntegers(sel);
		//System.out.println("sel array length" + selArray.length);
		return selArray;
	}
	
	
}
