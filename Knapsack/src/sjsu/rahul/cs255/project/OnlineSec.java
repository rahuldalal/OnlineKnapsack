package sjsu.rahul.cs255.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sjsu.dalal.cs146.project1.part2.Quicksort;


/*
 * This class gives a (1/e) competitive approximate solution to the k-secretary problem
 * */
public class OnlineSec {
	
	private int n; // No of items
	private int t; // Sampling period
	private int k; // No. of candidates to be hired
	private ArrayList<Item<Double>> ref; // Reference set of candidates
	private int[] sel; // Maintains a list of the indices of selected items
	private int selCounter; // counter keeping track of no. of candidates selected
	private int current; // counter keeping track of the current item (no.)
	private Item<Double>[] totalItems; // Array of the total items
	
	
	public OnlineSec(int n, int k)
	{
		
		this.n = n;
		this.t =(int)(n/Math.E);
		this.k=k;
		//if(k > t) throw new RuntimeException("This program works only for k < n/e");
		if(k > t) return;
		this.current = 0;
		this.ref = new ArrayList(t);
		this.sel = new int[k];
		this.selCounter = 0;
		this.totalItems = new Item[n];
		//System.out.println("t --->" + t);
	}
	public ArrayList<Item<Double>> getRef() {
		return ref;
	}
	
	public int[] getSel() {
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
		if(current <= t-1 )
		{
			//System.out.println(" current <= t-1 ");
			ref.add(new Item<Double>(weight, value));
			if(current == t-1 )
			{
				//System.out.println("Before collection sort");
				Collections.sort(ref,new CompareItem());
				//System.out.println("After collection sort");
				
			}
				
		}
		else if(current > t-1 )
		{
			if(selCounter < k && value > ref.get(0).getValue())
			{
					sel[selCounter] = current;
					selCounter++;
					selected = true;
					if(ref.size() > 1)
						ref.remove(0);
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
		System.out.println("Selected array");
		System.out.println(" Select array length" + sel.length);
		for(int i =0; i < sel.length; i++)
		{
			Item item = totalItems[sel[i]];
			System.out.println(item.toString());
			//System.out.println(sel[i]);
		}
	}
	
	public int getCurrent() {
		return current;
	}
	public Item<Double>[] getTotalItems() {
		return totalItems;
	}
	
}
