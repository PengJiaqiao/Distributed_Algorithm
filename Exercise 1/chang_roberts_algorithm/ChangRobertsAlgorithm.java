
/**
 * 
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
        boolean isConfirmation = msg.getIsConfirmation(); 

        //For normal messages that are not confirmation types
        if(!isConfirmation){

            //Initiator nodes
            if(isCandidate){
                //Dismiss the incoming message if its highest Id is less than the current node id
                if(electedId < highestId){
                    electedId = highestId;
                    sendMessageClockwise(interf, new ChangRobertsMessage(electedId));
                }
                
                //Check if the circulating message comes back to the initiator that has the highest node id
                if(highestId == id){
                    electedId = id;
                    caption = "I am the master";
                    isWinnerKnown = true;
                    color = Color.green;   
                    //trigger confirmation messager since the winner is known                 
                    sendMessageClockwise(interf, new ChangRobertsMessage(id, true)); 
                }
            }
            //non-initiator nodes 
            else{
                //just forward the incoming message since the node is not candidate
                sendMessageClockwise(interf, msg);
            }         
            
        }
        //For confirmation messages
        else{
            if(!isWinnerKnown){                
                electedId = highestId;
                caption = electedId + " is the master";
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