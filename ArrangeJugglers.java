import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;




public class ArrangeJugglers {
	
	/*Holding a Map for Circuits and Jugglers for fast access*/
	private HashMap<String, Juggler> Jugglers;
	private HashMap<String, Circuit> circuits;
	
	//Queue to store alloted Jugglers
	private Queue<Juggler>  alloted;
	
	/*Queues to Store all Circuits and all Jugglers*/
	private Queue<Juggler>  unAlloted;
	private Queue <Circuit> unOccupiedCircuits;
	
	//Queue to store Jugglers with no preference List
	private Queue<Juggler>  unDefined;
	

	ArrangeJugglers()
	{
		unOccupiedCircuits=new ConcurrentLinkedQueue<Circuit>();
		alloted=new ConcurrentLinkedQueue<Juggler>();
		unAlloted=new ConcurrentLinkedQueue<Juggler>();
		unDefined=new ConcurrentLinkedQueue<Juggler>();
	}
	public Queue<Circuit> getUnOccupiedCircuits() {
		return unOccupiedCircuits;
	}
	public void setUnOccupiedCircuits(Queue<Circuit> unOccupiedCircuits) {
		this.unOccupiedCircuits = unOccupiedCircuits;
	}
	public HashMap<String, Juggler> getJugglers() {
		return Jugglers;
	}

	public void setJugglers(HashMap<String, Juggler> jugglers) {
		Jugglers = jugglers;
	}
	
	public Queue<Juggler> getAlloted() {
		return alloted;
	}

	public void setAlloted(Queue<Juggler> alloted) {
		this.alloted = alloted;
	}

	public Queue<Juggler> getUnAlloted() {
		return unAlloted;
	}

	public void setUnAlloted(Queue<Juggler> unAlloted) {
		this.unAlloted = unAlloted;
	}
	
	public HashMap<String, Circuit> getCircuits() {
		return circuits;
	}
	public void setCircuits(HashMap<String, Circuit> circuits) {
		this.circuits = circuits;
	}
	public void addtounAllotedJuggler(Juggler juggler)
	{	
		//System.out.println("kicked out"+juggler.getJugglerId());
		unAlloted.add(juggler);
	}
	public void removeAllotedJuggler(Juggler juggler)
	{	
		alloted.remove(juggler);
	}
	public void addtoAllotedJuggler(Juggler juggler)
	{	
		alloted.add(juggler);
	}
	public void removeUnAllotedJuggler(Juggler juggler)
	{	
		unAlloted.remove(juggler);
	}
	
	//Actual Logic
	
	
	public void addtoCircuit()
	{
		//Loops un-till there are no un-alloted jugglers
		while (unAlloted.size() > 0) 
		{
			for(Juggler juggler:unAlloted)
			{
				//For each juggler once a circuit is visited it is removed 
				if(juggler.getCircuitPreference().size()>0)
				{
					Circuit circuit=circuits.get(juggler.getCircuitPreference().peek());
					circuit.addtoQueue(juggler);
					juggler.getCircuitPreference().poll();
				}
				else
				{
					//Juggler whose circuits are exhausted and still not assigned to any group
					//System.out.println("undefined"+juggler.getJugglerId());
					unAlloted.remove(juggler);
					unDefined.add(juggler);
				}				
			}
			
			//Look for all non empty circuits
			for(Map.Entry<String, Circuit> entry : circuits.entrySet())
			{
				String circuitId=entry.getKey();
				Circuit circuit=entry.getValue();
				if(circuit.isFull())
				{
					unOccupiedCircuits.remove(circuit);
				}
			}
		}
		
		//Assign Each undefined Juggler to each non Empty Circuit in order 
		for(Circuit circuit:unOccupiedCircuits)
		{
			
			for(Juggler juggler:unDefined)
			{
				if(!circuit.isFull())
				{
					circuit.addtoQueue(juggler);
					unDefined.remove(juggler);
				}
				else
				{
					unOccupiedCircuits.remove(circuit);
					break;
				}
					
			}
		}
		
	}
		 
	//Sanity check to verify whether the program is correct or not
	
	public boolean check()
	{
		return (alloted.size()==Jugglers.size());
	
	}
	
	class circuitCompare implements Comparator<Circuit>{
		 public int compare(Circuit x, Circuit y)
		 {
			 int cxNumber=Integer.parseInt(x.getCircuitId().substring(1));
			 int cyNumber=Integer.parseInt(y.getCircuitId().substring(1));
			 if(cxNumber>cyNumber)
				 return 1;
			 if(cxNumber<cyNumber)
				 return -1;
			 return 0;
		 }
	}
	//Writes Output to a file 
	public void print() throws IOException
	{
		System.out.println("Printing.........");
		BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"));
		
		//To print in sorted order of circuits according to circuit numbers
		Set<Circuit> circuitList=new TreeSet<Circuit>(new circuitCompare());
		for(Map.Entry<String, Circuit> entry : circuits.entrySet())
		{
			circuitList.add(entry.getValue());
		}
		
		//Printing to output.txt
		for(Circuit circuit:circuitList)
		{
			Stack<Juggler> jug=new Stack<Juggler>();
			output.write(circuit.getCircuitId() + " ");
			for(Juggler juggler:circuit.getInList())
			{
				jug.push(juggler);
			}
			while(!jug.isEmpty())
			{
				Juggler juggler=jug.pop();
				output.write(juggler.getJugglerId() + " ");
				for(String ckt:juggler.getCircuits())
				{
					Circuit ckts=circuits.get(ckt);
					output.write(ckt + ":");
					output.write(juggler.getSkill().dotProduct(ckts.getSkill()) + " ");
				}
				if(jug.size()!=0)
					output.write(",");
			}
			output.write("\n");						
		}
		output.close();
		System.out.println("Done Printing.........Check Output.txt");
		 
		 
	}

}
