

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Juggler {
	
	String jugglerId;
	Skills skill;
	
	//List of circuit preferences in order
	ArrayList<String> circuits;
	
	//Contains all circuits in order but head item will be removed for each visit.
	Queue<String> circuitPreference;
	boolean assigned;
	
	Juggler(String jugglerId,Skills skill,ArrayList<String> circuits)
	{
		this.skill=skill;
		this.jugglerId=jugglerId;
		this.circuits=circuits;
		assigned=true;
		circuitPreference=new LinkedList<String>();
		for(String ckt:circuits)
		{
			circuitPreference.add(ckt);
		}
		
	}
	
	public boolean isAssigned() {
		return assigned;
	}
	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}	
	public ArrayList<String> getCircuits() {
		return circuits;
	}
	public void setCircuits(ArrayList<String> circuits) {
		this.circuits = circuits;
	}
	public Queue<String> getCircuitPreference() {
		return circuitPreference;
	}
	public void setCircuitPreference(Queue<String> circuitPreference) {
		this.circuitPreference = circuitPreference;
	}
	public String getJugglerId() {
		return jugglerId;
	}
	public void setJugglerId(String jugglerId) {
		this.jugglerId = jugglerId;
	}
	
	public Skills getSkill() {
		return skill;
	}
	public void setSkill(Skills skill) {
		this.skill = skill;
	}
	
}
