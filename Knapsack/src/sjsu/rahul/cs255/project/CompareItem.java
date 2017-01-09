package sjsu.rahul.cs255.project;

import java.util.Comparator;

public class CompareItem implements Comparator<Item<Double>> {
	
	public int compare(Item<Double> item1, Item<Double> item2) {
		/*System.out.println("In compare item");
		System.out.println("Value 1 -->" + item1.getValue());
		System.out.println("Value 1 -->" + item2.getValue());*/
		return Double.compare(item1.getValue(), item2.getValue());
		//return (int) Math.floor(item1.getValue() - item2.getValue());
	}

}
