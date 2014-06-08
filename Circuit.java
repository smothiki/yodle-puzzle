

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Queue;
import java.util.PriorityQueue;


public class Circuit {
	
	private String circuitId;
	
	//Max Queue size (total number of jugglers / total number of circuits)
	private int queueSize;
	private ArrangeJugglers arrange;
	private boolean isFull;
	private Skills skill;
	
	//Priority Queue to store Juggler in order of best fit (Min Heap)
	private PriorityQueue<Juggler> inList;
	public ArrangeJugglers getArrange() {
		return arrange;
	}
	public Skills getSkill() {
		return skill;
	}
	public void setSkill(Skills skill) {
		this.skill = skill;
	}
	public void setArrange(ArrangeJugglers arrange) {
		this.arrange = arrange;
	}
	public boolean isFull() {
		if(inList.size()==queueSize)
			return true;
		else 
			return false;
	}	
	
	//Comparator for min heap
	public class MinJuggler implements Comparator<Juggler>
	{
	    @Override
	    public int compare(Juggler x, Juggler y)
	    {
	        int sumx=skill.dotProduct(x.getSkill());
	        int sumy=skill.dotProduct(y.getSkill());
	        if(sumx>sumy)
	        	return 1;
	        if(sumx<sumy)
	        	return -1;
	        return 0;
	    }
	}
	
	
	Circuit(String circuitId,int size,Skills skill)
	{
		
		queueSize=size;
		this.circuitId=circuitId;
		this.skill=skill;
		inList=new PriorityQueue<Juggler>(queueSize,new MinJuggler());
	}
	public String getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(String circuitId) {
		this.circuitId = circuitId;
	}
	public PriorityQueue<Juggler> getInList() {
		return inList;
	}
	public void setInList(PriorityQueue<Juggler> inList) {
		this.inList = inList;
	}
	
	//Adds Juggler to the queue
	public boolean addtoQueue(Juggler juggler)
	{
		
		/*If queue is full checks whether the juggler is best fit or not 
		a) if Juggler is best fit then removes head of the minHeap and inserts new Juggler
		--- adds juggler to alloted List and removes head of the Queue to unalloted List
		if Juggler is not best fit returns false   
		*/
		if(isFull())
		{
			Juggler last=inList.peek();
			int sumlast=skill.dotProduct(last.getSkill());
			int sumJuggler=skill.dotProduct(juggler.getSkill());;
			if(sumlast<sumJuggler)
			{
				//System.out.println(circuitId+"-"+juggler.getJugglerId()+":"+sumJuggler+"-"+last.getJugglerId()+":"+sumlast);
				last=inList.poll();
				last.setAssigned(false);
				juggler.setAssigned(true);
				arrange.addtounAllotedJuggler(last);
				arrange.removeAllotedJuggler(last);
				arrange.addtoAllotedJuggler(juggler);
				arrange.removeUnAllotedJuggler(juggler);
				inList.add(juggler);
				return true;
			}
			else
			{
				return false;
			}
			
		}
		
		//If Queue is not full adds Juggler to the Circuit
		//System.out.println(circuitId+"-"+juggler.getJugglerId());
		juggler.setAssigned(true);
		inList.add(juggler);
		arrange.addtoAllotedJuggler(juggler);
		arrange.removeUnAllotedJuggler(juggler);
		return true;			
	}
	
	
}
