package sjsu.rahul.cs255.project;

import java.util.Comparator;
import java.util.Random;

public class Item<T extends Comparable<T>> {
	private T weight;
	private T value;
	private float rescWeight;
	
	
	public Item()
	{
		// empty constructor
	}
	
	public T getWeight() {
		return weight;
	}
	public void setWeight(T weight) {
		this.weight = weight;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public String toString()
	{
		return "Item : " + " Weight --> " + weight + " Value --> " + value;
	}
	/*public Item[] createItem(int n,int rangeWeight, int rangeValue )
	{
		Random  gen  = new Random(); 
		Item[] itemList = new Item[n];
		for (int i=0; i < n; i++)
			itemList[i] = new Item( gen.nextInt(rangeWeight + 1), gen.nextInt(rangeValue + 1));
		return itemList;
		
	}*/
	
	public float getRescWeight() {
		return rescWeight;
	}
	public void setRescWeight(float rescWeight) {
		this.rescWeight = rescWeight;
	}
	public Item(T weight, T value)
	{
		this.weight = weight;
		this.value = value;
	}

}
