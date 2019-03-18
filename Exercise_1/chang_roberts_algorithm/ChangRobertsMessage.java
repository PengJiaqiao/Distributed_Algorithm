/**
Group 8
1. Alexandre Georges Rémi Martin, 406294
2. Benyamin Shafabakhsh, 406305
3. Yared Dejene Dessalk, 406228
4. Anubhav Guha, 406244
5. Jiaqiao Peng, 406038

Class to represent to the messaged used for Changs and Roberts Algorithm
*/

import java.awt.Color;

public class ChangRobertsMessage {
	Color color;	
	int highestId;
	boolean isWinNotification; 

	static int counter = 0;
	int id;

	/**
	Constructor with only highest node id
	This can be used to create normal message instances 
	*/
	public ChangRobertsMessage(int highestId) {
		this(highestId, false);
	}

	//Constructor with highest node id and message type(win notification or normal)
	public ChangRobertsMessage(int highestId, boolean isWinNotification) {
		this.highestId = highestId;
		this.isWinNotification = isWinNotification;
		
		//Use green color for win notification messages and orange for others
		this.color = isWinNotification ? Color.green: Color.orange;
		
		this.counter++;
		id = counter;
	}

	public int getHighestId(){
		return highestId;
	}

	public boolean getIsWinNotification(){
		return isWinNotification;
	}
	
	public String toString() {
		return "#" + id;
	}
}