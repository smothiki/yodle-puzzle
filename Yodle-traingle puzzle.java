package Yodle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class Yodle{


public static void main(String args[]) throws IOException
{

	BufferedReader br = new BufferedReader(new FileReader("triangle.txt"));
	String line;
	String splits[];
	
	//List Stores each Row
	ArrayList<ArrayList<Integer> > rows= new ArrayList<ArrayList<Integer> >();
	
	while ((line = br.readLine()) != null) {
	 splits=line.split("\\s+");
	 
	 //Stores elements in a row 
	 ArrayList<Integer> row=new ArrayList<Integer>();
	 for(int i=0;i<splits.length;i++)
	 {
		 row.add(Integer.parseInt(splits[i]));
	 }
	 rows.add(row);
	}
	br.close();
	
	int totalRows=rows.size();
	
	int i=totalRows-1;
	while(i>0)
	{
		ArrayList<Integer> row=getMax(rows.get(i),rows.get(i-1));
		rows.remove(i--);
		rows.set(i,row);
	}
	System.out.println("Max Sum is "+rows.get(0).get(0));
	}
	

	/*Bottom Up approach calculates the Triangular sum a[i]+b[i],a[i]+b[i+1] and chooses the max
	 where a is a top row array and b is bottom row array
	 */
	public static ArrayList<Integer> getMax(ArrayList<Integer> bottom,ArrayList<Integer> top)
	{
		ArrayList<Integer> maxrow=new ArrayList<Integer>();
		for(int i=0;i<top.size();i++)
		{
			int max=Math.max(top.get(i)+bottom.get(i), top.get(i)+bottom.get(i+1));
			maxrow.add(max);
		}
		return maxrow;
	}

}