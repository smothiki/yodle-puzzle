
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReadWriteFile {

	
	/*function to count size of a Circuit*/
	public static int counts() throws IOException
	{
		int arr[]=new int[2];
		BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		String line;
		
		while ((line = br.readLine()) != null) {
			if(line.length()>0)
			{
			if(line.charAt(0)=='C')
				arr[0]++;
			if(line.charAt(0)=='J')
				arr[1]++;
			}
		}
		return (arr[1]/arr[0]);
	}

	
	
	/*To parse Skills (Circuit or Juggler)*/
	public static Skills parseSkill(String line)
	{
		String splits[];
		splits=line.split(" ");
		return new Skills(Integer.parseInt(splits[2].split(":")[1]),Integer.parseInt(splits[3].split(":")[1]),Integer.parseInt(splits[4].split(":")[1]));
	}

	
	
	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("Input.txt"));
		String line;
		ArrangeJugglers arrange=new ArrangeJugglers();
		
		/*Queue to Store all Circuits*/
		Queue <Circuit> unOccupiedCircuits=new ConcurrentLinkedQueue<Circuit>();
		
		/*Queue to Store all Juggler*/
		Queue<Juggler>  unAlloted=new ConcurrentLinkedQueue<Juggler>();
		
		/*Holding a Map for Circuits and Jugglers for fast access*/
		HashMap<String,Circuit> circuits=new HashMap<String, Circuit>();
		HashMap<String,Juggler> jugglers=new HashMap<String, Juggler>();
		
		String splits[];
		int size=counts();
		while ((line = br.readLine()) != null) {
			splits=line.split(" ");
			if(line.length()>0)
			{
			if(line.charAt(0)=='C')
			{
				String circuitId=splits[1];
				Skills skill=parseSkill(line);
				Circuit circuit=new Circuit(circuitId,size,skill);
				circuit.setArrange(arrange);
				circuits.put(circuitId, circuit);
				unOccupiedCircuits.add(circuit);
				
			}
			if(line.charAt(0)=='J')
			{
				String jugglerId=splits[1];
				Skills skill=parseSkill(line);
				ArrayList<String> Pcircuits=new ArrayList<String>();
				for(String circ:splits[5].split(","))
					Pcircuits.add(circ);
				Juggler juggler=new Juggler(jugglerId,skill,Pcircuits);
				jugglers.put(jugglerId, juggler);
				unAlloted.add(juggler);
			}
			}
		}
		arrange.setCircuits(circuits);
		arrange.setJugglers(jugglers);
		arrange.setUnOccupiedCircuits(unOccupiedCircuits);
		arrange.setUnAlloted(unAlloted);
		
		/*Actual Logic adds all the jugglers to circuits*/
		arrange.addtoCircuit();

		if(arrange.check())
		{
			//Writes to output.txt
				arrange.print();

		}
		else
		{
			System.out.println("Error");	
		}
	}
}
