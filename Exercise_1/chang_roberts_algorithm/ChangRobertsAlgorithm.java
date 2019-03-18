
/**
Group 8
1. Alexandre Georges Rémi Martin, 406294
2. Benyamin Shafabakhsh, 406305
3. Yared Dejene Dessalk, 406228
4. Anubhav Guha, 406244
5. Jiaqiao Peng, 406038

 * Implementation of Chang and Roberts Algorithm
 */

import teachnet.algorithm.BasicAlgorithm;
import java.awt.Color;

public class ChangRobertsAlgorithm extends BasicAlgorithm
{
	Color color = null;
    int id = -1, electedId = -1; //current node id and elected node id respectively
	String caption;
    boolean isCandidate = false; //To identify a given node is an initator/candidate or not 
    boolean isWinnerKnown = false; 
    

	public void setup(java.util.Map<String, Object> config)
	{	
        id = (Integer) config.get("node.id");
        caption = "" + id;
	}
	public void initiate()
	{
        //Set isCandidate to true for all initiator nodes
        isCandidate = true;
        color = color.white;
        electedId = id; 
        //Send first message over interface id of 0 
        sendMessageClockwise(0, new ChangRobertsMessage(electedId));
	}


	public void receive(int interf, Object message)
	{
		if (!(message instanceof ChangRobertsMessage)) return;
          
        ChangRobertsMessage msg = (ChangRobertsMessage) message;         
        
        int highestId = msg.getHighestId();
        boolean isWinNotification = msg.getIsWinNotification(); 

        //For normal messages that are not win notification types
        if(!isWinNotification){

            //Initiator nodes
            if(isCandidate){
                //Dismiss the incoming message if its highest id is less than the current node id
                if(electedId < highestId){
                    electedId = highestId;
                    sendMessageClockwise(interf, new ChangRobertsMessage(electedId));
                }
                
                //Check if the circulating message comes back to the initiator that has the highest node id
                if(highestId == id){
                    electedId = id;
                    caption = id + ":  I am the master";
                    isWinnerKnown = true;
                    color = Color.CYAN;   
                    //trigger win notification messager since the winner is known                 
                    sendMessageClockwise(interf, new ChangRobertsMessage(id, true)); 
                }
            }
            //non-initiator nodes 
            else{
                //just forward the incoming message since the node is not candidate
                sendMessageClockwise(interf, msg);
            }         
            
        }
        //For on the ring in descending order and initiate election in ascending order messages
        else{
            if(!isWinnerKnown){                
                electedId = highestId;
                caption = id + ":  " + electedId + " is the master";
                isWinnerKnown = true;
                color = Color.green;
                sendMessageClockwise(interf, msg); //forward confirmation message
            }
        }
        
	}

    //send message to neighbor node in clockwise direction
    private void sendMessageClockwise(int interf, ChangRobertsMessage message){
        int receiverId = (interf + 1) % checkInterfaces();
        send(receiverId, message);
    }
}