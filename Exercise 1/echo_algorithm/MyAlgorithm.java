import teachnet.algorithm.BasicAlgorithm;

import java.awt.Color;
import java.util.Random;

public class MyAlgorithm extends BasicAlgorithm
{
	Color color = Color.WHITE;
	int markInterface = -1;
	String caption;
	String captionNoCount; //just for display
	Random rand = null;
	
	int rec = 0; //counts number of received messages
	int father = 0; //it needs to remember who sent a message first
	boolean isFather = false;	//the initiator behaves differently than the others from the image we have on the exercise
								//so we need to differenciate it from the others

	public void setup(java.util.Map<String, Object> config)
	{
		int id = (Integer) config.get("node.id");
		captionNoCount = "node " + id;
		caption = captionNoCount + " count " + rec + "/" + checkInterfaces();
		rand = getRandom();
	} //the time complexity of it would be Nodes...
	public void initiate() //only the initiator does that
	{
		isFather = true;
		for (int i = 0; i < checkInterfaces(); ++i) //sending a message to all the neighbors
			send(i, true);
		
		color = Color.RED; //has sent a message (following example L03 slide 18)
	} 
	public void receive(int interf, Object message)
	{
		rec++;
		caption = captionNoCount + " count " + rec + "/" + checkInterfaces();
		
		if(!isFather)
		{
			int i;
			if(rec < checkInterfaces() && rec == 1 && color != Color.RED) 
			{ 							//goes there if no messages have been received before
				father = interf; //remembering who the first message was sent by so it can reply to the father later
				for(i = 0; i < checkInterfaces(); ++i)
					if(i != interf) //sends to every neighbor except the father
						send(i, true);
				
				color = Color.RED; //has sent a message (following example L03 slide 19)
			}
			else if(rec == checkInterfaces()) //if all messages have been received
			{
				color = Color.GREEN; //has received all its messages (following example L03 slide 20)
				send(father, true);
			}
		}
		else if(rec == checkInterfaces())
		{
			color = Color.GREEN; //the initiator has received all messages
		}
	}
}
