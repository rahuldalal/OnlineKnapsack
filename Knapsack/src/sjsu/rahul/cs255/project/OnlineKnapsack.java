package sjsu.rahul.cs255.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
//import org.apache.commons.math3.distribution.*;
//import org.apache.commons.math3.random.RandomGenerator;
import cern.jet.random.Binomial;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.engine.MersenneTwister;

public class OnlineKnapsack {
	
	private int W;
	private int n;
	private ArrayList<Integer> solution;
	OnlineSec olSec;
	private int t; //Sampling period for algorithm knapsack2
	//private int counter1;  // Counter for knapasack 1
	private int counter2; // Counter for knapasack 2
	private int k; // the randomly generated k for running the online secretary algorithm
	//private double[] wt; // To store the weight of the incoming elements
	//private double[] val; // To store the value of the incoming elements
	private double avgDensity;
	private double ksWeight;
	//private int[] knapSack1soln;
	private ArrayList<Integer> knapSack2soln;
	double totSampleValue;
	double totSampleWeight;
	Item<Integer> [] origItemList;
	Binomial binDist;
	RandomEngine randomGenerator;
	OnlineSelect olSelect;
	
	public OnlineKnapsack(int W, int n)
	{
		this.W = W;
		this.n = n;
		Random randGen = new Random();
		int randInt = randGen.nextInt(5);
		//System.out.println("Random integer --->" + randInt);
		k = (int)Math.pow(3, randInt);
		//k=81;
		//System.out.println("k ---->" + k);
		if(k== 81)
		{
			MersenneTwister randomGenerator = new MersenneTwister();
			binDist = new Binomial(n,0.5,randomGenerator);	
//			if(binDist == null)
//				System.out.println("Bin dist object is null ");
//			System.out.println(" Before bin rand gen");
			int rand = binDist.nextInt();
//			System.out.println(" After bin rand gen");
			//System.out.println("Binomial random no. generated -->" + rand);
			olSelect = new OnlineSelect(n, rand, W);
			
		}
		// Set up the k-online hiring algorithm		
		olSec = new OnlineSec(n,k);
		// Set up parameters for knapsack2
		this.t =(int)(n/Math.E);
		//counter1 = 0;
		this.counter2 = 0;
		//wt = new double[n];
		//val = new double[n];
		avgDensity = 0;
		ksWeight = 0;
		totSampleValue = 0;
		totSampleWeight = 0;
		knapSack2soln = new ArrayList<Integer>();
		origItemList = new Item[n];
	}
	
	public void knapSack1Run(int w, int v)
	{
			if(k ==81)
				olSelect.select((double)w,(double)v);
			origItemList[olSec.getCurrent()] = new Item(w,v);
			knapSack1(w,v);
		
			
			
	}
	public boolean knapSack1(int w, int v)
	{
		
		double reScWt = w/W; // Rescale the weight to run the k-online hiring algorithm
		double density = (double)v/w; 
		
		if(olSec.select(reScWt, density))
		{
			//System.out.println("In knapsack if loop");
			//System.out.println("Rescaled weight -->" + w);
			if(reScWt < (1/k))
			{
				//System.out.println("Wt less than 1/k");
				//knapSack1soln[counter1] = olSec.getCurrent();
				//counter1++;
				return true;
			}
				
		}
			
		return false;	
	}
	
	public double getKnapSack1Value()
	{
		double value = 0;
		int[] selected;
		if(k==81)
			selected = olSelect.getSelArray();
		else
			selected = olSec.getSel();
		//System.out.println("Before for loop");
		//System.out.println("Length of sel array -->" + selected.length);
		for(int i = 0; i < selected.length; i++ )
		{
			//System.out.println("Value -->" + origItemList[selected[i]].getValue());
			value += origItemList[selected[i]].getValue();
		}
		//System.out.println("Tot value -->" + value);
		return value;
	}
	public boolean knapSack2(int w, int v)
	{
		//System.out.println("In Knapsack 2");
		//System.out.println("Current element --->" + olSec.getCurrent() );
		
		
	//	wt[counter2] = w;
	//	val[counter2] = v;
		double density = (double)v/w;
		boolean selected = false;
		
		if(olSec.getCurrent() <= t-1 )
		{
			totSampleValue += v;
			totSampleWeight += w;
			if(olSec.getCurrent() == t-1)
			{
				//System.out.println("Total density -->" + totDensity);
				//System.out.println("Current element>" + olSec.getCurrent());
				
				avgDensity = (totSampleValue)/(totSampleWeight);
			}
				
			//System.out.println("Average density -->" + avgDensity);
		}
			
		else if(olSec.getCurrent() > t-1 ){
			
			if(density > avgDensity && (ksWeight + w) <= W)
			{ 
				//System.out.println("Average density -->" + avgDensity);
				//System.out.println("Density" + density);
				selected = true;
				ksWeight += w;
				knapSack2soln.add(olSec.getCurrent());
				//System.out.println("Knapsack weight -->" + ksWeight);
			}
			
		}
			
				
		counter2++;
		return selected;		
	}
	public double getKnapSack2Value()
	{
		double value = 0;
		System.out.println("Solutin list size" + knapSack2soln.size() );
		Iterator i = knapSack2soln.iterator();
		int index;
		while(i.hasNext())
		{
			index = ((Integer)i.next()).intValue();
			System.out.println("Element value -->" + origItemList[index].getValue() );
			value += origItemList[index].getValue();
			System.out.println("Total value -->" + value);
		}
		return value;
	}
	
	public int[] getKnapSack1soln() {
		if(k == 81)
			return olSelect.getSelArray();
		else
			return olSec.getSel();
	}

	public ArrayList<Integer> getKnapSack2soln() {
		return knapSack2soln;
	}

	public void printSolKnapSack1()
	{
		int[] solution = getKnapSack1soln();
		if(solution == null)
			System.out.print("Solution is null");
		for(int i = 0; i < solution.length; i++)
		{
			System.out.print(solution[i] + " ");
		}
	}
	public void printSolKnapSack2()
	{
		System.out.println("Ttoal knapsack weight for modified algorithm"+ksWeight);
		Iterator i = knapSack2soln.iterator();
		int index;
		while(i.hasNext())
		{
			System.out.print((Integer)i.next() + " ");
		}
	}

}
