package sjsu.rahul.cs255.project;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TestKnapSack {

	@Test
	public void testCompareItemSort()
	{
		ArrayList<Item<Double>> itemList = new ArrayList<Item<Double>>(Arrays.asList(setup1()));
		Iterator<Item<Double>> i = itemList.iterator();
		System.out.println("Before Sorting");
		while(i.hasNext())
			System.out.print(" " + i.next().toString() );
		System.out.print("\n ");
		System.out.println("After Sorting");
		Collections.sort(itemList, new CompareItem());
		i = itemList.iterator();
		while(i.hasNext())
			System.out.print(" " + i.next().toString() );
		System.out.print("\n ");
		ArrayList<Item<Double>> solution = new ArrayList<Item<Double>>();
		solution.add(new Item<Double>(52.0,10.0));
		solution.add(new Item<Double>(48.0,11.0));
		solution.add(new Item<Double>(18.0,12.0));
		solution.add(new Item<Double>(24.0,15.0));
		solution.add(new Item<Double>(30.0,19.0));
		solution.add(new Item<Double>(7.0,20.0));
		solution.add(new Item<Double>(23.0,30.0));
		solution.add(new Item<Double>(32.0,32.0));
		solution.add(new Item<Double>(13.0,35.0));
		solution.add(new Item<Double>(39.0,40.0));
		solution.add(new Item<Double>(73.0,44.0));
		solution.add(new Item<Double>(68.0,45.0));
		solution.add(new Item<Double>(4.0,50.0));
		solution.add(new Item<Double>(50.0,60.0));
		solution.add(new Item<Double>(15.0,64.0));
		solution.add(new Item<Double>(27.0,67.0));
		solution.add(new Item<Double>(11.0,70.0));
		solution.add(new Item<Double>(42.0,72.0));
		solution.add(new Item<Double>(43.0,75.0));
		solution.add(new Item<Double>(22.0,80.0));
		solution.add(new Item<Double>(153.0,200.0));
		i = itemList.iterator();
		Iterator<Item<Double>> j = solution.iterator();
		while(j.hasNext())
		{
			Item<Double> item1 = i.next();
			Item<Double> item2 = j.next();
			assertEquals(item1.getValue(),item2.getValue(),0.01);
			assertEquals(item1.getWeight(),item2.getWeight(),0.01);
		}
		
		
	}
	@Test
	public void testOnlineSec()
	{
		OnlineSec olSec = new OnlineSec(21,3);
		ArrayList<Item<Double>> itemList = new ArrayList<Item<Double>>(Arrays.asList(setup1()));
		Iterator<Item<Double>> i = itemList.iterator();
		Item<Double> item;
		while(i.hasNext())
		{
			item = i.next();
			olSec.select(item.getWeight(), item.getValue());
		}
		olSec.printSel();
		int[] solution = {9,13,14};
		assertEquals(olSec.getSel()[0], solution[0]);
		assertEquals(olSec.getSel()[1], solution[1]);
		assertEquals(olSec.getSel()[2], solution[2]);
	}
	public Item<Double>[] setup1()
	{
		double wt[] = new double[]{13, 153, 50, 15, 68, 27, 39, 23, 52, 11, 32, 24, 48, 73, 42, 43, 22, 7, 18, 4, 30};
		double val[] = new double[]{35, 200, 60, 64, 45, 67, 40, 30, 10, 70, 32, 15, 11, 44, 72, 75, 80, 20, 12, 50, 19};
		Item<Double>[] itemList = new Item[21];
	    for(int i=0; i < 21; i++)
			itemList[i] = new Item<Double>(wt[i],val[i]);
	    return itemList;
	}
	
	// Setup 100 elements with equal and sorted in ascending order
	public Item<Integer>[] setup2()
	{
		Item<Integer>[] itemList = new Item[100];
		for(int i =0; i < 100; i++)
			itemList[i] = new Item<Integer>(i,i);
		return itemList;
	}
	
	// Setup 100 elements with equal and sorted in descending order
	public Item<Integer>[] setup3()
	{
		Item<Integer>[] itemList = new Item[100];
		for(int i = 99; i >= 0; i--)
			itemList[i] = new Item<Integer>(i,i);
		return itemList;
	}
	
	//Setup a randomly generated itemList with no. of elements, weight range and value range as input
	public Item<Integer>[] setup4(int n, int weightRange, int valueRange)
	{
		Random  gen  = new Random(); 
		Item<Integer>[] itemList = new Item[n];
		for (int i=0; i < n; i++)
			itemList[i] = new Item<Integer>( gen.nextInt(weightRange + 1), gen.nextInt(valueRange + 1));
		return itemList;
	}
	
	@Test
	/*public void testOfflineKnapsack()
	{
		OfflineKnapsack offKnapSack = new OfflineKnapsack();
		Item[] itemList1 = {new Item(23,92),new Item(31,57), new Item(29,49), new Item(44,68), new Item(53,60), new Item(38,43), new Item(63,67), new Item(85,84), new Item(89,87), new Item(82,72) };
		offKnapSack.knapSack2(165, itemList1, 10);
		assertEquals(offKnapSack.getKnapSackValue(),309);
		//System.out.println("Knapsack value -->"+offKnapSack.getKnapSackValue());
		List<Integer> selList1 = Arrays.asList(0,1,2,3,5);
		assertEquals(selList1,offKnapSack.getKnapSackItems());
		
		
	}*/
	public void testOfflineKnapsack()
	{
		OfflineKnapsack offKnapSack = new OfflineKnapsack();
		Item[] itemList1 = {new Item(2,12),new Item(1,10), new Item(3,20), new Item(2,15)};
		offKnapSack.knapSack2(5, itemList1, 4);
		assertEquals(offKnapSack.getKnapSackValue(),37);
		System.out.println("Algorithm gives correct Knapsack value");
		List<Integer> selList1 = Arrays.asList(4,2,1);
		assertEquals(selList1,offKnapSack.getKnapSackItems());
		System.out.println("Algorithm gives correct Knapsack itemlist");
		
		
	}
	
	public void compare(int W, int n,int weightRange, int valueRange)
	{
		long startOffKs, endOffKs, startOnKs1, endOnKs1, startOnKs2, endOnKs2;
		OfflineKnapsack offKnapSack = new OfflineKnapsack();
		OnlineKnapsack onKnapsack = new OnlineKnapsack(W,n);
		Item<Integer>[] itemList = setup4(n,weightRange,valueRange);
		startOffKs = System.currentTimeMillis();
		offKnapSack.knapSack2(W, itemList, n);
		endOffKs = System.currentTimeMillis();
		System.out.println("Value of the offline Knapsack -->"+offKnapSack.getKnapSackValue());
		startOnKs1 = System.currentTimeMillis();
		for(int i =0; i < itemList.length; i++)
		{
			onKnapsack.knapSack1Run(itemList[i].getWeight(), itemList[i].getValue());
			onKnapsack.knapSack2(itemList[i].getWeight(), itemList[i].getValue());
		}
			
		endOnKs1 = System.currentTimeMillis();
		startOnKs2 = System.currentTimeMillis();
		endOnKs2 = System.currentTimeMillis();
		//System.out.println("Solution array for Knapsack 1");
		//onKnapsack.printSolKnapSack1();
		System.out.println("Value of the online Knapsack original algo-->"+onKnapsack.getKnapSack1Value());
		System.out.println("Value of the online Knapsack modified algo-->"+onKnapsack.getKnapSack2Value());
		System.out.println("--------------------------------------------------------------------------- ");
		System.out.println("Time of the original algo-->"+ (endOffKs - startOffKs));
		System.out.println("Time of the online Knapsack original algo-->" + (endOnKs1 - startOnKs1));
		System.out.println("Time of the online Knapsack modified algo-->" + (endOnKs2 - startOnKs2));
		//System.out.println("Solution array for Knapsack 2");
		//onKnapsack.printSolKnapSack2();
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack1Value());
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack2Value());
		
		
	}
	public void compare2(int W)
	{
		OfflineKnapsack offKnapSack = new OfflineKnapsack();
		OnlineKnapsack onKnapsack = new OnlineKnapsack(W,100);
		Item<Integer>[] itemList = setup3();
		offKnapSack.knapSack2(W, itemList, 100);
		System.out.println("Value of the offline Knapsack -->"+offKnapSack.getKnapSackValue());
		for(int i =0; i < itemList.length; i++)
		{
			onKnapsack.knapSack1Run(itemList[i].getWeight(), itemList[i].getValue());
			onKnapsack.knapSack2(itemList[i].getWeight(), itemList[i].getValue());
		}
		//System.out.println("Solution array for Knapsack 1");
		//onKnapsack.printSolKnapSack1();
		System.out.println("Value of the online Knapsack original algo-->"+onKnapsack.getKnapSack1Value());
		System.out.println("Value of the online Knapsack modified algo-->"+onKnapsack.getKnapSack2Value());
		//System.out.println("Solution array for Knapsack 2");
		//onKnapsack.printSolKnapSack2();
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack1Value());
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack2Value());
		
		
	}
	
	public void compare3()
	{
		OfflineKnapsack offKnapSack = new OfflineKnapsack();
		Item[] itemList = {new Item(1,10), new Item(2,15), new Item(3,20), new Item(2,12) };
		OnlineKnapsack onKnapsack = new OnlineKnapsack(5,4);
		
		offKnapSack.knapSack2(5, itemList, 4);
		System.out.println("Value of the offline Knapsack -->"+offKnapSack.getKnapSackValue());
		for(int i =0; i < itemList.length; i++)
		{
			onKnapsack.knapSack1Run((Integer)itemList[i].getWeight(),(Integer) itemList[i].getValue());
			onKnapsack.knapSack2((Integer)itemList[i].getWeight(), (Integer)itemList[i].getValue());
		}
		//System.out.println("Solution array for Knapsack 1");
		//onKnapsack.printSolKnapSack1();
		System.out.println("Value of the online Knapsack original algo-->"+onKnapsack.getKnapSack1Value());
		System.out.println("Value of the online Knapsack modified algo-->"+onKnapsack.getKnapSack2Value());
		//System.out.println("Solution array for Knapsack 2");
		//onKnapsack.printSolKnapSack2();
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack1Value());
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack2Value());
		
		
	}
	public void compare4(int W, int n,int weightRange, int valueRange)
	{
		OfflineKnapsack offKnapSack = new OfflineKnapsack();
		OnlineKnapsack onKnapsack = new OnlineKnapsack(W,n);
		Item<Integer>[] itemList = setup4(n,weightRange,valueRange);
		offKnapSack.knapSack2(W, itemList, n);
		for(int i =0; i < itemList.length; i++)
		{
			onKnapsack.knapSack1Run(itemList[i].getWeight(), itemList[i].getValue());
			onKnapsack.knapSack2(itemList[i].getWeight(), itemList[i].getValue());
		}
		//System.out.println("Solution array for Knapsack 1");
		//onKnapsack.printSolKnapSack1();
		System.out.print(n + "," + offKnapSack.getKnapSackValue() + "," + onKnapsack.getKnapSack1Value() + "," + onKnapsack.getKnapSack2Value());
		System.out.println("\n");
		/*System.out.println("Value of the online Knapsack original algo-->"+onKnapsack.getKnapSack1Value());
		System.out.println("Value of the online Knapsack modified algo-->"+onKnapsack.getKnapSack2Value());
		System.out.println("Value of the offline Knapsack -->"+offKnapSack.getKnapSackValue());*/
		//System.out.println("Solution array for Knapsack 2");
		//onKnapsack.printSolKnapSack2();
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack1Value());
		//System.out.println("Value of the online Knapsack original algorithm -->"+onKnapsack.getKnapSack2Value());
		
		
	}
	public static void main(String[] args) {
		TestKnapSack test = new TestKnapSack();
		//test.testCompareItemSort();
		/*test.testOfflineKnapsack();
		test.testOnlineSec();
		test.testOnlineSec();*/
		test.compare(700, 1000, 50, 1000);
		//test.compare2(200);
		/*Random randomGenerator = new Random ();
		for(int i = 0; i < 100 ; i++)
		{
			test.compare4(200 + randomGenerator.nextInt(801), 10+randomGenerator.nextInt(801),1000,1000);
		}*/
		
	}
	
}
