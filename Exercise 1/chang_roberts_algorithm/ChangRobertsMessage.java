/**
Class to represent to the messaged used for Changs and Roberts Algorithm
*/

import java.awt.Color;

public class ChangRobertsMessage {
	Color color;	
	int highestId;
	boolean isConfirmation; 

	static int counter = 0;
	int id;

	/**
	Constructor with only highest node id
	This can be used to create normal message instances 
	*/
	public ChangRobertsMessage(int highestId) {
		this(highestId, false);
	}

	//Constructor with highest node id and message type(confirmation or normal)
	public ChangRobertsMessage(int highestId, boolean isConfirmation) {
		this.highestId = highestId;
		this.isConfirmation = isConfirmation;
		
		//Use green color for confirmation messages and orange for others
		this.color = isConfirmation ? Color.green: Color.orange;
		
		this.counter++;
		id = counter;
	}

	public int getHighestId(){
		return highestId;
	}

	public boolean getIsConfirmation(){
		return isConfirmation;
	}
	
	public String toString() {
		return "#" + id;
	}
}